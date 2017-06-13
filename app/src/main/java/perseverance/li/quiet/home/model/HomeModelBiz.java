package perseverance.li.quiet.home.model;

import io.reactivex.Observable;
import perseverance.li.quiet.bean.DailyModule;
import perseverance.li.quiet.bean.GankDataModule;
import perseverance.li.quiet.gank.GankDaily;
import perseverance.li.quiet.gank.GankData;
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
}
