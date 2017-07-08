package id.co.blogspot.tutor93.popularmovie.utility;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;

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

    public static final String POPULAR = "popular";
    public static final String TOP_RATED = "top rated";
}
