package perseverance.li.quiet.home.model;

import io.reactivex.Observable;
import perseverance.li.quiet.base.IBaseModel;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/31 15:49
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/31 15 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface IHomeModelBiz extends IBaseModel {


    /**
     * 加载每日新闻
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    Observable<DailyModule> onLoadDailyData(int year, int month, int day);

    /**
     * 根据类型获取数据
     *
     * @param type
     * @param size
     * @param page
     * @return
     */
    Observable<GankDataModule> onLoadGankDataByType(String type, int size, int page);

    /**
     * 加载福利
     *
     * @param start
     * @param count
     */
    void onLoadWelfareData(int start, int count);

    /**
     * 切换数据源
     *
     * @param type
     */
    void onSwitchType(QuietPageType type);
}
