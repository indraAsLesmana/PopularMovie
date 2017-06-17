package id.co.blogspot.tutor93.popularmovie.data.network;

import id.co.blogspot.tutor93.popularmovie.data.model.MoviePopular;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by indraaguslesmana on 6/8/17.
 */

public interface MovieService {

    /**
     * Retrive popular movies
     * */
    @GET("movie/popular")
    Call<MoviePopular> getMoviePopular(@Query("api_key") String api_key,
                                       @Query("language") String language,
                                       @Query("page") String page,
                                       @Query("region") String region);

}
