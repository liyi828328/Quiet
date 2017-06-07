package perseverance.li.quiet.home.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import perseverance.li.quiet.base.BaseGankData;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/5/31 16:58
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/5/31 16 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class DailyModule {

    @SerializedName("results")
    public DailyResults results;

    @SerializedName("category")
    public ArrayList<String> category;

    public class DailyResults {

        @SerializedName("福利")
        public ArrayList<BaseGankData> welfareData;

        @SerializedName("Android")
        public ArrayList<BaseGankData> androidData;

        @SerializedName("iOS")
        public ArrayList<BaseGankData> iosData;

        @SerializedName("前端")
        public ArrayList<BaseGankData> jsData;

        @SerializedName("休息视频")
        public ArrayList<BaseGankData> videoData;

        @SerializedName("拓展资源")
        public ArrayList<BaseGankData> resourcesData;

        @SerializedName("App")
        public ArrayList<BaseGankData> appData;

        @SerializedName("瞎推荐")
        public ArrayList<BaseGankData> recommendData;
    }
}
