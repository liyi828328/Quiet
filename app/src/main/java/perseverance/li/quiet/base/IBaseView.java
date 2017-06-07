package perseverance.li.quiet.base;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/27 09:53
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/27 09 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface IBaseView {

    /**
     * 初始化view
     */
    void initView();

    /**
     * View 加载失败
     *
     * @param e
     */
    void onLoadFailure(Throwable e);

}
