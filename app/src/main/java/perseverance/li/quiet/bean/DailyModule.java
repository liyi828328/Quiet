package perseverance.li.quiet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

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
public class DailyModule implements Parcelable {

    @SerializedName("results")
    public DailyResults results;

    @SerializedName("category")
    public ArrayList<String> category;

    public static class DailyResults implements Parcelable {

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(this.welfareData);
            dest.writeTypedList(this.androidData);
            dest.writeTypedList(this.iosData);
            dest.writeTypedList(this.jsData);
            dest.writeTypedList(this.videoData);
            dest.writeTypedList(this.resourcesData);
            dest.writeTypedList(this.appData);
            dest.writeTypedList(this.recommendData);
        }

        public DailyResults() {
        }

        protected DailyResults(Parcel in) {
            this.welfareData = in.createTypedArrayList(BaseGankData.CREATOR);
            this.androidData = in.createTypedArrayList(BaseGankData.CREATOR);
            this.iosData = in.createTypedArrayList(BaseGankData.CREATOR);
            this.jsData = in.createTypedArrayList(BaseGankData.CREATOR);
            this.videoData = in.createTypedArrayList(BaseGankData.CREATOR);
            this.resourcesData = in.createTypedArrayList(BaseGankData.CREATOR);
            this.appData = in.createTypedArrayList(BaseGankData.CREATOR);
            this.recommendData = in.createTypedArrayList(BaseGankData.CREATOR);
        }

        public static final Parcelable.Creator<DailyResults> CREATOR = new Parcelable.Creator<DailyResults>() {
            @Override
            public DailyResults createFromParcel(Parcel source) {
                return new DailyResults(source);
            }

            @Override
            public DailyResults[] newArray(int size) {
                return new DailyResults[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.results, flags);
        dest.writeStringList(this.category);
    }

    public DailyModule() {
    }

    protected DailyModule(Parcel in) {
        this.results = in.readParcelable(DailyResults.class.getClassLoader());
        this.category = in.createStringArrayList();
    }

    public static final Parcelable.Creator<DailyModule> CREATOR = new Parcelable.Creator<DailyModule>() {
        @Override
        public DailyModule createFromParcel(Parcel source) {
            return new DailyModule(source);
        }

        @Override
        public DailyModule[] newArray(int size) {
            return new DailyModule[size];
        }
    };
}
