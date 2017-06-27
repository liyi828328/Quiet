package perseverance.li.quiet.detail.presenter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import perseverance.li.quiet.base.BasePresenter;
import perseverance.li.quiet.bean.BaseGankData;
import perseverance.li.quiet.bean.DailyModule;
import perseverance.li.quiet.bean.DailyModule.DailyResults;
import perseverance.li.quiet.detail.view.IDailyDetailView;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/20 14:35
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/20 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class DailyDetailPresenter extends BasePresenter<IDailyDetailView> implements IDailyDetailPresenter {
    @Override
    public void transitionData(DailyModule data) {
        DisposableObserver<List<BaseGankData>> disposableObserver = new DisposableObserver<List<BaseGankData>>() {
            @Override
            public void onNext(@NonNull List<BaseGankData> dataList) {
                if (mView != null) {
                    if (dataList == null || dataList.size() == 0) {
                        mView.onLoadFailure(new Throwable("data list is empty"));
                    } else {
                        mView.onLoadDataToView(dataList);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                if (mView != null) {
                    mView.onLoadFailure(e);
                }
            }

            @Override
            public void onComplete() {
            }
        };
        //将 Observer 统一管理
        addDispoable(disposableObserver);

        Observable.just(data)
                .map(new Function<DailyModule, List<BaseGankData>>() {
                    @Override
                    public List<BaseGankData> apply(@NonNull DailyModule dailyModule) throws Exception {
                        List<BaseGankData> results = new ArrayList<>();
                        if (dailyModule == null) {
                            return Collections.EMPTY_LIST;
                        }
                        DailyResults dailyResults = dailyModule.results;
                        if (dailyResults == null) {
                            return Collections.EMPTY_LIST;
                        }
                        if (dailyResults.welfareData != null && dailyResults.welfareData.size() > 0) {
                            results.addAll(dailyResults.welfareData);
                        }
                        if (dailyResults.androidData != null && dailyResults.androidData.size() > 0) {
                            results.addAll(dailyResults.androidData);
                        }
                        if (dailyResults.iosData != null && dailyResults.iosData.size() > 0) {
                            results.addAll(dailyResults.iosData);
                        }
                        if (dailyResults.jsData != null && dailyResults.jsData.size() > 0) {
                            results.addAll(dailyResults.jsData);
                        }
                        if (dailyResults.videoData != null && dailyResults.videoData.size() > 0) {
                            results.addAll(dailyResults.videoData);
                        }
                        if (dailyResults.resourcesData != null && dailyResults.resourcesData.size() > 0) {
                            results.addAll(dailyResults.resourcesData);
                        }
                        if (dailyResults.appData != null && dailyResults.appData.size() > 0) {
                            results.addAll(dailyResults.appData);
                        }
                        if (dailyResults.recommendData != null && dailyResults.recommendData.size() > 0) {
                            results.addAll(dailyResults.recommendData);
                        }
                        return results;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(disposableObserver);
    }
}
