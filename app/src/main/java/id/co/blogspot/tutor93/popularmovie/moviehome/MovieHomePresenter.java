package id.co.blogspot.tutor93.popularmovie.moviehome;

import android.database.Cursor;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.tutor93.popularmovie.base.BasePresenter;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.Movies;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.data.network.RemoteCallback;
import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract.MovieEntry;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public class MovieHomePresenter extends BasePresenter<MovieHomeContract.MovielistView>
        implements MovieHomeContract.ViewActions {

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

    @Override
    public void onFavoriteRequested(Cursor newCursor) {
        if (!isViewAttached()) return;
        mView.showProgress();
        if (newCursor == null) {
            mView.hideProgress();
            mView.showError("you dont have any favorite");
            return;
        }

        List<MovieResult> movies = new ArrayList<>();

        while (newCursor.moveToNext()) {
            MovieResult movieResult = new MovieResult(
                    newCursor.getInt(Constant.COLUMN_MOVIE_ID),
                    newCursor.getInt(Constant.COLUMN_MOVIE_VOTECOUNT),
                    Boolean.getBoolean(newCursor.getString(Constant.COLUMN_MOVIE_VIDEO)),
                    newCursor.getDouble(Constant.COLUMN_MOVIE_VOTEAVERAGE),
                    newCursor.getString(Constant.COLUMN_MOVIE_TITLE),
                    newCursor.getDouble(Constant.COLUMN_MOVIE_POPULARITY),
                    newCursor.getString(Constant.COLUMN_MOVIE_POSTERPATH),
                    newCursor.getString(Constant.COLUMN_MOVIE_ORIGINALLANGUAGE),
                    newCursor.getString(Constant.COLUMN_MOVIE_ORIGINALTITLE),
                    null,
                    newCursor.getString(Constant.COLUMN_MOVIE_BACKDROPPATH),
                    Boolean.getBoolean(newCursor.getString(Constant.COLUMN_MOVIE_ADULT)),
                    newCursor.getString(Constant.COLUMN_MOVIE_OVERVIEW),
                    newCursor.getString(Constant.COLUMN_MOVIE_RELEASEDATE));

            movies.add(movieResult);
        }

        mView.hideProgress();
        mView.showMovielist(movies);
    }

    private void getMoviePopular() {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mDataManager.getPopularMovies(new RemoteCallback<Movies>() {
            @Override
            public void onSuccess(Movies response) {
                mView.hideProgress();
                if (!isViewAttached()) return;

                List<MovieResult> responseResults = response.results;
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
        mDataManager.getTopratedMovies(new RemoteCallback<Movies>() {
            @Override
            public void onSuccess(Movies response) {
                mView.hideProgress();
                if (!isViewAttached()) return;
                List<MovieResult> responseResults = response.results;
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
