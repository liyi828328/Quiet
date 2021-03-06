package perseverance.li.quiet.home;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import perseverance.li.quiet.R;
import perseverance.li.quiet.about.AboutActivity;
import perseverance.li.quiet.base.BaseActivity;
import perseverance.li.quiet.base.OnRecyclerViewItemClickListener;
import perseverance.li.quiet.bean.BaseGankData;
import perseverance.li.quiet.bean.DailyModule;
import perseverance.li.quiet.detail.DailyDetailActivity;
import perseverance.li.quiet.detail.WelfareDetailActivity;
import perseverance.li.quiet.home.adapter.DailyDataAdapter;
import perseverance.li.quiet.home.adapter.WelfareDataAdapter;
import perseverance.li.quiet.home.model.QuietPageType;
import perseverance.li.quiet.home.presenter.HomePresenter;
import perseverance.li.quiet.home.view.IHomeView;
import perseverance.li.quiet.util.ToastUtil;

public class HomeActivity extends BaseActivity<HomePresenter> implements NavigationView.OnNavigationItemSelectedListener,
        SwipeRefreshLayout.OnRefreshListener,
        IHomeView, OnRecyclerViewItemClickListener {

    private static final String TAG = "HomeActivity";
    private static final int RECYCLERVIEW_LINEAR = 1;
    private static final int RECYCLERVIEW_STAGGERED = 2;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private DailyDataAdapter mDailyDataAdapter;
    private WelfareDataAdapter mWelfareDataAdapter;
    private SpacingItemDecoration mLinearDecoration;
    private SpacingItemDecoration mStaggeredDecoration;
    private LinearLayoutManager mLinearLayoutManager;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private boolean isFirstLoadNotData = true;

    @Override
    public HomePresenter getPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onMenuHome() {
        //Don't do anything
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        mActionbar.setDisplayHomeAsUpEnabled(false);
    }

    @Override
    public void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        ColorStateList csl = getResources().getColorStateList(R.color.nav_item_color);
        mNavigationView.setItemIconTintList(csl);

        mLinearDecoration = new SpacingItemDecoration(RECYCLERVIEW_LINEAR,
                mActivity.getResources().getDimensionPixelSize(R.dimen.item_padding), true);
        mStaggeredDecoration = new SpacingItemDecoration(RECYCLERVIEW_STAGGERED,
                mActivity.getResources().getDimensionPixelSize(R.dimen.item_padding), true);
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mStaggeredGridLayoutManager = new StaggeredGridLayoutManager(RECYCLERVIEW_STAGGERED, StaggeredGridLayoutManager.VERTICAL);


        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.home_content_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.nav_item_icon_selected_color);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.home_content_recyclerview);
        mRecyclerView.addOnScrollListener(getRecyclerViewOnScrollListener());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(null);

        mDailyDataAdapter = new DailyDataAdapter(mActivity);
        mDailyDataAdapter.setOnItemClickListener(this);
        mWelfareDataAdapter = new WelfareDataAdapter(mActivity);
        mWelfareDataAdapter.setOnItemClickListener(this);

        //进入页面刷新数据
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
            mPresenter.changeLoadDataByType(QuietPageType.QUIET_DAILY_TYPE);
            mNavigationView.setCheckedItem(R.id.nav_daily);
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        QuietPageType quietPageType = null;
        switch (id) {
            case R.id.nav_daily:
                quietPageType = QuietPageType.QUIET_DAILY_TYPE;
                break;
            case R.id.nav_welfare:
                quietPageType = QuietPageType.QUIET_WELFARE_TYPE;
                break;
            case R.id.nav_app:
                quietPageType = QuietPageType.QUIET_APP_SHARE_TYPE;
                break;
            case R.id.nav_recreation:
                quietPageType = QuietPageType.QUIET_RECREATION_TYPE;
                break;
            case R.id.nav_share:
                quietPageType = QuietPageType.QUIET_SHARE_TYPE;
                break;
            case R.id.nav_about:
                Intent aboutIntent = new Intent(mActivity, AboutActivity.class);
                startActivity(aboutIntent);
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
        }
        isFirstLoadNotData = true;
        mPresenter.changeLoadDataByType(quietPageType);
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLoadFailure(Throwable e) {
        Log.d(TAG, "HomeActivity on load failure");
        if (isFirstLoadNotData) {
            mPresenter.loadMoreData();
        } else {
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }

    @Override
    public void onRefresh() {
        mPresenter.loadMoreData();
    }

    @Override
    public void onLoadDailyDatasSuccess(List<DailyModule> dailyModules) {
        Log.d(TAG, "HomeActivity on load daily success");
        mDailyDataAdapter.setDataList(dailyModules);
        mDailyDataAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        isFirstLoadNotData = false;
    }

    @Override
    public void onLoadWelfareDatasSuccess(List<BaseGankData> welfareDatas) {
        Log.d(TAG, "HomeActivity on load welfare success");
        mWelfareDataAdapter.setDataList(welfareDatas);
        mWelfareDataAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        isFirstLoadNotData = false;
    }

    @Override
    public void changeViewByType(QuietPageType pageType) {
        if (pageType == null) {
            return;
        }
        if (!mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }

        //是否为线性显示的布局
        boolean isLinear = pageType.equals(QuietPageType.QUIET_DAILY_TYPE);
        mRecyclerView.removeAllViews();
        mRecyclerView.setLayoutManager(isLinear ? mLinearLayoutManager : mStaggeredGridLayoutManager);
        mRecyclerView.removeItemDecoration(isLinear ? mStaggeredDecoration : mLinearDecoration);
        mRecyclerView.addItemDecoration(isLinear ? mLinearDecoration : mStaggeredDecoration);
        mRecyclerView.setAdapter(isLinear ? mDailyDataAdapter : mWelfareDataAdapter);
    }

    @Override
    public void lookForwardTo() {
        ToastUtil.showShort(mActivity, R.string.look_forward_to);
    }

    @Override
    public void onItemClick(View view, int position) {
        QuietPageType type = mPresenter.getCurrentPageType();
        switch (type) {
            case QUIET_WELFARE_TYPE:
                BaseGankData baseGankData = mWelfareDataAdapter.getDataByPosition(position);
                Intent intent = new Intent(this, WelfareDetailActivity.class);
                intent.putExtra(WelfareDetailActivity.IMAGE_URL_TAG, baseGankData.url);
                startActivity(intent);
                break;
            case QUIET_DAILY_TYPE:
                DailyModule dailyModule = mDailyDataAdapter.getDataByPosition(position);
                Intent dailyIntent = new Intent(this, DailyDetailActivity.class);
                dailyIntent.putExtra(DailyDetailActivity.DAILY_DATA_KEY, dailyModule);
                dailyIntent.putExtra(DailyDetailActivity.DAILY_TITLE_KEY, mDailyDataAdapter.getDate());
                startActivity(dailyIntent);
                break;
        }
    }

    /**
     * RecyclerView 滑动监听
     *
     * @return
     */
    private RecyclerView.OnScrollListener getRecyclerViewOnScrollListener() {
        return new OnScrollListener() {

            private boolean toLast = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                /**
                 * dy 表示y轴滑动方向
                 * dx 表示x轴滑动方向
                 */
                if (dy > 0) {
                    // 正在向下滑动
                    this.toLast = true;
                } else {
                    // 停止滑动或者向上滑动
                    this.toLast = false;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

                switch (newState) {
                    //当屏幕滚动且用户使用的触碰或手指还在屏幕上，停止加载图片
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        //滑动停止加载图片
                        //GlideUtil.pauseRequests(mActivity);
                        break;
                    //停止滚动
                    case RecyclerView.SCROLL_STATE_IDLE:
                        if (mSwipeRefreshLayout.isRefreshing()) {
                            return;
                        }
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof LinearLayoutManager) {
                            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                            // 不滚动
                            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                                // 最后完成显示的item的position 正好是 最后一条数据的index
                                if (toLast && manager.findLastCompletelyVisibleItemPosition() ==
                                        (manager.getItemCount() - 1)) {
                                    mSwipeRefreshLayout.setRefreshing(true);
                                    mPresenter.loadMoreData();
                                }
                            }
                        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
                            int[] bottom = manager.findLastCompletelyVisibleItemPositions(new int[2]);
                            int lastItemCount = manager.getItemCount() - 1;
                            if (toLast && (bottom[0] == lastItemCount || bottom[1] == lastItemCount)) {
                                mSwipeRefreshLayout.setRefreshing(true);
                                mPresenter.loadMoreData();
                            }
                        }

                        //GlideUtil.resumeRequests(mActivity);
                        break;
                    //由于用户的操作，屏幕产生惯性滑动，停止加载图片
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        //滑动停止加载图片
                        //GlideUtil.pauseRequests(mActivity);
                        break;
                }
            }
        };
    }
}