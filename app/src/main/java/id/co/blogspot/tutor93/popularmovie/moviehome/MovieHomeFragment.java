package id.co.blogspot.tutor93.popularmovie.moviehome;

import android.database.Cursor;
import android.os.Bundle;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import id.co.blogspot.tutor93.popularmovie.PopularMovie;
import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract.MovieEntry;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.moviedetail.MovieDetailActivity;
import id.co.blogspot.tutor93.popularmovie.seedb.SeeDBActivity;
import id.co.blogspot.tutor93.popularmovie.utility.PrefenceUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieHomeFragment extends Fragment implements MovieHomeContract.MovielistView,
        MovieHomeListAdapter.MovieListListener, View.OnClickListener {

    private static final int LANDSCAPE_SPANCOUNT = 4;
    private static final int POTRAIT_SPANCOUNT = 2;

    private AppCompatActivity mActivity;
    private RelativeLayout mContentFrame;

    private MovieHomePresenter mMovieHomePresenter;
    private MovieHomeListAdapter mMovieHomeListAdapter;

    private RecyclerView mMoviesRecycler;
    private ProgressBar mContentLoadingProgress;

    private View mMessageLayout;
    private ImageView mMessageImage;
    private TextView mMessageText;
    private Button mMessageButton;

    private boolean isFavoriteTableEmpety;

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
        setupToolbar(view);
        initMovieLists(view);
        checkPhoneOrientation();

        mContentFrame = (RelativeLayout) view.findViewById(R.id.details_content_frame);
        mContentLoadingProgress = (ProgressBar) view.findViewById(R.id.progress);
        mMessageLayout = view.findViewById(R.id.message_layout);
        mMessageImage = (ImageView) view.findViewById(R.id.iv_message);
        mMessageText = (AppCompatTextView) view.findViewById(R.id.tv_message);
        mMessageButton = (Button) view.findViewById(R.id.btn_try_again);

        setListener();
    }

    private void setListener() {
        mMessageButton.setOnClickListener(this);
        mMovieHomeListAdapter.setMovieListListener(this);
    }

    private void checkPhoneOrientation() {
        if (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mMoviesRecycler.setLayoutManager(new GridLayoutManager(mActivity, POTRAIT_SPANCOUNT));
        } else {
            mMoviesRecycler.setLayoutManager(new GridLayoutManager(mActivity, LANDSCAPE_SPANCOUNT));
        }
    }

    private void initMovieLists(View view) {
        mMoviesRecycler = (RecyclerView) view.findViewById(R.id.recycler_movielist);
        mMoviesRecycler.setHasFixedSize(true);
        mMoviesRecycler.setMotionEventSplittingEnabled(false);
        mMoviesRecycler.setItemAnimator(new DefaultItemAnimator());
        mMoviesRecycler.setAdapter(mMovieHomeListAdapter);
    }

    private void setupToolbar(View view) {
        mActivity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));
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
            case R.id.action_sortby_favorite:
                PrefenceUtils.setSaveUserConfig(mActivity, getString(R.string.sort_favorite));
                onFavoriteClick();
                break;
            case R.id.action_seedb:
                startActivity(SeeDBActivity.newStartIntent(mActivity));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onRefresh() {
        mMovieHomeListAdapter.removeAll();
        mMovieHomePresenter.onInitialListRequested(
                PrefenceUtils.getSinglePrefrenceString(mActivity, R.string.def_sortby_key));
    }

    private void onFavoriteClick(){
        Cursor mData;
        mData = PopularMovie.getmDb().query(
                MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
        mMovieHomeListAdapter.removeAll();
        mMovieHomePresenter.onFavoriteRequested(mData);

        setToolbarFavorite();
    }

    private void setToolbarFavorite() {
        if (mActivity.getSupportActionBar() != null) {
            mActivity.getSupportActionBar().setTitle(R.string.title_favorite_list);
            mActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            // TODO : its must check if title is "Favorite List" do onRefresh (Like back to Home activity).
        }
    }

    @Override
    public void showMovielist(List<MovieResult> movieResultses) {
        if (mMovieHomeListAdapter.getViewType() != MovieHomeListAdapter.VIEW_TYPE_GALLERY) {
            mMovieHomeListAdapter.removeAll();
            mMovieHomeListAdapter.setViewType(MovieHomeListAdapter.VIEW_TYPE_GALLERY);
        }

        mMovieHomeListAdapter.addItems(movieResultses);
    }

    @Override
    public void showProgress() {
        if (mMovieHomeListAdapter.isEmpty()) {
            mContentLoadingProgress.setVisibility(View.VISIBLE);
        }

        mContentFrame.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mContentLoadingProgress.setVisibility(View.GONE);
        mContentFrame.setVisibility(View.VISIBLE);
    }

    @Override
    public void showUnauthorizedError() {
        mMessageImage.setImageResource(R.drawable.ic_error_list);
        mMessageText.setText(getString(R.string.error_generic_server_error, "Unauthorized"));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showEmpty() {

    }

    @Override
    public void showError(String errorMessage) {
        mMessageImage.setImageResource(R.drawable.ic_error_list);
        mMessageText.setText(getString(R.string.error_generic_server_error, errorMessage));
        mMessageButton.setText(getString(R.string.action_try_again));
        showMessageLayout(true);
    }

    @Override
    public void showMessageLayout(boolean show) {
        mMessageLayout.setVisibility(show ? View.VISIBLE : View.GONE);
        mContentFrame.setVisibility(show ? View.GONE : View.VISIBLE);
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
    public void onMovieListClick(MovieResult movieDetail, View sharedElementView, int adapterPosition) {
        startActivity(MovieDetailActivity.newStartIntent(mActivity, movieDetail),
                makeTransitionBundle(sharedElementView));
    }

    private Bundle makeTransitionBundle(View sharedElementView) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(mActivity,
                sharedElementView, ViewCompat.getTransitionName(sharedElementView)).toBundle();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_try_again) {
            onRefresh();
        }
    }
}
