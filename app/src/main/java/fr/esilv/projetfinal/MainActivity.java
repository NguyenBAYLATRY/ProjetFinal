package fr.esilv.projetfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesList;
    private TVshowAdapter adapter;

    private TVshowRepository moviesRepository;

    private List<Genre> movieGenres;

    private boolean isFetchingMovies;
    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRepository = TVshowRepository.getInstance();

        moviesList = findViewById(R.id.movies_list);

        setupOnScrollListener();

        getGenres();
    }
    @Override
    public void onActorSelected(Actor actor) {
        ActorActivity.start(this, actor.getName());
    }

    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
        moviesList.setLayoutManager(manager);
        moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingMovies) {
                        GetShow(currentPage + 1);
                    }
                }
            }
        });
    }

    private void getGenres() {
        moviesRepository.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                movieGenres = genres;
                GetShow(currentPage);
            }

            @Override
            public void onError() {
                //showError();
            }
        });
    }

    private void GetShow(int page) {
        isFetchingMovies = true;
        moviesRepository.getShows(page, new OnGetShowCallback() {
            @Override
            public void onSuccess(int page, List<TVshow> show) {
                Log.d("MoviesRepository", "Current Page = " + page);
                if (adapter == null) {
                    adapter = new TVshowAdapter(show, movieGenres);
                    moviesList.setAdapter(adapter);
                } else {
                    adapter.appendMovies(show);
                }
                currentPage = page;
                isFetchingMovies = false;
            }
            @Override
            public void onError() {
                //showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }
}
