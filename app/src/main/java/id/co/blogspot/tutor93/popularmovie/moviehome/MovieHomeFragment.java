package id.co.blogspot.tutor93.popularmovie.moviehome;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResults;
import id.co.blogspot.tutor93.popularmovie.utility.PrefenceUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieHomeFragment extends Fragment implements MovieHomeContract.MovielistView,
        MovieHomeListAdapter.MovieListListener {

    private static final int LANDSCAPE_SPANCOUNT = 4;
    private static final int POTRAIT_SPANCOUNT = 2;

    private AppCompatActivity mActivity;
    private MovieHomePresenter mMovieHomePresenter;
    private MovieHomeListAdapter mMovieHomeListAdapter;
    
    private RecyclerView mMoviesRecycler;
    private ProgressBar mContentLoadingProgress;

    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;

    public static MovieHomeFragment newInstance() {
        return new MovieHomeFragment();
    }

    public MovieHomeFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        mMovieHomePresenter = new MovieHomePresenter(DataManager.getInstance());
        mMovieHomeListAdapter = new MovieHomeListAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_movie_home, container, false);

        initView(view);
        mMovieHomePresenter.attachView(this);
        if (mMovieHomeListAdapter.isEmpty()) {
            mMovieHomePresenter.onInitialListRequested(
                    PrefenceUtils.getSinglePrefrenceString(mActivity, R.string.def_sortby_key));
        }
        return view;
    }

    private void initView(View view) {
        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));

        if (mActivity.getSupportActionBar() != null){

        }

        mMoviesRecycler = (RecyclerView) view.findViewById(R.id.recycler_invitationlist);
        mMoviesRecycler.setHasFixedSize(true);
        mMoviesRecycler.setMotionEventSplittingEnabled(false);
        mMoviesRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesRecycler.setAdapter(mMovieHomeListAdapter);

        if (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mMoviesRecycler.setLayoutManager(new GridLayoutManager(mActivity, POTRAIT_SPANCOUNT));
        } else {
            mMoviesRecycler.setLayoutManager(new GridLayoutManager(mActivity, LANDSCAPE_SPANCOUNT));
        }

        mContentLoadingProgress = (ProgressBar) view.findViewById(R.id.progress);
        mMessageLayout = view.findViewById(R.id.message_layout);
        mMessageImage = (ImageView) view.findViewById(R.id.iv_message);
        mMessageText = (TextView) view.findViewById(R.id.tv_message);
        mMessageButton = (Button) view.findViewById(R.id.btn_try_again);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_sortby_popular:
                PrefenceUtils.setSaveUserConfig(mActivity, getString(R.string.sort_popular));
                onRefresh();
                break;
            case R.id.action_sortby_toprated:
                PrefenceUtils.setSaveUserConfig(mActivity, getString(R.string.sort_toprated));
                onRefresh();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onRefresh() {
        mMovieHomeListAdapter.removeAll();
        mMovieHomePresenter.onInitialListRequested(
                PrefenceUtils.getSinglePrefrenceString(mActivity, R.string.def_sortby_key));
    }

    @Override
    public void showMovielist(List<MovieResults> movieResultses) {
        if (mMovieHomeListAdapter.getViewType() != MovieHomeListAdapter.VIEW_TYPE_GALLERY) {
            mMovieHomeListAdapter.removeAll();
            mMovieHomeListAdapter.setViewType(MovieHomeListAdapter.VIEW_TYPE_GALLERY);
        }

        mMovieHomeListAdapter.addItems(movieResultses);
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
    public void onDestroyView() {
        mMoviesRecycler.setAdapter(null);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mMovieHomePresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onMovieListClick(MovieResults moviePopular, int adapterPosition) {
        
    }
}
