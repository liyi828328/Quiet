package perseverance.li.quiet.detail;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseActivity;
import perseverance.li.quiet.detail.presenter.WelfarePresenter;
import perseverance.li.quiet.detail.view.IWelfareDetailView;
import perseverance.li.quiet.util.ToastUtil;

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

    public static final String IMAGE_URL_TAG = "image_url";
    private String mImageUrl;
    private ProgressBar mProgressBar;
    private ImageView mImageView;

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
        mPresenter.loadWelfarePicture(this, mImageView, mImageUrl);
    }

    @Override
    public void onLoadFailure(Throwable e) {
        ToastUtil.showShort(this, "图片加载失败，error msg : " + e.getMessage());
        mImageView.setImageResource(R.mipmap.img_load_error);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welfare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_save) {
            mPresenter.saveWelfarePicture(mImageUrl);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveImageSuccess() {
        ToastUtil.showShort(this, "保存图片成功");
    }

    @Override
    public void onLoadImageSuccess() {
        mProgressBar.setVisibility(View.GONE);
    }
}
