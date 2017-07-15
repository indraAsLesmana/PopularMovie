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

    @Override
    public void onFavoriteRequested(Cursor newCursor) {
        if (!isViewAttached()) return;
        mView.showProgress();
        if (newCursor == null) {
            return;
        }

        List<MovieResult> movies = new ArrayList<>();

        int positionMovieId = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ID);
        int positionVoteCount = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_VOTECOUNT);
        int positionVideo = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_VIDEO);
        int positionVoteAverage = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_VOTEAVERAGE);
        int positionTitle = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_TITLE);
        int positionPopularity = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_POPULARITY);
        int positionPosterpath = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_POSTERPATH);
        int positionOriginalLanguage = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE);
        int positionOriginalTitle = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ORIGINALTITLE);
        int positionBackdroppath = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_BACKDROPPATH);
        int positionAdult = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_ADULT);
        int positionOverview = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_OVERVIEW);
        int positionReleaseDate = newCursor.getColumnIndex(MovieEntry.COLUMN_MOVIE_RELEASEDATE);

        if (newCursor.moveToFirst()) {
            while(newCursor.moveToNext()) {
                MovieResult movieResult = new MovieResult (
                        newCursor.getInt(positionMovieId),
                        newCursor.getInt(positionVoteCount),
                        Boolean.getBoolean(newCursor.getString(positionVideo)),
                        newCursor.getDouble(positionVoteAverage),
                        newCursor.getString(positionTitle),
                        newCursor.getDouble(positionPopularity),
                        newCursor.getString(positionPosterpath),
                        newCursor.getString(positionOriginalLanguage),
                        newCursor.getString(positionOriginalTitle),
                        null,
                        newCursor.getString(positionBackdroppath),
                        Boolean.getBoolean(newCursor.getString(positionAdult)),
                        newCursor.getString(positionOverview),
                        newCursor.getString(positionReleaseDate));

                movies.add(movieResult);
            }

            mView.hideProgress();
            mView.showMovielist(movies);
        }
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
