package perseverance.li.quiet.home.view;

import java.util.List;

import perseverance.li.quiet.bean.BaseGankData;
import perseverance.li.quiet.base.IBaseView;
import perseverance.li.quiet.bean.DailyModule;
import perseverance.li.quiet.home.model.QuietPageType;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/31 15:22
 * ---------------------------------------------------------------
 * Describe:
 * 首页与Presenter之间通信的接口
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/31 15 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface IHomeView extends IBaseView {

    /**
     * 加载每日精彩
     *
     * @param dailyModules
     */
    void onLoadDailyDatasSuccess(List<DailyModule> dailyModules);

    /**
     * 加载福利
     *
     * @param welfareDatas
     */
    void onLoadWelfareDatasSuccess(List<BaseGankData> welfareDatas);

    /**
     * 根据选中类型切换布局，用来更新RecyclerView的配置
     *
     * @param pageType
     */
    void changeViewByType(QuietPageType pageType);

    /**
     * 敬请期待
     */
    void lookForwardTo();
}
