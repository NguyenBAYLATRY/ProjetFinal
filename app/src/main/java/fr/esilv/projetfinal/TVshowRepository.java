package fr.esilv.projetfinal;

import android.support.annotation.NonNull;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class TVshowRepository {

    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static final String LANGUAGE = "en-US";
    private static final String TMDB_API_KEY = "1d8acf8ba0231b736fd297d8abd4766b";

    private static TVshowRepository repository;

    private TMDbApi api;

    private TVshowRepository(TMDbApi api) {
        this.api = api;
    }

    public static TVshowRepository getInstance() {
        if (repository == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            repository = new TVshowRepository(retrofit.create(TMDbApi.class));
        }

        return repository;
    }

    public void getShows(int page, final OnGetShowCallback callback) {
        Log.d("TVshowRepository", "Next Page = " + page);
        api.getPopularShows("1d8acf8ba0231b736fd297d8abd4766b", LANGUAGE, page)
                .enqueue(new Callback<TVshowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TVshowResponse> call, @NonNull Response<TVshowResponse> response) {
                        if (response.isSuccessful()) {
                            TVshowResponse TVshowResponse = response.body();
                            if (TVshowResponse != null && TVshowResponse.getShows() != null) {
                                callback.onSuccess(TVshowResponse.getPage(), TVshowResponse.getShows());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<TVshowResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }



    public void getGenres(final OnGetGenresCallback callback) {
        api.getGenres(TMDB_API_KEY, LANGUAGE)
                .enqueue(new Callback<GenreResponse>() {
                    @Override
                    public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {
                       if (response.isSuccessful()) {
                            GenreResponse genresResponse = response.body();
                            if (genresResponse != null && genresResponse.getGenres() != null) {
                                callback.onSuccess(genresResponse.getGenres());
                            } else {
                                callback.onError();
                            }
                          callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenreResponse> call, Throwable t) {
                        callback.onError();
                    }
                });
    }
}
