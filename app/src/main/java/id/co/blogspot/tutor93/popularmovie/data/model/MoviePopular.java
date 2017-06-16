package id.co.blogspot.tutor93.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indraaguslesmana on 6/16/17.
 */

public class MoviePopular implements Parcelable {

    private Integer page;

    @SerializedName("total_results")
    private Integer totalResults;

    @SerializedName("total_pages")
    private Integer totalPages;

    private List<MoviePopularResults> results = null;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.page);
        dest.writeValue(this.totalResults);
        dest.writeValue(this.totalPages);
        dest.writeList(this.results);
    }

    public MoviePopular() {
    }

    protected MoviePopular(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = new ArrayList<MoviePopularResults>();
        in.readList(this.results, MoviePopularResults.class.getClassLoader());
    }

    public static final Parcelable.Creator<MoviePopular> CREATOR = new Parcelable.Creator<MoviePopular>() {
        @Override
        public MoviePopular createFromParcel(Parcel source) {
            return new MoviePopular(source);
        }

        @Override
        public MoviePopular[] newArray(int size) {
            return new MoviePopular[size];
        }
    };
}
