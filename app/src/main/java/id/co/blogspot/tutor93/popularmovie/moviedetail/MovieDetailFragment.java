package id.co.blogspot.tutor93.popularmovie.moviedetail;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.data.model.ReviewResult;
import id.co.blogspot.tutor93.popularmovie.data.model.VideoResult;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;
import id.co.blogspot.tutor93.popularmovie.utility.Helper;
import id.co.blogspot.tutor93.popularmovie.utility.widgets.MovieDetailFrameWrapper;
import id.co.blogspot.tutor93.popularmovie.videoplayer.VideoPlayerYoutube;

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

    private LinearLayout mContentFrame;
    private RecyclerView mMoviesDetailVideosRecycler;
    RecyclerView mMoviesDetailReviewsRecycler;

    private Uri uriResult;
    private AppCompatActivity mActivity;


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
        initReviewAndVideos();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_movie_detail, container, false);
        initView(view);
        mMovieDetailPresenter.attachView(this);

        loadReviewAndVideos();
        loadAppbarImage();
        return view;
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

    private void initView(View view) {
        mActivity = (AppCompatActivity) getActivity();
        setupToolbar();

        appBarImage = (ImageView) view.findViewById(R.id.app_bar_image);
        makeFavoriteBtn = (FloatingActionButton) view.findViewById(R.id.moviedetail_makefavorite_fab);
        shareBtn = (Button) view.findViewById(R.id.moviedetail_sharebtn);

        mContentFrame = (LinearLayout) view.findViewById(R.id.details_content_frame);
        mMoviesDetailVideosRecycler = (RecyclerView) view.findViewById(R.id.moviedetail_list_videos);
        mMoviesDetailReviewsRecycler = (RecyclerView) view.findViewById(R.id.moviedetail_list_review);
        mActivity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));


        initMovieDetail();
        initReviewList();
        initVideosList();
        setListener();
    }


    private void initMovieDetail() {
        if (mMovieresult != null) {
            MovieDetailFrameWrapper mMovieDetailWrapper = new MovieDetailFrameWrapper(mActivity, mMovieresult);
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
        mMoviesDetailVideosRecycler.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false));
        mMoviesDetailVideosRecycler.setHasFixedSize(true);
        mMoviesDetailVideosRecycler.setMotionEventSplittingEnabled(false);
        mMoviesDetailVideosRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesDetailVideosRecycler.setAdapter(mMovieDetailVideoAdapter);
    }

    private void initReviewList() {

        mMoviesDetailReviewsRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        mMoviesDetailReviewsRecycler.setHasFixedSize(true);
        mMoviesDetailReviewsRecycler.setMotionEventSplittingEnabled(false);
        mMoviesDetailReviewsRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesDetailReviewsRecycler.setAdapter(mMovieDetailReviewAdapter);
    }

    private void setupToolbar() {
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mMovieresult.title);
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.moviedetail_sharebtn) {
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
        startActivity(VideoPlayerYoutube.newStartIntent(mActivity, movieUrl));
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
        Toast.makeText(mActivity, errorMessage, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showMessageLayout(boolean show) {

    }

    @Override
    public void onMovieReviewListClick(TextView mMovieContent, int adapterPosition) {
        if (mMovieContent.getMaxLines() == 4) {
            mMovieContent.setMaxLines(500);
        } else {
            mMovieContent.setMaxLines(4);
        }
    }

    @Override
    public void onVideoListClick(String movieUrl, int adapterPosition) {
        showVideoTrailer(movieUrl);
    }

    private void saveFavorite() {
        if (isMovieFavorite(String.valueOf(mMovieresult.id))) {
            if (unFavoriteMovie(mMovieresult.id)) {
                Toast.makeText(mActivity, getString(R.string.remove_favorite), Toast.LENGTH_SHORT).show();
            }
            return;
        }

        ContentValues movieDetail = new ContentValues();

        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                mMovieresult.id);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTECOUNT,
                mMovieresult.voteCount);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_VIDEO,
                mMovieresult.video);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_VOTEAVERAGE,
                mMovieresult.voteAverage);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_TITLE,
                mMovieresult.title);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_POPULARITY,
                mMovieresult.popularity);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_POSTERPATH,
                mMovieresult.posterPath);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE,
                mMovieresult.originalLanguage);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_ORIGINALTITLE,
                mMovieresult.originalTitle);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_BACKDROPPATH,
                mMovieresult.backdropPath);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_ADULT,
                mMovieresult.adult);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_OVERVIEW,
                mMovieresult.overview);
        movieDetail.put(MovieContract.MovieEntry.COLUMN_MOVIE_RELEASEDATE,
                mMovieresult.releaseDate);

        Uri uriResult = mActivity.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI,
                movieDetail);

        if (uriResult != null) {
            Log.i(TAG, uriResult.toString());
        }
    }

    private boolean unFavoriteMovie(int id) {
        int isSuccess = mActivity.getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                new String[]{String.valueOf(id)});
        return isSuccess > 0;
    }

    public boolean isMovieFavorite(String movieId) {
        boolean isFavorite = false;
        Cursor cursor = mActivity.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                Constant.PROJECTION_ALL_COLUMN,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID,
                new String[]{movieId}, null);
        if (cursor != null) {
            isFavorite = cursor.getCount() > 0;
            cursor.close();
        }
        return isFavorite;
    }

}
