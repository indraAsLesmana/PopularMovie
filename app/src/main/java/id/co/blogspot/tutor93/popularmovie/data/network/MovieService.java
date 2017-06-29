package id.co.blogspot.tutor93.popularmovie.data.network;

import id.co.blogspot.tutor93.popularmovie.data.model.Movie;
import id.co.blogspot.tutor93.popularmovie.data.model.Reviews;
import id.co.blogspot.tutor93.popularmovie.data.model.Videos;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by indraaguslesmana on 6/8/17.
 */

public interface MovieService {

    /**
     * Retrive popular movies
     */
    @GET("movie/popular")
    Call<Movie> getMoviePopular(@Query("api_key") String api_key);

    @GET("movie/popular")
    Call<Movie> getMoviePopular(@Query("api_key") String api_key,
                                @Query("language") String language,
                                @Query("page") String page,
                                @Query("region") String region);

    /**
     * Retrive top_rated movies
     */
    @GET("movie/top_rated")
    Call<Movie> getMovieToprated(@Query("api_key") String api_key);

    @GET("movie/top_rated")
    Call<Movie> getMovieToprated(@Query("api_key") String api_key,
                                 @Query("language") String language,
                                 @Query("page") String page,
                                 @Query("region") String region);

    /**
     * Retrive videos movies
     */
    @GET("movie/{id}/videos")
    Call<Videos> getVideos(@Path("id") int id,
                           @Query("api_key") String apiKey);

    /**
     * Retrive movies Reviews
     */
    @GET("movie/{id}/reviews")
    Call<Reviews> getReviews(@Path("id") int id,
                             @Query("api_key") String apiKey);

}
