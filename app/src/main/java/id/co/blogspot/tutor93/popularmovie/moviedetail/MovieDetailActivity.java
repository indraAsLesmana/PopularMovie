package id.co.blogspot.tutor93.popularmovie.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.base.BaseActivity;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.data.model.ReviewResult;
import id.co.blogspot.tutor93.popularmovie.data.model.Reviews;
import id.co.blogspot.tutor93.popularmovie.utility.widgets.MovieDetailFrameWrapper;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.HomeClickView {

    private static final String EXTRA_DETAIL_MOVIE = "movieDetail";

    private MovieDetailPresenter mMovieDetailPresenter;
    private MovieResult mMovieresult;
    private LinearLayout mContentFrame;
    private RecyclerView mMoviesDetailRecycler;
    private MovieDetailFrameWrapper mMovieDetailWrapper;
    private MovieDetailReviewAdapter mMovieDetailReviewAdapter;
    private List<ReviewResult> mReviewResult;

    public static Intent newStartIntent(Context context, MovieResult movieDetail) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_DETAIL_MOVIE, movieDetail);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        if (savedInstanceState != null) {
            mMovieresult = savedInstanceState.getParcelable(EXTRA_DETAIL_MOVIE);
        }else {
            mMovieresult = getIntent().getParcelableExtra(EXTRA_DETAIL_MOVIE);
        }

        mMovieDetailReviewAdapter = new MovieDetailReviewAdapter();

        initView();
        mMovieDetailPresenter = new MovieDetailPresenter(DataManager.getInstance());
        mMovieDetailPresenter.attachView(this);

        if (mMovieresult != null) {
            //load Movie review
            mMovieDetailPresenter.onShowReviewRequest(mMovieresult.id);
        }
    }

    private void initView() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mMovieresult.title);
        }

        mContentFrame = (LinearLayout) findViewById(R.id.details_content_frame);
        if (mMovieresult != null){
            mMovieDetailWrapper = new MovieDetailFrameWrapper(this, mMovieresult);
            mContentFrame.addView(mMovieDetailWrapper);
        }

        mMoviesDetailRecycler = (RecyclerView) findViewById(R.id.moviedetail_list_review);
        mMoviesDetailRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMoviesDetailRecycler.setHasFixedSize(true);
        mMoviesDetailRecycler.setMotionEventSplittingEnabled(false);
        mMoviesDetailRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesDetailRecycler.setAdapter(mMovieDetailReviewAdapter);

    }

    @Override
    public void showReview(List<ReviewResult> reviewResultList) {
        mMovieDetailReviewAdapter.removeAll();
        mMovieDetailReviewAdapter.addItems(reviewResultList);
    }

    @Override
    public void showTrailer() {

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
