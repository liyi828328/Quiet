package perseverance.li.quiet.gank;

import io.reactivex.Observable;
import perseverance.li.quiet.bean.DailyModule;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/31 17:04
 * ---------------------------------------------------------------
 * Describe:
 * 每日数据请求接口
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/31 17 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface GankDaily {

    /**
     * 获取每日福利
     *
     * @param year
     * @param month
     * @param day
     * @return Observable<DailyModule>
     */
    @GET("day/{year}/{month}/{day}")
    Observable<DailyModule> loadDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day);
}
