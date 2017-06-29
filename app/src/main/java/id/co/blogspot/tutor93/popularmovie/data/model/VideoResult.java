package id.co.blogspot.tutor93.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by indraaguslesmana on 6/30/17.
 */

public class VideoResult implements Parcelable {

    public String id;

    @SerializedName("iso_639_1")
    @Expose
    public String iso6391;

    @SerializedName("iso_3166_1")
    @Expose
    public String iso31661;

    public String key;

    public String name;

    public String site;

    public Integer size;

    public String type;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.iso6391);
        dest.writeString(this.iso31661);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeValue(this.size);
        dest.writeString(this.type);
    }

    public VideoResult() {
    }

    protected VideoResult(Parcel in) {
        this.id = in.readString();
        this.iso6391 = in.readString();
        this.iso31661 = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = (Integer) in.readValue(Integer.class.getClassLoader());
        this.type = in.readString();
    }

    public static final Parcelable.Creator<VideoResult> CREATOR = new Parcelable.Creator<VideoResult>() {
        @Override
        public VideoResult createFromParcel(Parcel source) {
            return new VideoResult(source);
        }

        @Override
        public VideoResult[] newArray(int size) {
            return new VideoResult[size];
        }
    };
}
