package perseverance.li.quiet.home.gank;

import io.reactivex.Observable;
import io.reactivex.Observer;
import perseverance.li.quiet.home.model.DailyModule;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/31 17:04
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/31 17 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface GankDaily {

    @GET("day/{year}/{month}/{day}")
    Observable<DailyModule> loadDailyData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

}
