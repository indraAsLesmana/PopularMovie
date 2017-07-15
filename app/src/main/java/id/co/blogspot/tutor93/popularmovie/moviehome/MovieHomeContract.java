package id.co.blogspot.tutor93.popularmovie.moviehome;

import android.database.Cursor;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.base.RemoteView;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public interface MovieHomeContract {

    interface ViewActions {

        void onInitialListRequested(String sortBy);

        void onFavoriteRequested(Cursor newCursor);
    }

    interface MovielistView extends RemoteView {

        void showMovielist(List<MovieResult> movieResultses);

    }
}
