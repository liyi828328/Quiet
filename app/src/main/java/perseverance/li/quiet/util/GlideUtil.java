package perseverance.li.quiet.util;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import perseverance.li.quiet.R;

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

    /**
     * glide 加载图片
     *
     * @param activity
     * @param view
     * @param url
     */
    public static void displayUrl(Activity activity, ImageView view, String url) {
        if (view == null) {
            throw new IllegalArgumentException("glide display url , view is null ");
        }
        //如果activity已经销毁，不在加载
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }

        Glide.with(activity)
                .load(url)
                .apply(getGlideOptions())
                .thumbnail(0.5f)
                .into(view);
    }

    /**
     * 获取glide options
     *
     * @return
     */
    public static RequestOptions getGlideOptions() {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.img_default_gray)
                .error(R.mipmap.img_load_error)
                .priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.ALL);
        return options;
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
