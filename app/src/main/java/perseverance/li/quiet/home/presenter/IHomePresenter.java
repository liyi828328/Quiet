package perseverance.li.quiet.home.presenter;

import perseverance.li.quiet.home.model.QuietPageType;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/19 10:19
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/19 10 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface IHomePresenter {

    /**
     * 获取福利
     */
    void loadWelfareData();

    /**
     * 下拉刷新时，获取一定范围时间的每日数据
     */
    void loadDailyListData();

    /**
     * 滑动加载更多
     */
    void loadMoreData();

    /**
     * 切换页面
     *
     * @param type
     */
    void changeLoadDataByType(QuietPageType type);

    /**
     * 返回当前页面类型
     *
     * @return
     */
    QuietPageType getCurrentPageType();
}
