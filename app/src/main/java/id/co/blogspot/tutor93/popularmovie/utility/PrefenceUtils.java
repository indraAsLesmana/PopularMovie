package id.co.blogspot.tutor93.popularmovie.utility;

import android.content.Context;
import android.content.SharedPreferences;

import id.co.blogspot.tutor93.popularmovie.PopularMovie;
import id.co.blogspot.tutor93.popularmovie.R;

/**
 * Created by indraaguslesmana on 6/17/17.
 */

public class PrefenceUtils {

    public static void setSaveUserConfig(Context context, String movieSortBy) {
        SharedPreferences.Editor editor = PopularMovie.getsPreferences().edit();
        // save sortby config
        editor.putString(context.getString(R.string.def_sortby_key), movieSortBy);
        editor.apply();
    }

    public static String getSinglePrefrenceString(Context context, int prefereceKeyName) {
        String result = null;
        SharedPreferences dataPreferece = PopularMovie.getsPreferences();
        switch (prefereceKeyName) {
            case R.string.def_sortby_key:
                result = dataPreferece.getString(context.getString(R.string.def_sortby_key),
                        context.getString(R.string.def_sortby_key));
                break;
        }
        return result;
    }
}
