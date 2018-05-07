package perseverance.li.quiet.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * ---------------------------------------------------------------
 * Author: LiYi
 * Create: 2017/6/1 09:35
 * ---------------------------------------------------------------
 * Describe:
 * ---------------------------------------------------------------
 * Changes:
 * ---------------------------------------------------------------
 * 2017/6/1 09 : Create by LiYi
 * ---------------------------------------------------------------
 */
public class BaseGankData implements Parcelable {

    //id
    @SerializedName("_id")
    public String id;
    //发布人
    @SerializedName("who")
    public String who;
    //发布时间
    @SerializedName("publishedAt")
    public Date publishedAt;
    //标题
    @SerializedName("desc")
    public String desc;
    //类型， 一般都是"福利"
    @SerializedName("type")
    public String type;
    //图片url
    @SerializedName("url")
    public String url;
    //是否可用
    @SerializedName("used")
    public Boolean used;
    //对象id
    @SerializedName("objectId")
    public String objectId;
    //创建时间
    @SerializedName("createdAt")
    public Date createdAt;
    //更新时间
    @SerializedName("updatedAt")
    public Date updatedAt;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.who);
        dest.writeLong(this.publishedAt != null ? this.publishedAt.getTime() : -1);
        dest.writeString(this.desc);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeValue(this.used);
        dest.writeString(this.objectId);
        dest.writeLong(this.createdAt != null ? this.createdAt.getTime() : -1);
        dest.writeLong(this.updatedAt != null ? this.updatedAt.getTime() : -1);
    }

    public BaseGankData() {
    }

    protected BaseGankData(Parcel in) {
        this.id = in.readString();
        this.who = in.readString();
        long tmpPublishedAt = in.readLong();
        this.publishedAt = tmpPublishedAt == -1 ? null : new Date(tmpPublishedAt);
        this.desc = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.objectId = in.readString();
        long tmpCreatedAt = in.readLong();
        this.createdAt = tmpCreatedAt == -1 ? null : new Date(tmpCreatedAt);
        long tmpUpdatedAt = in.readLong();
        this.updatedAt = tmpUpdatedAt == -1 ? null : new Date(tmpUpdatedAt);
    }

    public static final Parcelable.Creator<BaseGankData> CREATOR = new Parcelable.Creator<BaseGankData>() {
        @Override
        public BaseGankData createFromParcel(Parcel source) {
            return new BaseGankData(source);
        }

        @Override
        public BaseGankData[] newArray(int size) {
            return new BaseGankData[size];
        }
    };
}
