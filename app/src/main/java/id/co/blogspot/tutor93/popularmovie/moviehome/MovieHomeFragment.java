package id.co.blogspot.tutor93.popularmovie.moviehome;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import id.co.blogspot.tutor93.popularmovie.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MovieHomeFragment extends Fragment implements MovieHomeContract.MovielistView{

    private AppCompatActivity mActivity;
    private MovieHomePresenter mMovieHomePresenter;


    public static MovieHomeFragment newInstance() {
        return new MovieHomeFragment();
    }

    public MovieHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMovieHomePresenter = new MovieHomePresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_movie_home, container, false);

        initView(view);
        mMovieHomePresenter.attachView(this);

        return view;
    }

    private void initView(View view) {
        mActivity = (AppCompatActivity) getActivity();
        mActivity.setSupportActionBar((Toolbar) view.findViewById(R.id.toolbar));

        if (mActivity.getSupportActionBar() != null){

        }
    }


    @Override
    public void showMovielist() {

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
