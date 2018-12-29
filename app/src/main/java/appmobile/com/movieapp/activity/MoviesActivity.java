package appmobile.com.movieapp.activity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import appmobile.com.movieapp.GetData;
import appmobile.com.movieapp.adapter.MovieAdapter;
import appmobile.com.movieapp.OnItemClickListener;
import appmobile.com.movieapp.R;
import appmobile.com.movieapp.RetrofitClient;
import appmobile.com.movieapp.TheMovieDB;
import appmobile.com.movieapp.model.Movie;
import appmobile.com.movieapp.model.Movies;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesActivity extends AppCompatActivity {

    static final String LOG_TAG = MoviesActivity.class.getName();
    private RecyclerView mMovieRecyclerView;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mProgressBar;
    private GetData mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);

        // Récupère la référence du RecyclerView
        mMovieRecyclerView = findViewById(R.id.recycler_view);

        // Récupère la référence de la ProgressBar
        mProgressBar = findViewById(R.id.progressbar_view);

        // Créer un handler de l'instance Retrofit
        mService = RetrofitClient.getRetrofitInstance().create(GetData.class);

        // Définit le call
        Call<Movies>  call = mService.getMovies(
                TheMovieDB.API_KEY.toString(),
                TheMovieDB.LANGUAGE_FR.toString(),
                TheMovieDB.SORT_BY_POPULARITY.toString(),
                false,
                false,
                100);

        // Exécute la requête de manière asynchrone
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                // Masque la ProgressBar
                mProgressBar.setVisibility(View.GONE);

                if (response.body() == null) {
                    Toast.makeText(MoviesActivity.this,
                            "Aucun film à afficher",
                            Toast.LENGTH_SHORT).show();
                } else
                    loadDataList(response.body());
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Toast.makeText(MoviesActivity.this,
                        "Une erreur est survenue, chargemement inpossible",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.search_menu, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                // Set activity title to search query
                MoviesActivity.this.setTitle(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);

            // Définit le call
            Call<Movies> call = mService.searchMovie(
                    TheMovieDB.API_KEY.toString(),
                    TheMovieDB.LANGUAGE_FR.toString(),
                    query);

            // Exécute la requête de manière asynchrone
            call.enqueue(new Callback<Movies>() {
                @Override
                public void onResponse(Call<Movies> call, Response<Movies> response) {
                    if (response.body() == null) {
                        Toast.makeText(MoviesActivity.this,
                                "Aucun film trouvé",
                                Toast.LENGTH_SHORT).show();
                    } else
                        loadDataList(response.body());
                }

                @Override
                public void onFailure(Call<Movies> call, Throwable t) {
                    Toast.makeText(MoviesActivity.this,
                            "Une erreur est survenue, recherche impossible",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void loadDataList(final Movies movies) {
        // Construit un MovieAdapter avec la liste des films
        mMovieAdapter = new MovieAdapter(
                movies.getResults(),
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int position) {
                        Movie currentMovie = (movies.getResults().get(position));
                        Intent intent = new Intent(
                                MoviesActivity.this,
                                MovieDetailActivity.class);
                        intent.putExtra("movie_id", currentMovie.getId());
                        intent.putExtra("movie_title", currentMovie.getTitle());
                        startActivity(intent);
                    }
                });

        // Définit un LinearLayoutManager orienté verticalement (défaut)
        RecyclerView.LayoutManager layoutManager =
                new LinearLayoutManager(MoviesActivity.this);
        // Affecte le LayoutManager à la RecyclerView
        mMovieRecyclerView.setLayoutManager(layoutManager);

        // Affecte le MovieAdapter à la RecyclerView
        mMovieRecyclerView.setAdapter(mMovieAdapter);
    }
}
