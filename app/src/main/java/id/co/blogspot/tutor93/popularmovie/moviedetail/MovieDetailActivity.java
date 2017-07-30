package id.co.blogspot.tutor93.popularmovie.moviedetail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.PopularMovie;
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
        MovieDetailVideoAdapter.VideoListListener, View.OnClickListener, MovieDetailReviewAdapter.MovieReviewListListener {

    private static final String TAG = "MovieDetailActivity";
    private static final String EXTRA_DETAIL_MOVIE = "movieDetail";

    private MovieDetailPresenter mMovieDetailPresenter;
    private MovieResult mMovieresult;
    private MovieDetailReviewAdapter mMovieDetailReviewAdapter;
    private MovieDetailVideoAdapter mMovieDetailVideoAdapter;
    private ImageView appBarImage;
    private NestedScrollView mScrollView;
    private FloatingActionButton makeFavoriteBtn;
    private Button shareBtn;
    private RecyclerView mMoviesDetailReviewsRecycler;
    private RecyclerView mMoviesDetailVideosRecycler;
    private LinearLayout mContentFrame;

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

        if (getIntent().getParcelableExtra(EXTRA_DETAIL_MOVIE) != null) {
            mMovieresult = getIntent().getParcelableExtra(EXTRA_DETAIL_MOVIE);
        }

        initReviewAndVideos();
        initView();
        mMovieDetailPresenter = new MovieDetailPresenter(DataManager.getInstance());
        mMovieDetailPresenter.attachView(this);

        loadReviewAndVideos();
        loadAppbarImage();
        setupToolbar();
        initMovieDetail();
        initReviewList();
        initVideosList();
        setListener();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState: IM CALL");
        outState.putParcelable(EXTRA_DETAIL_MOVIE,
                getIntent().getParcelableExtra(EXTRA_DETAIL_MOVIE));
        outState.putIntArray("scrollPosition",
                new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});
        outState.putInt("scrollReview",
                ((LinearLayoutManager)mMoviesDetailReviewsRecycler.getLayoutManager())
                        .findFirstVisibleItemPosition());
        outState.putInt("scrollVideos",
                ((LinearLayoutManager)mMoviesDetailVideosRecycler.getLayoutManager())
                        .findFirstVisibleItemPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.i(TAG, "onRestoreInstanceState: IM CALL");
        mMovieresult = savedInstanceState.getParcelable(EXTRA_DETAIL_MOVIE);
        final int[] position = savedInstanceState.getIntArray("scrollPosition");
        if (position != null) {
            mScrollView.scrollTo(position[0], position[1]);
        }
        mMoviesDetailReviewsRecycler.getLayoutManager()
                .scrollToPosition(savedInstanceState.getInt("scrollReview"));
        mMoviesDetailVideosRecycler.getLayoutManager()
                .scrollToPosition(savedInstanceState.getInt("scrollVideos"));

    }

    private void loadAppbarImage() {
        if (mMovieresult.posterPath != null) {
            Glide.with(this)
                    .load(Helper.getComplateImageUrl(
                            mMovieresult.backdropPath,
                            Constant.POSTERSIZE_w500))
                    .error(R.drawable.ic_error_list)
                    .centerCrop()
                    .crossFade()
                    .into(appBarImage);
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
        appBarImage = (ImageView) findViewById(R.id.app_bar_image);
        makeFavoriteBtn = (FloatingActionButton) findViewById(R.id.moviedetail_makefavorite_fab);
        shareBtn = (Button) findViewById(R.id.moviedetail_sharebtn);
        mScrollView = (NestedScrollView) findViewById(R.id.moviedetail_scroll);

        mMoviesDetailReviewsRecycler = (RecyclerView) findViewById(R.id.moviedetail_list_review);
        mMoviesDetailVideosRecycler = (RecyclerView) findViewById(R.id.moviedetail_list_videos);
        mMoviesDetailReviewsRecycler.setLayoutManager(
                new LinearLayoutManager(this));
        mMoviesDetailVideosRecycler.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mContentFrame = (LinearLayout) findViewById(R.id.details_content_frame);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void initMovieDetail() {
        if (mMovieresult != null) {
            MovieDetailFrameWrapper mMovieDetailWrapper = new MovieDetailFrameWrapper(this, mMovieresult);
            mContentFrame.addView(mMovieDetailWrapper);
        }
    }

    private void setListener() {
        mMovieDetailVideoAdapter.setVideoListListener(this);
        mMovieDetailReviewAdapter.setMovieReviewListListener(this);
        shareBtn.setOnClickListener(this);
        makeFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFavorite();
            }
        });
    }

    private void initVideosList() {
        mMoviesDetailVideosRecycler.setHasFixedSize(true);
        mMoviesDetailVideosRecycler.setMotionEventSplittingEnabled(false);
        mMoviesDetailVideosRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesDetailVideosRecycler.setAdapter(mMovieDetailVideoAdapter);
    }

    private void initReviewList() {
        mMoviesDetailReviewsRecycler.setHasFixedSize(true);
        mMoviesDetailReviewsRecycler.setMotionEventSplittingEnabled(false);
        mMoviesDetailReviewsRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesDetailReviewsRecycler.setAdapter(mMovieDetailReviewAdapter);
    }

    private void setupToolbar() {
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
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
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
        if (v.getId() == R.id.moviedetail_sharebtn) {
            if (mMovieDetailVideoAdapter.shareClick() != null) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.youtube_url, mMovieDetailVideoAdapter.shareClick()));
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            } else {
                showError(getString(R.string.error_share));
            }
        }
    }

    private void saveFavorite() {
        if (isMovieFavorite(String.valueOf(mMovieresult.id))) {
            if (unFavoriteMovie(mMovieresult.id)) {
                Toast.makeText(this, getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();
            }
            return;
        }

        ContentValues movieDetail = new ContentValues();

        movieDetail.put(MovieEntry.COLUMN_MOVIE_ID,
                mMovieresult.id);
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

        if (uriResult != null) {
            Log.i(TAG, uriResult.toString());
        }
    }


    private boolean unFavoriteMovie(int id) {
        int isSuccess = getContentResolver().delete(
                MovieEntry.CONTENT_URI,
                null,
                new String[]{String.valueOf(id)});
        return isSuccess > 0;
    }

    public boolean isMovieFavorite(String movieId) {
        Cursor cursor = getContentResolver().query(
                MovieEntry.CONTENT_URI,
                Constant.PROJECTION_ALL_COLUMN,
                MovieEntry.COLUMN_MOVIE_ID,
                new String[]{movieId}, null);
        boolean isFavorite = cursor.getCount() > 0;
        cursor.close();

        return isFavorite;
    }

    @Override
    public void onMovieReviewListClick(TextView mMovieContent, int adapterPosition) {
        if (mMovieContent.getMaxLines() == 4) {
            mMovieContent.setMaxLines(500);
        } else {
            mMovieContent.setMaxLines(4);
        }
    }
}
