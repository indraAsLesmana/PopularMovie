package id.co.blogspot.tutor93.popularmovie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import id.co.blogspot.tutor93.popularmovie.R;

/**
 * Created by indraaguslesmana on 6/14/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getSupportActionBar().getTitle().equals(getResources().getString(R.string.title_favorite_list))) {
                    return super.onOptionsItemSelected(item);
                }else {
                    onBackPressed();
                }
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}