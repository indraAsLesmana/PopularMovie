package id.co.blogspot.tutor93.popularmovie.moviehome;

import android.support.annotation.NonNull;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.base.BasePresenter;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.MoviePopular;
import id.co.blogspot.tutor93.popularmovie.data.model.MoviePopularResults;
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
        if (sortBy.equals("popular")) {
            getMoviePopular();
        } else {
            getMovieToprated();
        }
    }

    private void getMoviePopular() {
         mDataManager.getPopularMovies(new RemoteCallback<MoviePopular>() {
             @Override
             public void onSuccess(MoviePopular response) {
                 if (!isViewAttached()) return;
                 mView.hideProgress();
                 List<MoviePopularResults> responseResults = response.results;
                 if (responseResults.isEmpty()) {
                     mView.showEmpty();
                     return;
                 }

                 mView.showMovielist(responseResults);
             }

             @Override
             public void onUnauthorized() {

             }

             @Override
             public void onFailed(Throwable throwable) {

             }
         });
    }

    private void getMovieToprated() {
         mDataManager.getTopratedMovies(new RemoteCallback<MoviePopular>() {
             @Override
             public void onSuccess(MoviePopular response) {
                 if (!isViewAttached()) return;
                 mView.hideProgress();
                 List<MoviePopularResults> responseResults = response.results;
                 if (responseResults.isEmpty()) {
                     mView.showEmpty();
                     return;
                 }

                 mView.showMovielist(responseResults);
             }

             @Override
             public void onUnauthorized() {

             }

             @Override
             public void onFailed(Throwable throwable) {

             }
         });
    }
}
