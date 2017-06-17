package id.co.blogspot.tutor93.popularmovie.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by indraaguslesmana on 6/16/17.
 */

public class MovieResults implements Parcelable {

    @SerializedName("vote_count")
    public Integer voteCount;

    public Integer id;

    public Boolean video;

    @SerializedName("vote_average")
    public Double voteAverage;

    @SerializedName("title")
    public String title;

    @SerializedName("popularity")
    public Double popularity;

    @SerializedName("poster_path")
    public String posterPath;

    @SerializedName("original_language")
    public String originalLanguage;

    @SerializedName("original_title")
    public String originalTitle;

    @SerializedName("genre_ids")
    public List<Integer> genreIds = null;

    @SerializedName("backdrop_path")
    public String backdropPath;

    @SerializedName("adult")
    public Boolean adult;

    public String overview;

    @SerializedName("release_date")
    public String releaseDate;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.voteCount);
        dest.writeValue(this.id);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeString(this.title);
        dest.writeValue(this.popularity);
        dest.writeString(this.posterPath);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.originalTitle);
        dest.writeList(this.genreIds);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.adult);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
    }

    public MovieResults() {
    }

    protected MovieResults(Parcel in) {
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.title = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.posterPath = in.readString();
        this.originalLanguage = in.readString();
        this.originalTitle = in.readString();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.backdropPath = in.readString();
        this.adult = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.overview = in.readString();
        this.releaseDate = in.readString();
    }

    public static final Parcelable.Creator<MovieResults> CREATOR = new Parcelable.Creator<MovieResults>() {
        @Override
        public MovieResults createFromParcel(Parcel source) {
            return new MovieResults(source);
        }

        @Override
        public MovieResults[] newArray(int size) {
            return new MovieResults[size];
        }
    };
}

