package perseverance.li.quiet.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.File;

import jp.wasabeef.blurry.Blurry;
import jp.wasabeef.blurry.Blurry.ImageComposer.ImageComposerListener;
import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseActivity;
import perseverance.li.quiet.detail.presenter.WelfarePresenter;
import perseverance.li.quiet.detail.view.IWelfareDetailView;
import perseverance.li.quiet.util.ToastUtil;
import perseverance.li.quiet.widget.ArrowDownloadButton;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/13 14:01
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/13 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class WelfareDetailActivity extends BaseActivity<WelfarePresenter> implements IWelfareDetailView {

    private static final String TAG = "WelfareDetailActivity";
    public static final String IMAGE_URL_TAG = "image_url";
    private static final long ANIM_TIME = 500;
    private String mImageUrl;
    private ProgressBar mProgressBar;
    private ImageView mImageView;
    private ArrowDownloadButton mDownloadButton;
    private MenuItem mSaveMenu;
    private MenuItem mShareMenu;

    @Override
    public WelfarePresenter getPresenter() {
        return new WelfarePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welfare;
    }

    @Override
    protected void onMenuHome() {
        finish();
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void initView() {
        mProgressBar = (ProgressBar) findViewById(R.id.welfare_progressbar);
        mImageUrl = getIntent().getStringExtra(IMAGE_URL_TAG);
        mImageView = (ImageView) findViewById(R.id.welfare_image);
        mPresenter.loadWelfarePicture(mActivity, mImageView, mImageUrl);
        mDownloadButton = (ArrowDownloadButton) findViewById(R.id.arrow_download_button);
    }

    @Override
    public void onLoadFailure(Throwable e) {
        ToastUtil.showShort(mActivity, "图片加载失败，error msg : " + e.getMessage());
        mImageView.setImageResource(R.mipmap.img_load_error);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welfare, menu);
        mSaveMenu = menu.findItem(R.id.menu_save);
        mShareMenu = menu.findItem(R.id.menu_share);
        mShareMenu.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_save:
                mSaveMenu.setEnabled(false);
                Blurry.with(mActivity)
                        .radius(10)
                        .sampling(8)
                        .animate(500)
                        .async(blurryListener)
                        .capture(mImageView)
                        .into(mImageView);
                return true;
            case R.id.menu_share:
                if (mPresenter != null) {
                    boolean shareStatus = mPresenter.sendPictureToWx();
                    Log.d(TAG, "share to wx sattus: " + shareStatus);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDownloadProgress(float progress) {
        mDownloadButton.setProgress(progress);
    }

    @Override
    public void onDownloadImageFail() {
        resetView(false);
    }

    @Override
    public void onDownloadImageSuccess(String filePath) {
        resetView(true);
        // 更新相册
        Uri uri = Uri.fromFile(new File(filePath));
        Intent scannerIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri);
        sendBroadcast(scannerIntent);
    }

    @Override
    public void onLoadImageSuccess() {
        mProgressBar.setVisibility(View.GONE);
        if (mShareMenu != null) {
            mShareMenu.setVisible(true);
        }
    }

    private void resetView(boolean isSaveSuccess) {
        mDownloadButton.postDelayed(new Runnable() {
            @Override
            public void run() {
                mDownloadButton.reset();
                mDownloadButton.setVisibility(View.GONE);
                if (mPresenter != null) {
                    Bitmap bitmap = mPresenter.resetWelfarePicture();
                    if (bitmap != null) {
                        mImageView.setImageBitmap(bitmap);
                    }
                }
                mSaveMenu.setEnabled(true);
            }
        }, ANIM_TIME);
        ToastUtil.showShort(this, isSaveSuccess ? R.string.save_image_success : R.string.save_image_fail);
    }

    private ImageComposerListener blurryListener = new ImageComposerListener() {
        @Override
        public void onImageReady(BitmapDrawable drawable) {
            //如果activity已经销毁，不在加载
            if (mActivity == null || mActivity.isFinishing() || mActivity.isDestroyed()) {
                return;
            }
            mImageView.setImageDrawable(drawable);
            mDownloadButton.setVisibility(View.VISIBLE);
            mDownloadButton.startAnimating();
            mDownloadButton.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (mPresenter != null) {
                        mPresenter.downloadPicture(mImageUrl);
                    }
                }
            }, ANIM_TIME);
        }
    };
}
