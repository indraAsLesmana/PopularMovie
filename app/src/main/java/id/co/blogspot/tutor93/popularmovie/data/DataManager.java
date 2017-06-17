package id.co.blogspot.tutor93.popularmovie.data;


import id.co.blogspot.tutor93.popularmovie.BuildConfig;
import id.co.blogspot.tutor93.popularmovie.data.model.MoviePopular;
import id.co.blogspot.tutor93.popularmovie.data.network.MovieService;
import id.co.blogspot.tutor93.popularmovie.data.network.MovieServiceFactory;
import id.co.blogspot.tutor93.popularmovie.data.network.RemoteCallback;

/**
 * Created by indraaguslesmana on 6/16/17.
 */

public class DataManager {

    private static DataManager sInstance;

    private final MovieService mMovieService;

    public static DataManager getInstance() {
        if (sInstance == null) {
            sInstance = new DataManager();
        }
        return sInstance;
    }

    private DataManager() {
        mMovieService = MovieServiceFactory.makeMovieService();
    }

    public void getPopularMovies(RemoteCallback<MoviePopular> listener) {
        mMovieService.getMoviePopular(BuildConfig.MOVIEDB_APIKEY).enqueue(listener);
    }

    public void getTopratedMovies(RemoteCallback<MoviePopular> listener) {
        mMovieService.getMovieToprated(BuildConfig.MOVIEDB_APIKEY).enqueue(listener);
    }
}
