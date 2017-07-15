package id.co.blogspot.tutor93.popularmovie.seedb;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract.MovieEntry;
import id.co.blogspot.tutor93.popularmovie.utility.Constant;

public class SeeDBActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private ContentValues contentValues;
    private ListView main_listview;
    private MovieCursorAdapter mCursorAdapter;

    /** cheking is table empety*/
    private boolean isTableEmpety;

    public static Intent newStartIntent(Context context) {
        return new Intent(context, SeeDBActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_db);

        contentValues = new ContentValues();
        RelativeLayout empetyView = (RelativeLayout) findViewById(R.id.empety_list);

        //view initialized
        main_listview = (ListView) findViewById(R.id.main_listview);
        main_listview.setEmptyView(empetyView);

        mCursorAdapter = new MovieCursorAdapter(this, null);
        main_listview.setAdapter(mCursorAdapter);

        getLoaderManager().initLoader(1, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MovieEntry.CONTENT_URI,
                Constant.PROJECTION_ALL_COLUMN,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
        /** checking is table empety or not*/
        if(data.moveToFirst()){  // if on table can move to first data, table not empety
            isTableEmpety = false;
        }else {
            isTableEmpety = true;
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
