package id.co.blogspot.tutor93.popularmovie;

import android.app.Application;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import id.co.blogspot.tutor93.popularmovie.data.local.MovieDBHelper;

/**
 * Created by indraaguslesmana on 6/17/17.
 */

public class PopularMovie extends Application {

    private static SharedPreferences sPreferences;
    private static SQLiteDatabase mDb;

    @Override
    public void onCreate() {
        super.onCreate();
        sPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);

        MovieDBHelper dbHelper = new MovieDBHelper(this);
        mDb = dbHelper.getWritableDatabase();
    }

    @Override
    public void onTerminate() {
        mDb.close();
        super.onTerminate();
    }

    public static SharedPreferences getsPreferences() {
        return sPreferences;
    }

    public static SQLiteDatabase getmDb() {
        return mDb;
    }
}
