package id.co.blogspot.tutor93.popularmovie.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.base.BaseActivity;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.data.model.ReviewResult;
import id.co.blogspot.tutor93.popularmovie.data.model.VideoResult;
import id.co.blogspot.tutor93.popularmovie.utility.widgets.MovieDetailFrameWrapper;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.HomeClickView {

    private static final String EXTRA_DETAIL_MOVIE = "movieDetail";

    private MovieDetailPresenter mMovieDetailPresenter;
    private MovieResult mMovieresult;
    private LinearLayout mContentFrame;
    private RecyclerView mMoviesDetailReviewsRecycler;
    private RecyclerView mMoviesDetailVideosRecycler;
    private MovieDetailFrameWrapper mMovieDetailWrapper;
    private MovieDetailReviewAdapter mMovieDetailReviewAdapter;
    private MovieDetailVideoAdapter mMovieDetailVideoAdapter;

    public static Intent newStartIntent(Context context, MovieResult movieDetail) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_DETAIL_MOVIE, movieDetail);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        isSavedInstanceEvaluabe(savedInstanceState);

        initReviewAndVideos();

        initView();
        mMovieDetailPresenter = new MovieDetailPresenter(DataManager.getInstance());
        mMovieDetailPresenter.attachView(this);

        loadReviewAndVideos();
    }

    private void isSavedInstanceEvaluabe(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mMovieresult = savedInstanceState.getParcelable(EXTRA_DETAIL_MOVIE);
        }else {
            mMovieresult = getIntent().getParcelableExtra(EXTRA_DETAIL_MOVIE);
        }
    }

    private void initReviewAndVideos() {
        mMovieDetailReviewAdapter = new MovieDetailReviewAdapter();
        mMovieDetailVideoAdapter = new MovieDetailVideoAdapter();
    }

    private void loadReviewAndVideos() {
        if (mMovieresult != null) {
            mMovieDetailPresenter.onShowReviewRequest(mMovieresult.id);
            mMovieDetailPresenter.onVideoRequest(mMovieresult.id);
        }
    }

    private void initView() {
        setupToolbar();

        mContentFrame = (LinearLayout) findViewById(R.id.details_content_frame);
        if (mMovieresult != null){
            mMovieDetailWrapper = new MovieDetailFrameWrapper(this, mMovieresult);
            mContentFrame.addView(mMovieDetailWrapper);
        }

        initReviewList();

        initVideosList();

    }

    private void initVideosList() {
        mMoviesDetailVideosRecycler = (RecyclerView) findViewById(R.id.moviedetail_list_videos);
        mMoviesDetailVideosRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMoviesDetailVideosRecycler.setHasFixedSize(true);
        mMoviesDetailVideosRecycler.setMotionEventSplittingEnabled(false);
        mMoviesDetailVideosRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesDetailVideosRecycler.setAdapter(mMovieDetailVideoAdapter);
    }

    private void initReviewList() {
        mMoviesDetailReviewsRecycler = (RecyclerView) findViewById(R.id.moviedetail_list_review);
        mMoviesDetailReviewsRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMoviesDetailReviewsRecycler.setHasFixedSize(true);
        mMoviesDetailReviewsRecycler.setMotionEventSplittingEnabled(false);
        mMoviesDetailReviewsRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesDetailReviewsRecycler.setAdapter(mMovieDetailReviewAdapter);
    }

    private void setupToolbar() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mMovieresult.title);
        }
    }

    @Override
    public void showReview(List<ReviewResult> reviewResultList) {
        mMovieDetailReviewAdapter.removeAll();
        mMovieDetailReviewAdapter.addItems(reviewResultList);
    }

    @Override
    public void showVideos(List<VideoResult> videoResults) {
        mMovieDetailVideoAdapter.removeAll();
        mMovieDetailVideoAdapter.addItems(videoResults);
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
}
