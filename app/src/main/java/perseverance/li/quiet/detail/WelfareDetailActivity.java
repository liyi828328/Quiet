package perseverance.li.quiet.detail;

import android.graphics.Color;
import android.media.Image;
import android.provider.Settings;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseActivity;
import perseverance.li.quiet.detail.presenter.WelfarePresenter;
import perseverance.li.quiet.detail.view.IWelfareDetailView;
import perseverance.li.quiet.util.GlideUtil;
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
        mImageUrl = getIntent().getStringExtra(IMAGE_URL_TAG);
        ImageView imgView = (ImageView) findViewById(R.id.welfare_image);
        GlideUtil.displayUrl(this, imgView, mImageUrl, R.mipmap.img_default_gray);
    }

    @Override
    public void onLoadFailure(Throwable e) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_welfare, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_save) {
            mPresenter.saveWelfarePicture(mImageUrl);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void saveImageSuccess() {
        ToastUtil.showShort(this, "保存图片成功");
    }
}
