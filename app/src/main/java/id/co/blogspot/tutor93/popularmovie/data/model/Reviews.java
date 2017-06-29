package id.co.blogspot.tutor93.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indraaguslesmana on 6/30/17.
 */

public class Reviews implements Parcelable {

    public Integer id;

    public Integer page;

    public List<ReviewResult> results = null;

    @SerializedName("total_pages")
    @Expose
    public Integer totalPages;

    @SerializedName("total_results")
    @Expose
    public Integer totalResults;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.page);
        dest.writeList(this.results);
        dest.writeValue(this.totalPages);
        dest.writeValue(this.totalResults);
    }

    public Reviews() {
    }

    protected Reviews(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = new ArrayList<ReviewResult>();
        in.readList(this.results, ReviewResult.class.getClassLoader());
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel source) {
            return new Reviews(source);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}
