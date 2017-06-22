package perseverance.li.quiet.detail;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseActivity;
import perseverance.li.quiet.detail.presenter.DailyDetailPresenter;
import perseverance.li.quiet.detail.view.IDailyDetailView;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/16 17:18
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/16 17 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class DailyDetailActivity extends BaseActivity<DailyDetailPresenter> implements IDailyDetailView {
    

    @Override
    public void initView() {

    }

    @Override
    public void onLoadFailure(Throwable e) {

    }

    @Override
    protected void onMenuHome() {
        finish();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.daily_detail_activity;
    }

    @Override
    public DailyDetailPresenter getPresenter() {
        return null;
    }
}
