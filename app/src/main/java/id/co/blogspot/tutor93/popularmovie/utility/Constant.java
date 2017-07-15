package id.co.blogspot.tutor93.popularmovie.utility;

import android.support.annotation.IntDef;
import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract.MovieEntry;

import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * Created by indraaguslesmana on 6/16/17.
 */

public class Constant {

    /**
     * https://www.themoviedb.org/talk/53c11d4ec3a3684cf4006400?language=en
     * "poster_sizes": [
     * */
    public static final String POSTERSIZE_w92 = "w92";
    public static final String POSTERSIZE_w154 = "w154";
    public static final String POSTERSIZE_w185 = "w185";
    public static final String POSTERSIZE_w342 = "w342";
    public static final String POSTERSIZE_w500 = "w500";
    public static final String POSTERSIZE_w780 = "w780";

    @Retention(SOURCE)
    @StringDef({
            POSTERSIZE_w92,
            POSTERSIZE_w154,
            POSTERSIZE_w185,
            POSTERSIZE_w342,
            POSTERSIZE_w500,
            POSTERSIZE_w780
    })
    public @interface PosterSize {}

    public static final String BASE_URL_IMAGE = "https://image.tmdb.org/t/p/";
    public static final String YOUTUBE_THUMBNAIL_URL = "http://img.youtube.com/vi/%1$s/0.jpg";
    public static final String YOUTUBE_VIDEO_URL = "https://www.youtube.com/watch?v=%1$s";

    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top rated";

    /** Projection */
    public static final String [] PROJECTION_ALL_COLUMN = {
            MovieEntry._ID,
            MovieEntry.COLUMN_MOVIE_ID,
            MovieEntry.COLUMN_MOVIE_VOTECOUNT,
            MovieEntry.COLUMN_MOVIE_VIDEO,
            MovieEntry.COLUMN_MOVIE_VOTEAVERAGE,
            MovieEntry.COLUMN_MOVIE_TITLE,
            MovieEntry.COLUMN_MOVIE_POPULARITY,
            MovieEntry.COLUMN_MOVIE_POSTERPATH,
            MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE,
            MovieEntry.COLUMN_MOVIE_ORIGINALTITLE,
            MovieEntry.COLUMN_MOVIE_BACKDROPPATH,
            MovieEntry.COLUMN_MOVIE_ADULT,
            MovieEntry.COLUMN_MOVIE_OVERVIEW,
            MovieEntry.COLUMN_MOVIE_RELEASEDATE
    };

    public static final int COLUMN_MOVIE_ID = 1;
    public static final int COLUMN_MOVIE_VOTECOUNT = 2;
    public static final int COLUMN_MOVIE_VIDEO = 3;
    public static final int COLUMN_MOVIE_VOTEAVERAGE = 4;
    public static final int COLUMN_MOVIE_TITLE = 5;
    public static final int COLUMN_MOVIE_POPULARITY = 6;
    public static final int COLUMN_MOVIE_POSTERPATH = 7;
    public static final int COLUMN_MOVIE_ORIGINALLANGUAGE = 8;
    public static final int COLUMN_MOVIE_ORIGINALTITLE = 9;
    public static final int COLUMN_MOVIE_BACKDROPPATH = 10;
    public static final int COLUMN_MOVIE_ADULT = 11;
    public static final int COLUMN_MOVIE_OVERVIEW = 12;
    public static final int COLUMN_MOVIE_RELEASEDATE = 13;

    @Retention(SOURCE)
    @IntDef({
            COLUMN_MOVIE_ID,
            COLUMN_MOVIE_VOTECOUNT,
            COLUMN_MOVIE_VIDEO,
            COLUMN_MOVIE_VOTEAVERAGE,
            COLUMN_MOVIE_TITLE,
            COLUMN_MOVIE_POPULARITY,
            COLUMN_MOVIE_POSTERPATH,
            COLUMN_MOVIE_ORIGINALLANGUAGE,
            COLUMN_MOVIE_ORIGINALTITLE,
            COLUMN_MOVIE_BACKDROPPATH,
            COLUMN_MOVIE_ADULT,
            COLUMN_MOVIE_OVERVIEW,
            COLUMN_MOVIE_RELEASEDATE
    })
    public @interface ColumnIndex {}
}
