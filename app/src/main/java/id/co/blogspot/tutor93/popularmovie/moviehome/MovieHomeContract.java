package id.co.blogspot.tutor93.popularmovie.moviehome;

import id.co.blogspot.tutor93.popularmovie.base.RemoteView;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public interface MovieHomeContract {

    interface ViewActions {

        void onInitialListRequested(String token);

    }

    interface MovielistView extends RemoteView {

        void showMovielist();
    }
}
