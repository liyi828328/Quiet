package perseverance.li.quiet.detail.presenter;

import android.app.Activity;
import android.widget.ImageView;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/19 11:15
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/19 11 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface IWelfarePresenter {

    /**
     * 加载图片
     *
     * @param activity
     * @param view
     * @param url
     */
    void loadWelfarePicture(final Activity activity, ImageView view, String url);
}
