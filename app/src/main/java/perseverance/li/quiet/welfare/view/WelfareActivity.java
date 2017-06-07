package perseverance.li.quiet.welfare.view;

import android.os.Bundle;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseActivity;
import perseverance.li.quiet.base.BasePresenter;
import perseverance.li.quiet.welfare.presenter.WelfarePresenter;

public class WelfareActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public BasePresenter getPresenter() {
        return new WelfarePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_welfare;
    }

    @Override
    public void initView() {

    }

    @Override
    public void onLoadFailure(Throwable e) {

    }
}
