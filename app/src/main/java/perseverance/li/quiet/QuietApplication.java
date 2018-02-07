package perseverance.li.quiet;

import android.app.Application;

import com.liulishuo.filedownloader.FileDownloader;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

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

    private static final String WX_APP_ID = "wx35b9746b5e093156";
    private static IWXAPI mWxApi;

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 文件下载工具初始化
         */
        FileDownloader.setup(getApplicationContext());
        regToWx();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }

    private void regToWx() {
        mWxApi = WXAPIFactory.createWXAPI(this, WX_APP_ID, true);
        mWxApi.registerApp(WX_APP_ID);
    }

    public static IWXAPI getWxAPi() {
        return mWxApi;
    }
}
