package id.co.blogspot.tutor93.popularmovie.moviehome;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.base.RemoteView;
import id.co.blogspot.tutor93.popularmovie.data.model.MoviePopularResults;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public interface MovieHomeContract {

    interface ViewActions {

        void onInitialListRequested(String language, String page, String region);

    }

    interface MovielistView extends RemoteView {

        void showMovielist(List<MoviePopularResults> moviePopularResultses);
    }
}
