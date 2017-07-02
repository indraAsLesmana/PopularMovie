package id.co.blogspot.tutor93.popularmovie.moviedetail;


import java.util.List;

import id.co.blogspot.tutor93.popularmovie.base.RemoteView;
import id.co.blogspot.tutor93.popularmovie.data.model.ReviewResult;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public interface MovieDetailContract {

    interface ViewActions {

        void onShowReviewRequest(int id);

        void onPlayTrailerRequest(int id);
    }

    interface HomeClickView extends RemoteView {

        void showReview(List<ReviewResult> reviewResultList);

        void showTrailer();
    }
}
