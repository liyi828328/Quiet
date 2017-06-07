package perseverance.li.quiet.home.presenter;

import android.util.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import perseverance.li.quiet.base.BaseGankData;
import perseverance.li.quiet.base.BasePresenter;
import perseverance.li.quiet.home.model.DailyModule;
import perseverance.li.quiet.home.model.GankDataModule;
import perseverance.li.quiet.home.model.HomeModelBiz;
import perseverance.li.quiet.home.model.QuietPageType;
import perseverance.li.quiet.home.view.IHomeView;
import perseverance.li.quiet.util.GankApi;
import perseverance.li.quiet.util.GankApiDateUtil;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/31 15:25
 * ---------------------------------------------------------------
 * Describe:
 * 首页Presenter
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/31 15 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class HomePresenter extends BasePresenter<IHomeView> {

    private static final String TAG = "HomePresenter";
    private HomeModelBiz mHomeModelBiz;
    /**
     * 当前加载的页面，加载成功后+1
     */
    private int mCurrentPage = 1;
    private GankApiDateUtil mCurrentDate;
    private QuietPageType mCurrentPageType;
    private List<DailyModule> mDailyModuleList = new ArrayList<>();
    private List<BaseGankData> mWelfareDataList = new ArrayList<>();

    public HomePresenter() {
        mHomeModelBiz = new HomeModelBiz();
        mCurrentDate = new GankApiDateUtil(Calendar.getInstance());
        mCurrentDate.setCurrentPagePosition(mCurrentPage);
    }

    /**
     * 获取福利
     */
    private void loadWelfareData() {
        DisposableObserver<List<BaseGankData>> listDisposableObserver = new DisposableObserver<List<BaseGankData>>() {
            @Override
            public void onNext(@NonNull List<BaseGankData> gankDatas) {
                IHomeView homeView = getBaseView();
                if (gankDatas == null || gankDatas.size() == 0) {
                    Log.d(TAG, "load welfare datas is null , return on load failure");
                    if (homeView != null) {
                        homeView.onLoadFailure(new Throwable("welfare datas is null"));
                    }
                    return;
                }

                if (homeView != null) {
                    mWelfareDataList.addAll(gankDatas);
                    homeView.onLoadWelfareDatasSuccess(mWelfareDataList);
                }
                //刷新成功一次后页面数量+1
                mCurrentPage++;
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                IHomeView homeView = getBaseView();
                if (homeView != null) {
                    homeView.onLoadFailure(e);
                }
            }

            @Override
            public void onComplete() {

            }
        };

        //将 Observer 统一管理
        addDispoable(listDisposableObserver);

        mHomeModelBiz.onLoadGankDataByType(GankApi.DATA_TYPE_WELFARE, GankApi.DEFAULT_DATA_SIZE, mCurrentPage)
                //将数据转换为界面可用的数据
                .map(new Function<GankDataModule, List<BaseGankData>>() {
                    @Override
                    public List<BaseGankData> apply(@NonNull GankDataModule gankDataModule) throws Exception {
                        if (gankDataModule == null || gankDataModule.results == null || gankDataModule.results.size() == 0) {
                            return Collections.emptyList();
                        }
                        List<BaseGankData> baseGankDatas = new ArrayList<>();
                        baseGankDatas.addAll(gankDataModule.results);
                        return baseGankDatas;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listDisposableObserver);
    }

    /**
     * 下拉刷新时，获取一定范围时间的每日数据
     */
    private void loadDailyListData() {

        DisposableObserver<List<DailyModule>> listDisposableObserver = new DisposableObserver<List<DailyModule>>() {
            @Override
            public void onNext(@NonNull List<DailyModule> dailyModules) {

                IHomeView homeView = getBaseView();
                if (dailyModules == null || dailyModules.size() == 0) {
                    Log.d(TAG, "load daily datas is null , return on load failure");
                    if (homeView != null) {
                        homeView.onLoadFailure(new Throwable("Daily datas is null"));
                    }
                    return;
                }

                Log.d(TAG, "onNext dailyModules size: " + dailyModules.size());

                //去掉无效数据
                Iterator<DailyModule> iterator = dailyModules.iterator();
                while (iterator.hasNext()) {
                    DailyModule dailyModule = iterator.next();
                    if (dailyModule == null || dailyModule.category == null || dailyModule.category.size() == 0 || dailyModule.results == null) {
                        iterator.remove();
                    }
                }

                Log.d(TAG, "onNext dailyModules size iterator end: " + dailyModules.size());
                //回调界面
                if (homeView != null) {
                    mDailyModuleList.addAll(dailyModules);
                    homeView.onLoadDailyDatasSuccess(mDailyModuleList);
                }
                //刷新成功一次后页面数量+1
                mCurrentPage++;
                mCurrentDate.setCurrentPagePosition(mCurrentPage);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
                IHomeView homeView = getBaseView();
                if (homeView != null) {
                    homeView.onLoadFailure(e);
                }
            }

            @Override
            public void onComplete() {

            }
        };

        //将 Observer 统一管理
        addDispoable(listDisposableObserver);

        Observable.fromIterable(mCurrentDate.getListTime())
                .flatMap(new Function<GankApiDateUtil, ObservableSource<DailyModule>>() {
                    @Override
                    public ObservableSource<DailyModule> apply(@NonNull GankApiDateUtil gankApiDateUtil) throws Exception {
                        int year = gankApiDateUtil.getYear();
                        int month = gankApiDateUtil.getMonth();
                        int day = gankApiDateUtil.getDay();
                        Log.d(TAG, "home presenter load daily data year: " + year + " month: " + month + " day: " + day);
                        return mHomeModelBiz.onLoadDailyData(year, month, day);
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toList()
                .toObservable()
                .subscribe(listDisposableObserver);
    }

    /**
     * 切换页面
     *
     * @param type
     */
    public void changeLoadDataByType(QuietPageType type) {
        //TODO:一下if中的页面还没有做，直接return
        if (type == null || type.equals(mCurrentPageType)
                || type.equals(QuietPageType.QUIET_ABOUT_TYPE)
                || type.equals(QuietPageType.QUIET_APP_SHARE_TYPE)
                || type.equals(QuietPageType.QUIET_RECREATION_TYPE)
                || type.equals(QuietPageType.QUIET_SHARE_TYPE)) {
            return;
        }

        clearDispoable();
        mCurrentPageType = type;
        IHomeView view = getBaseView();
        if (view != null) {
            view.changeViewByType(mCurrentPageType);
        }

        switch (type) {
            case QUIET_DAILY_TYPE:
                mCurrentPage = 1;
                mDailyModuleList.clear();
                mCurrentDate.setCurrentPagePosition(mCurrentPage);
                loadDailyListData();
                break;
            case QUIET_WELFARE_TYPE:
                mCurrentPage = 1;
                mWelfareDataList.clear();
                loadWelfareData();
                break;
        }
    }

    /**
     * 滑动加载更多
     */
    public void loadMoreData() {
        if (mCurrentPageType == null) {
            Log.d(TAG, "load more data type : " + mCurrentPageType);
            return;
        }

        switch (mCurrentPageType) {
            case QUIET_DAILY_TYPE:
                loadDailyListData();
                break;
            case QUIET_WELFARE_TYPE:
                loadWelfareData();
                break;
        }
    }

    /**
     * 返回当前页面类型
     *
     * @return
     */
    public QuietPageType getCurrentPageType() {
        return mCurrentPageType;
    }

    @Override
    public void destory() {
        super.destory();

        mCurrentDate = null;
        mCurrentPage = 1;
        mCurrentPageType = null;
        mDailyModuleList.clear();
        mDailyModuleList = null;
        mWelfareDataList.clear();
        mWelfareDataList = null;
    }
}
