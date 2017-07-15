package id.co.blogspot.tutor93.popularmovie.moviedetail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.base.BaseActivity;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.data.model.ReviewResult;
import id.co.blogspot.tutor93.popularmovie.data.model.VideoResult;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;
import id.co.blogspot.tutor93.popularmovie.utility.Helper;
import id.co.blogspot.tutor93.popularmovie.utility.widgets.MovieDetailFrameWrapper;
import id.co.blogspot.tutor93.popularmovie.videoplayer.VideoPlayerYoutube;
import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract.MovieEntry;

public class MovieDetailActivity extends BaseActivity implements MovieDetailContract.HomeClickView,
        MovieDetailVideoAdapter.VideoListListener, View.OnClickListener{

    private static final String TAG = "MovieDetailActivity";
    private static final String EXTRA_DETAIL_MOVIE = "movieDetail";

    private MovieDetailPresenter mMovieDetailPresenter;
    private MovieResult mMovieresult;
    private MovieDetailReviewAdapter mMovieDetailReviewAdapter;
    private MovieDetailVideoAdapter mMovieDetailVideoAdapter;
    private ImageView appBarImage;
    private FloatingActionButton makeFavoriteBtn;

    private Uri uriResult;

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
        loadAppbarImage();
    }

    private void loadAppbarImage() {
        if (mMovieresult.posterPath != null) {
            Glide.with(this)
                    .load(Helper.getComplateImageUrl(mMovieresult.backdropPath, Constant.POSTERSIZE_w500))
                    .error(R.drawable.ic_error_list)
                    .centerCrop()
                    .crossFade()
                    .into(appBarImage);
        }
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

        appBarImage = (ImageView) findViewById(R.id.app_bar_image);
        makeFavoriteBtn = (FloatingActionButton) findViewById(R.id.moviedetail_makefavorite_fab);

        initMovieDetail();
        initReviewList();
        initVideosList();
        setListener();
    }

    private void initMovieDetail() {
        LinearLayout mContentFrame = (LinearLayout) findViewById(R.id.details_content_frame);
        if (mMovieresult != null) {
            MovieDetailFrameWrapper mMovieDetailWrapper = new MovieDetailFrameWrapper(this, mMovieresult);
            mContentFrame.addView(mMovieDetailWrapper);
        }
    }

    private void setListener() {
        mMovieDetailVideoAdapter.setVideoListListener(this);
        makeFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavorite();
            }
        });
    }

    private void initVideosList() {
        RecyclerView mMoviesDetailVideosRecycler = (RecyclerView) findViewById(R.id.moviedetail_list_videos);
        mMoviesDetailVideosRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mMoviesDetailVideosRecycler.setHasFixedSize(true);
        mMoviesDetailVideosRecycler.setMotionEventSplittingEnabled(false);
        mMoviesDetailVideosRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesDetailVideosRecycler.setAdapter(mMovieDetailVideoAdapter);
    }

    private void initReviewList() {
        RecyclerView mMoviesDetailReviewsRecycler = (RecyclerView) findViewById(R.id.moviedetail_list_review);
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
    public void showReviews(List<ReviewResult> reviewResultList) {
        mMovieDetailReviewAdapter.removeAll();
        mMovieDetailReviewAdapter.addItems(reviewResultList);
    }

    @Override
    public void showVideos(List<VideoResult> videoResults) {
        mMovieDetailVideoAdapter.removeAll();
        mMovieDetailVideoAdapter.addItems(videoResults);
    }

    @Override
    public void showVideoTrailer(String movieUrl) {
        startActivity(VideoPlayerYoutube.newStartIntent(this, movieUrl));
    }

    @Override
    public void showReviewItemFull() {

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
    public void onVideoListClick(String movieUrl, int adapterPosition) {
        showVideoTrailer(movieUrl);
    }

    @Override
    public void onClick(View v) {

    }

    private void saveFavorite() {
        ContentValues movieDetail = new ContentValues();
        movieDetail.put(MovieEntry.COLUMN_MOVIE_VOTECOUNT,
                mMovieresult.voteCount);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_VIDEO,
                mMovieresult.video);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_VOTEAVERAGE,
                mMovieresult.voteAverage);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_TITLE,
                mMovieresult.title);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_POPULARITY,
                mMovieresult.popularity);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_POSTERPATH,
                mMovieresult.posterPath);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE,
                mMovieresult.originalLanguage);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_ORIGINALTITLE,
                mMovieresult.originalTitle);
        /*movieDetail.put(MovieEntry.COLUMN_MOVIE_GENREIDS,
                mMovieresult.genreIds);*/
        movieDetail.put(MovieEntry.COLUMN_MOVIE_BACKDROPPATH,
                mMovieresult.backdropPath);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_ADULT,
                mMovieresult.adult);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_OVERVIEW,
                mMovieresult.overview);
        movieDetail.put(MovieEntry.COLUMN_MOVIE_RELEASEDATE,
                mMovieresult.releaseDate);

        Uri uriResult = getContentResolver().insert(MovieEntry.CONTENT_URI,
                movieDetail);

        if (uriResult != null){
            Log.i(TAG, uriResult.toString());
        }
    }
}
