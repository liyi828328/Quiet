package perseverance.li.quiet.detail.presenter;

import perseverance.li.quiet.bean.DailyModule;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/20 14:34
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/20 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface IDailyDetailPresenter {

    /**
     * 将每日数据转换过
     *
     * @param data
     */
    void transitionData(DailyModule data);
}
