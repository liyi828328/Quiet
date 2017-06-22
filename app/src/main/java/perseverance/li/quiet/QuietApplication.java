package perseverance.li.quiet;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/22 14:52
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/22 14 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class QuietApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 文件下载工具初始化
         */
        FileDownloader.init(getApplicationContext());
    }
}
