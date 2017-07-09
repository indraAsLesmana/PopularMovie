package id.co.blogspot.tutor93.popularmovie.data.local;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract.MovieEntry;

/**
 * Created by indraaguslesmana on 7/9/17.
 */

public class MovieProvider extends ContentProvider {

    public MovieDBHelper mDBhelper;

    /**
     * URI matcher code for the content URI for the movies table
     */
    private static final int MOVIES = 100;

    /**
     * URI matcher code for the content URI for a single movie in the movies table
     */
    private static final int MOVIE_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES, MOVIES);
        sUriMatcher.addURI(MovieContract.CONTENT_AUTHORITY, MovieContract.PATH_MOVIES + "/#", MOVIE_ID);
    }

    @Override
    public boolean onCreate() {
        mDBhelper = new MovieDBHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = mDBhelper.getReadableDatabase();
        Cursor cursor = null;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                //create Select * from movies;
                cursor = db.query(MovieEntry.TABLE_NAME, projection, null, null, null, null, sortOrder, null);
                break;

            case MOVIE_ID:
                //create select where from movies;
                selection = MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(MovieEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder, null);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        /** this make automaticly reload for every data change...*/
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return MovieEntry.CONTENT_LIST_TYPE;
            case MOVIE_ID:
                return MovieEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return insertMovies(uri, values);
            default:
                throw new IllegalArgumentException("Error inserting queri" + uri);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mDBhelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIES:
                return deleteAlltable(uri);
            case MOVIE_ID:
                return deleteById(uri, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("error delete data" + uri);
        }
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int match = sUriMatcher.match(uri);
        switch (match) {
            case MOVIES:
                return updateMovie(uri, values, selection, selectionArgs);
            case MOVIE_ID:
                // For the Movie_ID code, extract out the ID from the URI,
                // so we know which row to update. Selection will be "_id=?" and selection
                // arguments will be a String array containing the actual ID.
                selection = MovieEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return updateMovie(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    /**
     * insert data method
     */
    private Uri insertMovies(Uri uri, ContentValues values) {
        if (!inputCheck(values)) {
            Toast.makeText(getContext(), "reject by CoProvider", Toast.LENGTH_SHORT).show();
            return null;
        }

        SQLiteDatabase db = mDBhelper.getWritableDatabase();
        long id = db.insert(
                MovieEntry.TABLE_NAME,
                null,
                values);

        /** make toast for every new insert row, if success*/
        Uri uriResult = ContentUris.withAppendedId(uri, id);
        if (id != -1) {
            Toast.makeText(getContext(), "Insert Success at: \n" +
                    uriResult.toString(), Toast.LENGTH_SHORT).show();

            //notify if change has been made
            getContext().getContentResolver().notifyChange(uri, null);

        } else {
            Toast.makeText(getContext(), "insert failure, return value: " +
                    id, Toast.LENGTH_SHORT).show();
        }

        return uriResult;
    }

    private boolean inputCheck(ContentValues values) {
        boolean inputResult = true;
        if (values.getAsInteger(MovieEntry.COLUMN_MOVIE_VOTECOUNT) == null ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_VIDEO).isEmpty() ||
                values.getAsDouble(MovieEntry.COLUMN_MOVIE_VOTEAVERAGE) == null ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_TITLE).isEmpty() ||
                values.getAsDouble(MovieEntry.COLUMN_MOVIE_POPULARITY) == null ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_POSTERPATH).isEmpty() ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE).isEmpty() ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_ORIGINALTITLE).isEmpty() ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_GENREIDS).isEmpty() ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_BACKDROPPATH).isEmpty() ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_ADULT).isEmpty() ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_OVERVIEW).isEmpty() ||
                values.getAsString(MovieEntry.COLUMN_MOVIE_RELEASEDATE).isEmpty()) {

            return inputResult = false;
        }

        return inputResult;
    }

    /**
     * delete all data in table
     */
    private int deleteAlltable(Uri uri) {
        SQLiteDatabase db = mDBhelper.getWritableDatabase();

        int rowDeleted = db.delete(MovieEntry.TABLE_NAME, null, null);

        /** notify if delete success*/
        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowDeleted;
    }

    /**
     * delete by id method
     */
    private int deleteById(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDBhelper.getWritableDatabase();
        selection = MovieEntry._ID + "=?";
        int rowDeleted = db.delete(MovieEntry.TABLE_NAME, selection, selectionArgs);

        /** notify if delete success*/
        if (rowDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return rowDeleted;
    }

    /**
     * Update Movies in the database with the given content values. Apply the changes to the rows
     * specified in the selection and selection arguments (which could be 0 or 1 or more Movies).
     * Return the number of rows that were successfully updated.
     */
    private int updateMovie(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_VOTECOUNT)) {
            Integer voteCount = values.getAsInteger(MovieEntry.COLUMN_MOVIE_VOTECOUNT);
            if (voteCount == null) {
                throw new IllegalArgumentException("Movie requires a votecount");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_VIDEO)) {
            String video = values.getAsString(MovieEntry.COLUMN_MOVIE_VIDEO);
            if (video.isEmpty() || !MovieEntry.isValidMovieValue(video)) {
                throw new IllegalArgumentException("Movie requires valid video");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_VOTEAVERAGE)) {
            Double voteAverage = values.getAsDouble(MovieEntry.COLUMN_MOVIE_VOTEAVERAGE);
            if (voteAverage == null) {
                throw new IllegalArgumentException("Movie requires a vote average");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_TITLE)) {
            String title = values.getAsString(MovieEntry.COLUMN_MOVIE_TITLE);
            if (title.isEmpty()) {
                throw new IllegalArgumentException("Movie requires valid title");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_POPULARITY)) {
            Double popularity = values.getAsDouble(MovieEntry.COLUMN_MOVIE_POPULARITY);
            if (popularity == null) {
                throw new IllegalArgumentException("Movie requires valid popularity");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_POSTERPATH)) {
            String arg = values.getAsString(MovieEntry.COLUMN_MOVIE_POSTERPATH);
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("Movie requires valid posterpath");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE)) {
            String arg = values.getAsString(MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE);
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("Movie requires valid original language");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_ORIGINALTITLE)) {
            String arg = values.getAsString(MovieEntry.COLUMN_MOVIE_ORIGINALTITLE);
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("Movie requires valid original title");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_GENREIDS)) {
            String arg = values.getAsString(MovieEntry.COLUMN_MOVIE_GENREIDS);
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("Movie requires valid gendre ids");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_BACKDROPPATH)) {
            String arg = values.getAsString(MovieEntry.COLUMN_MOVIE_BACKDROPPATH);
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("Movie requires valid backdroppath");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_ADULT)) {
            String arg = values.getAsString(MovieEntry.COLUMN_MOVIE_ADULT);
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("Movie requires valid adult");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_OVERVIEW)) {
            String arg = values.getAsString(MovieEntry.COLUMN_MOVIE_OVERVIEW);
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("Movie requires valid overview");
            }
        }

        if (values.containsKey(MovieEntry.COLUMN_MOVIE_RELEASEDATE)) {
            String arg = values.getAsString(MovieEntry.COLUMN_MOVIE_RELEASEDATE);
            if (arg.isEmpty()) {
                throw new IllegalArgumentException("Movie requires valid release date");
            }
        }

        // If there are no values to update, then don't try to update the database
        if (values.size() == 0) {
            return 0;
        }

        // Otherwise, get writeable database to update the data
        SQLiteDatabase database = mDBhelper.getWritableDatabase();

        int rowUpdate = database.update(MovieEntry.TABLE_NAME, values, selection, selectionArgs);

        /** notfied vied if change has been made*/
        if (rowUpdate != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        // Returns the number of database rows affected by the update statement
        return rowUpdate;
    }

}
