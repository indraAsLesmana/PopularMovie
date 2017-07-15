package id.co.blogspot.tutor93.popularmovie.seedb;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import id.co.blogspot.tutor93.popularmovie.R;
import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract.MovieEntry;

/**
 * Created by indraaguslesmana on 1/5/17.
 */

public class MovieCursorAdapter extends CursorAdapter {


    public MovieCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTitle = (TextView) view.findViewById(R.id.name_tx);
        TextView breedTitle = (TextView) view.findViewById(R.id.breed_tx);

        String nameResult = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_TITLE));
        String breedResult = cursor.getString(cursor.getColumnIndexOrThrow(MovieEntry.COLUMN_MOVIE_ORIGINALTITLE));

        nameTitle.setText(nameResult);
        breedTitle.setText(breedResult);
    }
}
