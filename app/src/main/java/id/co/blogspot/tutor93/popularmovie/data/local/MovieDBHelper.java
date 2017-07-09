package id.co.blogspot.tutor93.popularmovie.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract.MovieEntry;

/**
 * Created by indraaguslesmana on 7/9/17.
 */

public class MovieDBHelper extends SQLiteOpenHelper{
    /**
     * schema change, You Must change database version!!!
     * */
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "movies.db";


    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    private static final String SQL_CREATE_TABLE =
            "CREATE TABLE " + MovieEntry.TABLE_NAME + " ("+
                    MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    MovieEntry.COLUMN_MOVIE_VOTECOUNT + " INTEGER NOT NULL, " +
                    MovieEntry.COLUMN_MOVIE_VIDEO + " TEXT, " +
                    MovieEntry.COLUMN_MOVIE_VOTEAVERAGE + " REAL NOT NULL, " +
                    MovieEntry.COLUMN_MOVIE_TITLE + " TEXT, " +
                    MovieEntry.COLUMN_MOVIE_POPULARITY + " REAL NOT NULL, " +
                    MovieEntry.COLUMN_MOVIE_POSTERPATH + " TEXT, " +
                    MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE + " TEXT, " +
                    MovieEntry.COLUMN_MOVIE_ORIGINALTITLE + " TEXT, " +
//                    MovieEntry.COLUMN_MOVIE_GENREIDS + " TEXT, " +
                    MovieEntry.COLUMN_MOVIE_BACKDROPPATH + " TEXT, " +
                    MovieEntry.COLUMN_MOVIE_ADULT + " TEXT, " +
                    MovieEntry.COLUMN_MOVIE_OVERVIEW + " TEXT, " +
                    MovieEntry.COLUMN_MOVIE_RELEASEDATE + " TEXT);";

    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + MovieEntry.TABLE_NAME;
}
