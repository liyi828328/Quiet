package perseverance.li.quiet.web;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.DownloadListener;

import java.lang.ref.WeakReference;

/**
 * -------------------------------------------------------------------------
 * Author: yi.li
 * Create: 2018/8/30
 * -------------------------------------------------------------------------
 * Describe:
 * -------------------------------------------------------------------------
 * Changes:
 * -------------------------------------------------------------------------
 * 2018/8/30 : yi.li
 * -------------------------------------------------------------------------
 */
public class WebDownloadListener implements DownloadListener {

    private WeakReference<Activity> mActivity;

    public WebDownloadListener(Activity activity) {
        this.mActivity = new WeakReference<>(activity);
    }

    @Override
    public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (mActivity != null && mActivity.get() != null) {
                mActivity.get().startActivity(intent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onDestroy() {
        if (mActivity != null) {
            mActivity.clear();
            mActivity = null;
        }
    }
}
