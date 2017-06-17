package id.co.blogspot.tutor93.popularmovie.moviehome;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.base.RemoteView;
import id.co.blogspot.tutor93.popularmovie.data.model.MoviePopularResults;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public interface MovieHomeContract {

    interface ViewActions {

        void onInitialListRequested(String sortBy);

    }

    interface MovielistView extends RemoteView {

        void showMovielist(List<MoviePopularResults> moviePopularResultses);
    }
}
