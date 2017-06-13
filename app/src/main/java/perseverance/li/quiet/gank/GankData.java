package perseverance.li.quiet.gank;

import io.reactivex.Observable;
import perseverance.li.quiet.bean.GankDataModule;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/6 15:46
 * ---------------------------------------------------------------
 * Describe:
 * data 接口请求
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/6 15 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface GankData {

    /**
     * 根据类型获取数据
     *
     * @param type 数据类型 >> 福利、Android、iOS、前端、扩展资源、休息视频
     * @param size 数据个数
     * @param page 第几页
     * @return Observable<GankDataModule>
     */
    @GET("data/{type}/{size}/{page}")
    Observable<GankDataModule> loadGankData(
            @Path("type") String type, @Path("size") int size, @Path("page") int page);
}
