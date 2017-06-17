package id.co.blogspot.tutor93.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indraaguslesmana on 6/16/17.
 */

public class Movie implements Parcelable {

    public Integer page;

    @SerializedName("total_results")
    public Integer totalResults;

    @SerializedName("total_pages")
    public Integer totalPages;

    public List<MovieResults> results = null;

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

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.page = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalResults = (Integer) in.readValue(Integer.class.getClassLoader());
        this.totalPages = (Integer) in.readValue(Integer.class.getClassLoader());
        this.results = new ArrayList<MovieResults>();
        in.readList(this.results, MovieResults.class.getClassLoader());
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
