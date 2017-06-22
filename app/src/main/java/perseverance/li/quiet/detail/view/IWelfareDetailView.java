package perseverance.li.quiet.detail.view;

import android.provider.Settings;

import perseverance.li.quiet.base.IBaseView;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/13 14:01
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/13 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public interface IWelfareDetailView extends IBaseView {

    /**
     * 下周进度
     *
     * @param progress
     */
    void onDownloadProgress(float progress);

    /**
     * 保存图片失败
     */
    void onDownloadImageFail();

    /**
     * 下载成功
     *
     * @param filePath
     */
    void onDownloadImageSuccess(String filePath);

    /**
     * 图片加载成功
     */
    void onLoadImageSuccess();
}
