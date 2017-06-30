package id.co.blogspot.tutor93.popularmovie.moviedetail;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.utility.widgets.MovieDetailFrameWrapper;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends Fragment implements MovieDetailContract.HomeClickView {

    private static final String ARG_MOVIEDETAIL = "argMovieDetail";

    private MovieDetailPresenter mMovieDetailPresenter;
    private MovieResult mMovieresult;
    private LinearLayout mContentFrame;

    private AppCompatActivity mActivity;
    private MovieDetailFrameWrapper mMovieDetailWrapper;

    public static MovieDetailFragment newInstance(MovieResult movieDetail) {
        Bundle args = new Bundle();
        args.putParcelable(ARG_MOVIEDETAIL, movieDetail);
        MovieDetailFragment fragment = new MovieDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MovieDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        mMovieDetailPresenter = new MovieDetailPresenter(DataManager.getInstance());
        if (savedInstanceState != null) {
            mMovieresult = savedInstanceState.getParcelable(ARG_MOVIEDETAIL);
        } else if (getArguments() != null) {
            mMovieresult = getArguments().getParcelable(ARG_MOVIEDETAIL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_movie_detail, container, false);

        mMovieDetailPresenter.attachView(this);
        initView(view);
        mMovieDetailPresenter.onShowReviewRequest(mMovieresult.id);
        mMovieDetailPresenter.onPlayTrailerRequest(mMovieresult.id);
        return view;
    }

    private void initView(View view) {
        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setTitle(mMovieresult.title);
        }

        mContentFrame = (LinearLayout) view.findViewById(R.id.details_content_frame);
        if (mMovieresult != null){
            mMovieDetailWrapper = new MovieDetailFrameWrapper(mActivity, mMovieresult);
            mContentFrame.addView(mMovieDetailWrapper);
        }

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
    public void showReview() {

    }

    @Override
    public void showTrailer() {

    }
}
