package perseverance.li.quiet.detail;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.List;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BaseActivity;
import perseverance.li.quiet.base.OnRecyclerViewItemClickListener;
import perseverance.li.quiet.bean.BaseGankData;
import perseverance.li.quiet.bean.DailyModule;
import perseverance.li.quiet.detail.adapter.DailyDetailAdapter;
import perseverance.li.quiet.detail.presenter.DailyDetailPresenter;
import perseverance.li.quiet.detail.view.IDailyDetailView;
import perseverance.li.quiet.home.SpacingItemDecoration;
import perseverance.li.quiet.util.GankApi;
import perseverance.li.quiet.util.ToastUtil;
import perseverance.li.quiet.web.WebActivity;

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
public class DailyDetailActivity extends BaseActivity<DailyDetailPresenter> implements IDailyDetailView, OnRecyclerViewItemClickListener {

    private static final String TAG = "DailyDetailActivity";
    public static final String DAILY_DATA_KEY = "daily_data_key";
    public static final String DAILY_TITLE_KEY = "daily_title_key";
    private RecyclerView mDailyRecyclerView;
    private DailyDetailAdapter mAdapter;

    @Override
    public void initView() {
        String title = getIntent().getStringExtra(DAILY_TITLE_KEY);
        if (!TextUtils.isEmpty(title)) {
            mActionbar.setTitle(title);
        }
        LinearLayoutManager lManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        SpacingItemDecoration lsid = new SpacingItemDecoration(1, mActivity.getResources()
                .getDimensionPixelSize(R.dimen.item_padding), true);
        mDailyRecyclerView = (RecyclerView) findViewById(R.id.daily_detail_recyclerview);
        mDailyRecyclerView.setLayoutManager(lManager);
        mDailyRecyclerView.setHasFixedSize(true);
        mDailyRecyclerView.setItemAnimator(null);
        mDailyRecyclerView.addItemDecoration(lsid);
        mAdapter = new DailyDetailAdapter(mActivity);
        mAdapter.setOnItemClickListener(this);
        mDailyRecyclerView.setAdapter(mAdapter);

        if (mPresenter != null) {
            DailyModule dailyModule = getIntent().getParcelableExtra(DAILY_DATA_KEY);
            Log.d(TAG, "get intent dailyModule : " + dailyModule.toString());
            mPresenter.transitionData(dailyModule);
        }
    }

    @Override
    public void onLoadFailure(Throwable e) {
        ToastUtil.showShort(mActivity, R.string.load_fail);
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
        return new DailyDetailPresenter();
    }

    @Override
    public void onLoadDataToView(List<BaseGankData> dataList) {
        mAdapter.setDataList(dataList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        if (mAdapter == null) {
            return;
        }
        BaseGankData gankData = mAdapter.getDataByPosition(position);
        if (gankData == null) {
            return;
        }
        String type = gankData.type;
        if (GankApi.DATA_TYPE_WELFARE.equals(type)) {
            Intent intent = new Intent(this, WelfareDetailActivity.class);
            intent.putExtra(WelfareDetailActivity.IMAGE_URL_TAG, gankData.url);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, WebActivity.class);
            intent.putExtra(WebActivity.TITLE_TAG, gankData.desc);
            intent.putExtra(WebActivity.URL_TAG, gankData.url);
            startActivity(intent);
        }
    }
}
