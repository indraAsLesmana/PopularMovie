package id.co.blogspot.tutor93.popularmovie.moviedetail;

import id.co.blogspot.tutor93.popularmovie.base.BasePresenter;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.data.model.Reviews;
import id.co.blogspot.tutor93.popularmovie.data.model.Videos;
import id.co.blogspot.tutor93.popularmovie.data.network.RemoteCallback;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public class MovieDetailPresenter extends BasePresenter<MovieDetailContract.HomeClickView>
        implements MovieDetailContract.ViewActions{

    private DataManager mDataManager;
    private MovieResult mMovieResult;

    public MovieDetailPresenter(DataManager dataManager) {
        mDataManager = dataManager;
    }

    @Override
    public void onPlayTrailerRequest(int id) {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mDataManager.getTrailer(id, new RemoteCallback<Videos>() {
            @Override
            public void onSuccess(Videos response) {
                mView.hideProgress();
            }

            @Override
            public void onUnauthorized() {
                mView.hideProgress();

            }

            @Override
            public void onFailed(Throwable throwable) {
                mView.hideProgress();

            }
        });
    }

    @Override
    public void onShowReviewRequest(int id) {
        if (!isViewAttached()) return;
        mView.showMessageLayout(false);
        mView.showProgress();
        mDataManager.getReview(id, new RemoteCallback<Reviews>() {
            @Override
            public void onSuccess(Reviews response) {
                mView.hideProgress();
                if (response.results != null) {
                    mView.showReview(response.results);
                }
            }

            @Override
            public void onUnauthorized() {
                mView.hideProgress();

            }

            @Override
            public void onFailed(Throwable throwable) {
                mView.hideProgress();

            }
        });
    }

}
