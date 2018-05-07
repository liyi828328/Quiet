package perseverance.li.quiet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/6 15:54
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/6 15 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class GankDataModule implements Parcelable {

    @SerializedName("results")
    public ArrayList<BaseGankData> results;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.results);
    }

    public GankDataModule() {
    }

    protected GankDataModule(Parcel in) {
        this.results = in.createTypedArrayList(BaseGankData.CREATOR);
    }

    public static final Parcelable.Creator<GankDataModule> CREATOR = new Parcelable.Creator<GankDataModule>() {
        @Override
        public GankDataModule createFromParcel(Parcel source) {
            return new GankDataModule(source);
        }

        @Override
        public GankDataModule[] newArray(int size) {
            return new GankDataModule[size];
        }
    };
}
