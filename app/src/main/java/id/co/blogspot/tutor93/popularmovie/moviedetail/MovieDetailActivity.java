package id.co.blogspot.tutor93.popularmovie.moviedetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.base.BaseActivity;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResults;

public class MovieDetailActivity extends BaseActivity {

    private static final String EXTRA_DETAIL_MOVIE = "movieDetail";

    public static Intent newStartIntent(Context context, MovieResults movieDetail) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_DETAIL_MOVIE, movieDetail);
        return intent;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieResults movieDetail = getIntent().getParcelableExtra(EXTRA_DETAIL_MOVIE);

        setContentView(R.layout.activity_movie_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.moviedetail_container, MovieDetailFragment.newInstance(movieDetail))
                    .commit();
        }

    }
}
