package id.co.blogspot.tutor93.popularmovie;

import android.app.Application;
import android.content.SharedPreferences;

/**
 * Created by indraaguslesmana on 6/17/17.
 */

public class PopularMovie extends Application {

    private static SharedPreferences sPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        sPreferences = getSharedPreferences(getPackageName(), MODE_PRIVATE);
    }

    public static SharedPreferences getsPreferences() {
        return sPreferences;
    }
}
