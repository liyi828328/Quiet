package perseverance.li.quiet.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import perseverance.li.quiet.R;
import perseverance.li.quiet.util.GlideUtil;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/27 17:29
 * ---------------------------------------------------------------
 * Describe:
 * Activity 基类
 *
 * 初始化顺序  init > initToolbar > initView
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/27 17 : Create by LiYi
 * ---------------------------------------------------------------
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {

    protected T mPresenter;

    protected Toolbar mToolbar;

    protected ActionBar mActionbar;
    /**
     * 获取所绑定的presenter
     *
     * @return
     */
    public abstract T getPresenter();

    /**
     * 获取布局id
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * ActionBar 返回键监听处理
     */
    protected abstract void onMenuHome();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init();
        initToolbar();
        initView();
    }

    /**
     * 初始化toolbar
     */
    protected void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.create(this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onMenuHome();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.destory();
            mPresenter = null;
        }
        GlideUtil.clearGlideMemory(this);
    }
}
