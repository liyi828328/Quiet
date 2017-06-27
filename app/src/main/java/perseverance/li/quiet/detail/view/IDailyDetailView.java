package perseverance.li.quiet.detail.view;

import java.util.List;

import perseverance.li.quiet.base.IBaseView;
import perseverance.li.quiet.bean.BaseGankData;

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
public interface IDailyDetailView extends IBaseView {

    /**
     * 将数据展示到view上
     *
     * @param dataList
     */
    void onLoadDataToView(List<BaseGankData> dataList);

}
