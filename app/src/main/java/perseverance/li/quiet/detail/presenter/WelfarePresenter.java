package perseverance.li.quiet.detail.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.Target;

import perseverance.li.quiet.R;
import perseverance.li.quiet.base.BasePresenter;
import perseverance.li.quiet.detail.view.IWelfareDetailView;
import perseverance.li.quiet.util.GlideUtil;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/13 14:02
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/13 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class WelfarePresenter extends BasePresenter<IWelfareDetailView> {

    public void saveWelfarePicture(String imageUrl) {
    }

    /**
     * 加载图片
     *
     * @param activity
     * @param view
     * @param url
     */
    public void loadWelfarePicture(final Activity activity, ImageView view, String url) {
        //如果activity已经销毁，不在加载
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            getBaseView().onLoadFailure(new RuntimeException("activity is finishing"));
            return;
        }

        Glide.with(activity)
                .load(url)
                .listener(new RequestListener<Drawable>() {

                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target,
                                                boolean isFirstResource) {
                        getBaseView().onLoadFailure(new RuntimeException("glide load fail"));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target,
                                                   DataSource dataSource, boolean isFirstResource) {
                        getBaseView().onLoadImageSuccess();
                        return false;
                    }
                })
                .apply(GlideUtil.getGlideOptions())
                .thumbnail(0.5f)
                .into(view);

    }

}
