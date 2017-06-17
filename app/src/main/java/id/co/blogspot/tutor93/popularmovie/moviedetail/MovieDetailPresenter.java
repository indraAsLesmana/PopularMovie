package id.co.blogspot.tutor93.popularmovie.moviedetail;

import id.co.blogspot.tutor93.popularmovie.base.BasePresenter;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResults;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.HomeClickView>
        implements MovieDetailContract.ViewActions{

    private DataManager mDataManager;
    private MovieResults mMovieResults;

    public MovieDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

}
