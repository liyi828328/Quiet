package perseverance.li.quiet.home.model;

import io.reactivex.Observable;
import perseverance.li.quiet.home.gank.GankDaily;
import perseverance.li.quiet.home.gank.GankData;
import perseverance.li.quiet.util.RetrofitManager;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/31 15:27
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/31 15 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class HomeModelBiz implements IHomeModelBiz {


    @Override
    public Observable<DailyModule> onLoadDailyData(int year, int month, int day) {
        GankDaily gankDaily = RetrofitManager.getInstance().getRetrofit().create(GankDaily.class);
        Observable<DailyModule> obs = gankDaily.loadDailyData(year, month, day);
        return obs;
    }

    @Override
    public Observable<GankDataModule> onLoadGankDataByType(String type, int size, int page) {
        GankData gankData = RetrofitManager.getInstance().getRetrofit().create(GankData.class);
        Observable<GankDataModule> obs = gankData.loadGankData(type, size, page);
        return obs;
    }

    @Override
    public void onLoadWelfareData(int start, int count) {

    }

    @Override
    public void onSwitchType(QuietPageType type) {
        if (type == null) {
            return;
        }
        switch (type) {
            case QUIET_DAILY_TYPE:
                break;
            case QUIET_WELFARE_TYPE:
                break;
        }
    }
}
