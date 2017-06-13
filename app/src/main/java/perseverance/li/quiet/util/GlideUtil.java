package perseverance.li.quiet.util;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/5 17:44
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/5 17 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class GlideUtil {

    public static void displayUrl(Activity activity, ImageView view, String url, @DrawableRes int defaultImage) {
        if (view == null) {
            throw new IllegalArgumentException("glide display url , view is null ");
        }
        //如果activity已经销毁，不在加载
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(defaultImage)
                .error(defaultImage)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(activity)
                .load(url)
                .apply(options)
                .thumbnail(0.5f)
                .into(view);
    }

    /**
     * 销毁glide
     *
     * @param activity
     */
    public static void clearGlideMemory(Activity activity) {
        if (activity == null) {
            return;
        }
        Glide.get(activity).clearMemory();
    }
}
