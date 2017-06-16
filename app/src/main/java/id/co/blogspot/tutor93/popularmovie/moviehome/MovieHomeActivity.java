package id.co.blogspot.tutor93.popularmovie.moviehome;

import android.os.Bundle;
import android.view.Menu;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.base.BaseActivity;


public class MovieHomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_home);

        if (savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movielist_container, MovieHomeFragment.newInstance())
                    .commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.homemovie_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
