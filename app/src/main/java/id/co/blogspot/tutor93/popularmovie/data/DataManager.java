package id.co.blogspot.tutor93.popularmovie.data;


import id.co.blogspot.tutor93.popularmovie.BuildConfig;
import id.co.blogspot.tutor93.popularmovie.data.model.Movies;
import id.co.blogspot.tutor93.popularmovie.data.model.Reviews;
import id.co.blogspot.tutor93.popularmovie.data.model.Videos;
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

    public void getPopularMovies(RemoteCallback<Movies> listener) {
        mMovieService.getMoviePopular(BuildConfig.MOVIEDB_APIKEY).enqueue(listener);
    }

    public void getTopratedMovies(RemoteCallback<Movies> listener) {
        mMovieService.getMovieToprated(BuildConfig.MOVIEDB_APIKEY).enqueue(listener);
    }

    public void getReview(int id, RemoteCallback<Reviews> listener) {
        mMovieService.getReviews(id, BuildConfig.MOVIEDB_APIKEY).enqueue(listener);
    }

    public void getVideos(int id, RemoteCallback<Videos> listener) {
        mMovieService.getVideos(id, BuildConfig.MOVIEDB_APIKEY).enqueue(listener);
    }

}
