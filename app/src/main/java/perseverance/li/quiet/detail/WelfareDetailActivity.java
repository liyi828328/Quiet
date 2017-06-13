package perseverance.li.quiet.detail;

import perseverance.li.quiet.base.BaseActivity;
import perseverance.li.quiet.detail.presenter.WelfarePresenter;
import perseverance.li.quiet.detail.view.IWelfareDetailView;

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
    @Override
    public void initView() {

    }

    @Override
    public void onLoadFailure(Throwable e) {

    }

    @Override
    public WelfarePresenter getPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }
}
