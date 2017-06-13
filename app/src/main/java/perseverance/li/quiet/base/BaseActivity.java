package perseverance.li.quiet.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import perseverance.li.quiet.util.GlideUtil;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/27 17:29
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/27 17 : Create by LiYi
 * ---------------------------------------------------------------
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView {

    protected T mPresenter;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        init();
        initView();
    }

    private void init() {
        mPresenter = getPresenter();
        if (mPresenter != null) {
            mPresenter.create(this);
        }
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
