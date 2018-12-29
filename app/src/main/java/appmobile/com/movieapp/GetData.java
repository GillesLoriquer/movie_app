package appmobile.com.movieapp;

import appmobile.com.movieapp.model.Movie;
import appmobile.com.movieapp.model.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetData {
    // Requête générale (lancement de l'application)
    @GET("discover/movie")
    Call<Movies> getMovies(@Query("api_key") String key,
                           @Query("language") String language,
                           @Query("sort_by") String sortBy,
                           @Query("include_adult") boolean isAdult,
                           @Query("include_video") boolean hasVideo,
                           @Query("page") Integer nbPage);

    // Requête de recherche de film
    @GET("search/movie")
    Call<Movies> searchMovie(@Query("api_key") String key,
                             @Query("language") String language,
                             @Query("query") String query);

    // Requête de recherche de film par id
    @GET("movie/{id}")
    Call<Movie> getMovie(@Path("id") Integer id,
                         @Query("api_key") String key,
                         @Query("language") String language);

}