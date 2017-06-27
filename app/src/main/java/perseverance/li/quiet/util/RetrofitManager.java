package perseverance.li.quiet.util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/31 17:22
 * ---------------------------------------------------------------
 * Describe:
 * Retrofit 管理类
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/31 17 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class RetrofitManager {

    private static volatile RetrofitManager mInstance;
    private static final long DEFAULT_TIMEOUT = 5;
    private static Retrofit mRetrofit;

    private RetrofitManager() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(GankApi.BASE_URL)
                //数据转换解析
                .addConverterFactory(GsonConverterFactory.create())
                //默认call是DefaultCallAdapterFactory 可以自定义修改为Observable rxjava支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    private static synchronized void syncInit() {
        if (mInstance == null) {
            mInstance = new RetrofitManager();
        }
    }

    public static RetrofitManager getInstance() {
        if (mInstance == null) {
            syncInit();
        }
        return mInstance;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }
}
