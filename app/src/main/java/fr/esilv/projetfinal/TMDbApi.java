package fr.esilv.projetfinal;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TMDbApi {

    @GET("movie/popular")
    Call<TVshowResponse> getPopularShows(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );
    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );
}
