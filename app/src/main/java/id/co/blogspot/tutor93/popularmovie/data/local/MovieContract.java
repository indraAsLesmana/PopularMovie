package id.co.blogspot.tutor93.popularmovie.data.local;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by indraaguslesmana on 7/9/17.
 */

public final class MovieContract {

    public static final String CONTENT_AUTHORITY = "id.co.blogspot.tutor93.popularmovie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_MOVIES = "movies";
    public static final String PATH_MOVIES_ID = "movies/#";

    public MovieContract() {
    }

    public static final class MovieEntry implements BaseColumns {
        /**
         * table name
         * */
        public static final String TABLE_NAME = "movies";

        /**
         * table column
         * */
        public static final String _ID = BaseColumns._ID;

        public static final String COLUMN_MOVIE_ID = "movieId";
        public static final String COLUMN_MOVIE_VOTECOUNT = "voteCount";
        public static final String COLUMN_MOVIE_VIDEO = "video";
        public static final String COLUMN_MOVIE_VOTEAVERAGE = "voteAverage";
        public static final String COLUMN_MOVIE_TITLE = "title";
        public static final String COLUMN_MOVIE_POPULARITY = "popularity";
        public static final String COLUMN_MOVIE_POSTERPATH = "posterPath";
        public static final String COLUMN_MOVIE_ORIGINALLANGUAGE = "originalLanguage";
        public static final String COLUMN_MOVIE_ORIGINALTITLE = "originalTitle";
        public static final String COLUMN_MOVIE_BACKDROPPATH = "backdropPath";
        public static final String COLUMN_MOVIE_ADULT = "adult";
        public static final String COLUMN_MOVIE_OVERVIEW = "overview";
        public static final String COLUMN_MOVIE_RELEASEDATE = "releaseDate";

        public static final String MOVIE_VALUE_TRUE = "true";
        public static final String MOVIE_VALUE_FALSE = "false";

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_MOVIES);

        /**
         * The MIME type of the {@link #CONTENT_URI} for a list of movies.
         */
        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        /**
         * The MIME type of the {@link #CONTENT_URI} for a single movie.
         */
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIES;

        public static boolean isValidMovieValue(String value) {
            boolean result = false;
            if (value.equals(MOVIE_VALUE_TRUE) || value.equals(MOVIE_VALUE_FALSE)) {
                result = true;
            }
            return result;
        }

    }
}
