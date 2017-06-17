package id.co.blogspot.tutor93.popularmovie.utility;

import android.content.res.Resources;
import android.util.DisplayMetrics;
/**
 * Created by indraaguslesmana on 6/16/17.
 */

public class Helper {

    public static String getComplateImageUrl(String backdrop_path, @Constant.PosterSize String size) {
        return Constant.BASE_URL_IMAGE + size + "/" + backdrop_path;
    }

    /**
     * Return true if the width in DP of the device is equal or greater than the given value
     */
    public static boolean isScreenW(int widthDp) {
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float screenWidth = displayMetrics.widthPixels / displayMetrics.density;
        return screenWidth >= widthDp;
    }
}
