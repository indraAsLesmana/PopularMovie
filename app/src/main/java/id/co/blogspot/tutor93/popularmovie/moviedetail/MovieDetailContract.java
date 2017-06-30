package id.co.blogspot.tutor93.popularmovie.moviedetail;


import id.co.blogspot.tutor93.popularmovie.base.RemoteView;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public interface MovieDetailContract {

    interface ViewActions {

        void onShowReviewRequest(int id);

        void onPlayTrailerRequest(int id);
    }

    interface HomeClickView extends RemoteView {

        void showReview();

        void showTrailer();
    }
}
