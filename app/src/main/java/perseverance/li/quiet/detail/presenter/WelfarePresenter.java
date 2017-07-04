package perseverance.li.quiet.detail.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;
import com.liulishuo.filedownloader.model.FileDownloadStatus;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import perseverance.li.quiet.QuietApplication;
import perseverance.li.quiet.base.BasePresenter;
import perseverance.li.quiet.detail.view.IWelfareDetailView;
import perseverance.li.quiet.util.GlideUtil;
import perseverance.li.quiet.util.Util;

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
public class WelfarePresenter extends BasePresenter<IWelfareDetailView> implements IWelfarePresenter {

    private static final String TAG = "WelfarePresenter";
    private static final String FILE_FOLDER = "/quiet/";
    private int mDownloadId;
    private String mDownloadFilePath;
    private Bitmap mBitmap;

    @Override
    public void loadWelfarePicture(final Activity activity, ImageView view, String url) {
        //如果activity已经销毁，不在加载
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            mView.onLoadFailure(new RuntimeException("activity is finishing"));
            return;
        }

        Glide.with(activity)
                .asBitmap()
                .load(url)
                .listener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        if (mView != null) {
                            mView.onLoadFailure(new RuntimeException("glide load fail"));
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        mBitmap = resource;
                        if (mView != null) {
                            mView.onLoadImageSuccess();
                        }
                        return false;
                    }
                })
                .apply(GlideUtil.getGlideOptions())
                .thumbnail(0.5f)
                .into(view);
    }

    @Override
    public void downloadPicture(String imageUrl) {
        mDownloadFilePath = getFilePath(imageUrl);
        if (TextUtils.isEmpty(mDownloadFilePath)) {
            if (mView != null) {
                mView.onDownloadImageFail();
            }
            return;
        }
        Log.d(TAG, "download filePath: " + mDownloadFilePath);
        mDownloadId = FileDownloader.getImpl().create(imageUrl)
                .setPath(mDownloadFilePath)
                .setListener(new FileDownloadListener() {

                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d(TAG, "pending");
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Thread t = Thread.currentThread();
                        float progress = ((float) soFarBytes / (float) totalBytes) * 100;
                        Log.d(TAG, "progress soFarBytes: " + soFarBytes
                                + " totalBytes: " + totalBytes + "tId: "
                                + t.getId() + " threadName: " + t.getName() + " progress: " + progress);
                        if (mView != null) {
                            mView.onDownloadProgress(progress);
                        }

                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        Log.d(TAG, "completed");
                        if (mView != null) {
                            mView.onDownloadProgress(100);
                            mView.onDownloadImageSuccess(mDownloadFilePath);
                        }

                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        Log.d(TAG, "paused");
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        Log.d(TAG, "error msg: " + e.getMessage());
                        if (mView != null) {
                            mView.onDownloadImageFail();
                        }
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        Log.d(TAG, "warn");
                    }
                }).start();
    }

    @Override
    public Bitmap resetWelfarePicture() {
        return mBitmap;
    }

    @Override
    public boolean sendPictureToWx() {
        WXImageObject imgObj = new WXImageObject(mBitmap);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = Util.buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        return QuietApplication.getWxAPi().sendReq(req);
    }


    /**
     * 获取文件保存的路径
     *
     * @param imageUrl
     * @return
     */
    private String getFilePath(String imageUrl) {
        if (TextUtils.isEmpty(imageUrl)) {
            return null;
        }

        String fileName = "";
        int position = imageUrl.lastIndexOf("/");
        if (position >= 0) {
            fileName = imageUrl.substring(position + 1);
        }

        return Environment.getExternalStorageDirectory().getPath() + FILE_FOLDER + fileName;
    }

    @Override
    public void destory() {
        super.destory();
        //如果不是完成状态，界面退出时暂停下载
        byte downloadStatus = FileDownloader.getImpl().getStatus(mDownloadId, mDownloadFilePath);
        if (FileDownloadStatus.completed != downloadStatus) {
            FileDownloader.getImpl().pause(mDownloadId);
        }
        FileDownloader.getImpl().unBindService();
    }
}
