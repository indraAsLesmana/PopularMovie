package id.co.blogspot.tutor93.popularmovie.moviedetail;


import java.util.List;

import id.co.blogspot.tutor93.popularmovie.base.RemoteView;
import id.co.blogspot.tutor93.popularmovie.data.model.ReviewResult;
import id.co.blogspot.tutor93.popularmovie.data.model.VideoResult;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public interface MovieDetailContract {

    interface ViewActions {

        void onShowReviewRequest(int id);

        void onVideoRequest(int id);
    }

    interface HomeClickView extends RemoteView {

        void showReviews(List<ReviewResult> reviewResultList);

        void showVideos(List<VideoResult> videoResults);

        void showVideoTrailer(String movieUrl);

        void showReviewItemFull();
    }
}
