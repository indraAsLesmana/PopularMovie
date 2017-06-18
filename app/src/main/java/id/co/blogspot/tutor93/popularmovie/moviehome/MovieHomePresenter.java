package id.co.blogspot.tutor93.popularmovie.moviehome;

import android.support.annotation.NonNull;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.base.BasePresenter;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.Movie;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResults;
import id.co.blogspot.tutor93.popularmovie.data.network.RemoteCallback;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public class MovieHomePresenter extends BasePresenter<MovieHomeContract.MovielistView>
        implements MovieHomeContract.ViewActions{

    private final DataManager mDataManager;

    public MovieHomePresenter(@NonNull DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onInitialListRequested(String sortBy) {
        if (sortBy.equalsIgnoreCase("popular")) {
            getMoviePopular();
        } else {
            getMovieToprated();
        }
    }

    private void getMoviePopular() {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mDataManager.getPopularMovies(new RemoteCallback<Movie>() {
             @Override
             public void onSuccess(Movie response) {
                 mView.hideProgress();
                 if (!isViewAttached()) return;
                 List<MovieResults> responseResults = response.results;
                 if (responseResults.isEmpty()) {
                     mView.showEmpty();
                     return;
                 }

                 mView.showMovielist(responseResults);
             }

             @Override
             public void onUnauthorized() {
                 if (!isViewAttached()) return;
                 mView.hideProgress();
                 mView.showUnauthorizedError();

             }

             @Override
             public void onFailed(Throwable throwable) {
                 if (!isViewAttached()) return;
                 mView.hideProgress();
                 mView.showError(throwable.getMessage());

             }
         });
    }

    private void getMovieToprated() {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mDataManager.getTopratedMovies(new RemoteCallback<Movie>() {
             @Override
             public void onSuccess(Movie response) {
                 mView.hideProgress();
                 if (!isViewAttached()) return;
                 List<MovieResults> responseResults = response.results;
                 if (responseResults.isEmpty()) {
                     mView.showEmpty();
                     return;
                 }

                 mView.showMovielist(responseResults);
             }

             @Override
             public void onUnauthorized() {
                 if (!isViewAttached()) return;
                 mView.hideProgress();
                 mView.showUnauthorizedError();
             }

             @Override
             public void onFailed(Throwable throwable) {
                 if (!isViewAttached()) return;
                 mView.hideProgress();
                 mView.showError(throwable.getMessage());
             }
         });
    }
}
