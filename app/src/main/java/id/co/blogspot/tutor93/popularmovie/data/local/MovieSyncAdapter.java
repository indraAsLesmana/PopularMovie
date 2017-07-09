package id.co.blogspot.tutor93.popularmovie.data.local;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.SyncResult;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import id.co.blogspot.tutor93.popularmovie.data.DataManager;
import id.co.blogspot.tutor93.popularmovie.data.local.MovieContract.MovieEntry;
import id.co.blogspot.tutor93.popularmovie.data.model.MovieResult;
import id.co.blogspot.tutor93.popularmovie.data.model.Movies;
import id.co.blogspot.tutor93.popularmovie.data.network.RemoteCallback;

/**
 * Created by indraaguslesmana on 7/9/17.
 */

public class MovieSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = "MovieSyncAdapter";
    /**
     * Content resolver, for performing database operations.
     */
    private final ContentResolver mContentResolver;

    /** Projection */
    public static final String [] PROJECTION = {
            MovieEntry._ID,
            MovieEntry.COLUMN_MOVIE_VOTECOUNT,
            MovieEntry.COLUMN_MOVIE_VIDEO,
            MovieEntry.COLUMN_MOVIE_VOTEAVERAGE,
            MovieEntry.COLUMN_MOVIE_TITLE,
            MovieEntry.COLUMN_MOVIE_POPULARITY,
            MovieEntry.COLUMN_MOVIE_POSTERPATH,
            MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE,
            MovieEntry.COLUMN_MOVIE_ORIGINALTITLE,
            MovieEntry.COLUMN_MOVIE_BACKDROPPATH,
            MovieEntry.COLUMN_MOVIE_ADULT,
            MovieEntry.COLUMN_MOVIE_OVERVIEW,
            MovieEntry.COLUMN_MOVIE_RELEASEDATE
    };

    // Constants representing column positions from PROJECTION.
    public static final int COLUMN_ID = 1;
    public static final int COLUMN_MOVIE_VOTECOUNT = 2;
    public static final int COLUMN_MOVIE_VIDEO = 3;
    public static final int COLUMN_MOVIE_VOTEAVERAGE = 4;
    public static final int COLUMN_MOVIE_TITLE = 5;
    public static final int COLUMN_MOVIE_POPULARITY = 6;
    public static final int COLUMN_MOVIE_POSTERPATH = 7;
    public static final int COLUMN_MOVIE_ORIGINALLANGUAGE = 8;
    public static final int COLUMN_MOVIE_ORIGINALTITLE = 9;
    public static final int COLUMN_MOVIE_BACKDROPPATH = 10;
    public static final int COLUMN_MOVIE_ADULT = 11;
    public static final int COLUMN_MOVIE_OVERVIEW = 12;
    public static final int COLUMN_MOVIE_RELEASEDATE = 13;

    public MovieSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
        mContentResolver = context.getContentResolver();
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, final SyncResult syncResult) {
        Log.i(TAG, "Beginning network synchronization");
        DataManager mDatamanager = DataManager.getInstance();
        mDatamanager.getPopularMovies(new RemoteCallback<Movies>() {
            @Override
            public void onSuccess(Movies response) {
                updateLocalData(response.results, syncResult);
            }

            @Override
            public void onUnauthorized() {

            }

            @Override
            public void onFailed(Throwable throwable) {

            }
        });

        Log.i(TAG, "Network synchronization complete");
    }

    private void updateLocalData(List<MovieResult> movies, SyncResult syncResult) {
        final ContentResolver contentResolver = getContext().getContentResolver();
        ArrayList<ContentProviderOperation> batch = new ArrayList<>();

        // Build hash table of incoming entries
        HashMap<Integer, MovieResult> entryMap = new HashMap<>();
        for (MovieResult movie : movies) {
            entryMap.put(movie.id, movie);
        }

        // Get list of all items
        Log.i(TAG, "Fetching local entries for merge");
        Uri uri = MovieContract.BASE_CONTENT_URI; // Get all entries
        Cursor c = contentResolver.query(uri, PROJECTION, null, null, null);
        assert c != null;
        Log.i(TAG, "Found " + c.getCount() + " local entries. Computing merge solution...");

        // Find stale data
        int id;
        int voteCount;
        String video;
        double voteAverage;
        String title;
        double popularity;
        String posterPath;
        String originalLanguage;
        String originalTitle;
//      List<Integer> genreIds = null;
        String backdropPath;
        String adult;
        String overview;
        String releaseDate;

        while (c.moveToNext()) {
            syncResult.stats.numEntries++;
            id = c.getInt(COLUMN_ID);
            voteCount = c.getInt(COLUMN_MOVIE_VOTECOUNT);
            video = c.getString(COLUMN_MOVIE_VIDEO);
            voteAverage = c.getDouble(COLUMN_MOVIE_VOTEAVERAGE);
            title = c.getString(COLUMN_MOVIE_TITLE);
            popularity = c.getDouble(COLUMN_MOVIE_POPULARITY);
            posterPath = c.getString(COLUMN_MOVIE_POSTERPATH);
            originalLanguage = c.getString(COLUMN_MOVIE_ORIGINALLANGUAGE);
            originalTitle = c.getString(COLUMN_MOVIE_ORIGINALTITLE);
            backdropPath = c.getString(COLUMN_MOVIE_BACKDROPPATH);
            adult = c.getString(COLUMN_MOVIE_ADULT);
            overview = c.getString(COLUMN_MOVIE_OVERVIEW);
            releaseDate = c.getString(COLUMN_MOVIE_RELEASEDATE);

            MovieResult match = entryMap.get(id);

            if (match != null) {
                // Entry exists. Remove from entry map to prevent insert later.
                entryMap.remove(id);
                // Check to see if the entry needs to be updated
                Uri existingUri = MovieEntry.CONTENT_URI.buildUpon()
                        .appendPath(Integer.toString(id)).build();

                if ((match.posterPath != null && match.posterPath.equals(posterPath)) ||
                        (match.title != null && !match.title.equals(title))) {
                    // Update existing record
                    Log.i(TAG, "Scheduling update: " + existingUri);
                    batch.add(ContentProviderOperation.newUpdate(existingUri)
                            .withValue(MovieEntry.COLUMN_MOVIE_VOTECOUNT, match.voteCount)
                            .withValue(MovieEntry.COLUMN_MOVIE_VIDEO, match.video)
                            .withValue(MovieEntry.COLUMN_MOVIE_VOTEAVERAGE, match.voteAverage)
                            .withValue(MovieEntry.COLUMN_MOVIE_TITLE, match.title)
                            .withValue(MovieEntry.COLUMN_MOVIE_POPULARITY, match.popularity)
                            .withValue(MovieEntry.COLUMN_MOVIE_POSTERPATH, match.posterPath)
                            .withValue(MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE, match.originalLanguage)
                            .withValue(MovieEntry.COLUMN_MOVIE_ORIGINALTITLE, match.originalTitle)
                            .withValue(MovieEntry.COLUMN_MOVIE_BACKDROPPATH, match.backdropPath)
                            .withValue(MovieEntry.COLUMN_MOVIE_ADULT, match.adult)
                            .withValue(MovieEntry.COLUMN_MOVIE_OVERVIEW, match.overview)
                            .withValue(MovieEntry.COLUMN_MOVIE_RELEASEDATE, match.releaseDate)
                            .build());
                    syncResult.stats.numUpdates++;
                } else {
                    Log.i(TAG, "No action: " + existingUri);
                }
            } else {
                // Entry doesn't exist. Remove it from the database.
                Uri deleteUri = MovieEntry.CONTENT_URI.buildUpon()
                        .appendPath(Integer.toString(id)).build();
                Log.i(TAG, "Scheduling delete: " + deleteUri);
                batch.add(ContentProviderOperation.newDelete(deleteUri).build());
                syncResult.stats.numDeletes++;
            }
        }
        c.close();

        // Add new items
        for (MovieResult e : entryMap.values()) {
            Log.i(TAG, "Scheduling insert: entry_id=" + e.id);
            batch.add(ContentProviderOperation.newInsert(MovieEntry.CONTENT_URI)
                    .withValue(MovieEntry.COLUMN_MOVIE_VOTECOUNT, e.voteCount)
                    .withValue(MovieEntry.COLUMN_MOVIE_VIDEO, e.video)
                    .withValue(MovieEntry.COLUMN_MOVIE_VOTEAVERAGE, e.voteAverage)
                    .withValue(MovieEntry.COLUMN_MOVIE_TITLE, e.title)
                    .withValue(MovieEntry.COLUMN_MOVIE_POPULARITY, e.popularity)
                    .withValue(MovieEntry.COLUMN_MOVIE_POSTERPATH, e.posterPath)
                    .withValue(MovieEntry.COLUMN_MOVIE_ORIGINALLANGUAGE, e.originalLanguage)
                    .withValue(MovieEntry.COLUMN_MOVIE_ORIGINALTITLE, e.originalTitle)
                    .withValue(MovieEntry.COLUMN_MOVIE_BACKDROPPATH, e.backdropPath)
                    .withValue(MovieEntry.COLUMN_MOVIE_ADULT, e.adult)
                    .withValue(MovieEntry.COLUMN_MOVIE_OVERVIEW, e.overview)
                    .withValue(MovieEntry.COLUMN_MOVIE_RELEASEDATE, e.releaseDate)
                    .build());
            syncResult.stats.numInserts++;
        }
        Log.i(TAG, "Merge solution ready. Applying batch update");
        try {
            mContentResolver.applyBatch(MovieContract.CONTENT_AUTHORITY, batch);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
        mContentResolver.notifyChange(
                MovieEntry.CONTENT_URI, // URI where data was modified
                null,                           // No local observer
                false);                         // IMPORTANT: Do not sync to network
        // This sample doesn't support uploads, but if *your* code does, make sure you set
        // syncToNetwork=false in the line above to prevent duplicate syncs.

    }

}
