package id.co.blogspot.tutor93.popularmovie.moviedetail;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.data.model.ReviewResult;
import id.co.blogspot.tutor93.popularmovie.data.model.VideoResult;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment implements MovieDetailContract.HomeClickView,
        MovieDetailVideoAdapter.VideoListListener, View.OnClickListener, MovieDetailReviewAdapter.MovieReviewListListener{

    private static final String TAG = "MovieDetailFragment";
    private static final String EXTRA_DETAIL_MOVIE = "movieDetail";

    private MovieDetailPresenter mMovieDetailPresenter;
    private MovieDetailReviewAdapter mMovieDetailReviewAdapter;
    private MovieDetailVideoAdapter mMovieDetailVideoAdapter;

    private MovieResult mMovieresult;
    private ImageView appBarImage;
    private FloatingActionButton makeFavoriteBtn;
    private Button shareBtn;

    private Uri uriResult;


    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mMovieDetailPresenter = new MovieDetailPresenter(DataManager.getInstance());
        mMovieDetailReviewAdapter = new MovieDetailReviewAdapter();
        mMovieDetailVideoAdapter = new MovieDetailVideoAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movie_detail, container, false);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void showReviews(List<ReviewResult> reviewResultList) {

    }

    @Override
    public void showVideos(List<VideoResult> videoResults) {

    }

    @Override
    public void showVideoTrailer(String movieUrl) {

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showUnauthorizedError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errorMessage) {

    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    @Override
    public void onMovieReviewListClick(TextView mMovieContent, int adapterPosition) {

    }

    @Override
    public void onVideoListClick(String movieUrl, int adapterPosition) {

    }
}
