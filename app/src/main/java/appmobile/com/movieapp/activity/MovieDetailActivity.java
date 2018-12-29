package appmobile.com.movieapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import appmobile.com.movieapp.GetData;
import appmobile.com.movieapp.R;
import appmobile.com.movieapp.RetrofitClient;
import appmobile.com.movieapp.TheMovieDB;
import appmobile.com.movieapp.Utils;
import appmobile.com.movieapp.model.Genre;
import appmobile.com.movieapp.model.Movie;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {

    static final String LOG_TAG = MoviesActivity.class.getName();
    private ProgressBar mProgressBar;
    private ShareActionProvider mShareActionProvider;
    private Intent mShareIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Récupère la référence de la ProgressBar
        mProgressBar = findViewById(R.id.progressbar_view);

        // Récupère l'id du film cliqué
        Integer movieId = getIntent().getIntExtra("movie_id", -1);

        // Récupère le nom du film cliqué
        final String movieName = getIntent().getStringExtra("movie_title");

        // Créer un handler de l'instance Retrofit
        GetData service = RetrofitClient.getRetrofitInstance().create(GetData.class);

        // Définit le call avec la requête getMovies
        Call<Movie> call = service.getMovie(
                movieId,
                TheMovieDB.API_KEY.toString(),
                TheMovieDB.LANGUAGE_FR.toString());

        // Exécute la requête de manière asynchrone
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                // Masque la ProgressBar
                mProgressBar.setVisibility(View.GONE);

                if (response.body() == null) {
                    Toast.makeText(MovieDetailActivity.this,
                            "Impossible de charger le film souhaité",
                            Toast.LENGTH_SHORT).show();
                } else
                    // Modifie l'intent pour y ajouter le content à partager
                    // contenant le nom du film
                    mShareIntent.putExtra(
                            Intent.EXTRA_TEXT,
                            "Tu dois voir ce film ! '" + movieName + "'");

                    // Attache l'intent à l'action provider de nouveau
                    mShareActionProvider.setShareIntent(mShareIntent);

                    // Appelle showMovie avec l'object de type Movie
                    showMovie(response.body());
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(MovieDetailActivity.this,
                        "Une erreur est survenue lors du chargement du film",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate le menu avec share_menu.xml
        getMenuInflater().inflate(R.menu.share_menu, menu);

        // Récupère la référence de l'item action_share
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Récupère la référence au ShareActionProvider en passant le menu item action_share
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // Créer un nouvel intent ACTION_SEND set du type de content partagé à plain text
        mShareIntent = new Intent(Intent.ACTION_SEND);
        mShareIntent.setType("text/plain");

        // Attache l'intent à l'action provider
        mShareActionProvider.setShareIntent(mShareIntent);

        return super.onCreateOptionsMenu(menu);
    }

    private void showMovie(Movie movie) {
        // Affiche le titre du film dans l'app bar
        MovieDetailActivity.this.setTitle(movie.getTitle());

        // Récupère les vues à manipuler
        String posterUrl = TheMovieDB.THUMBNAIL_W500.toString() + movie.getPosterPath();
        ImageView poster = findViewById(R.id.poster);
        String backdropUrl = TheMovieDB.THUMBNAIL_W500.toString() + movie.getBackdropPath();
        ImageView backdrop = findViewById(R.id.backdrop);
        TextView title = findViewById(R.id.title);
        TextView dateAndTime = findViewById(R.id.date_and_time);
        TextView genres = findViewById(R.id.genre);
        TextView overview = findViewById(R.id.overview);

        // Set la pochette avant
        Picasso.get()
                .load(posterUrl)
                .placeholder(R.drawable.no_image)
                .into(poster);

        // Set la pochette arrière
        Picasso.get()
                .load(backdropUrl)
                .placeholder(R.drawable.no_image)
                .into(backdrop);

        // Set du titre
        title.setText(movie.getTitle());

        // Set de la date et du temps
        String sDateAndTime =
                Utils.getYear(movie.getReleaseDate()) +
                " | " +
                movie.getRuntime() +
                "min";
        dateAndTime.setText(sDateAndTime);

        // Set des genres
        StringBuilder sGenres = new StringBuilder();
        for (Genre g : movie.getGenres()) {
            sGenres.append(g.getName()).append(", ");
        }
        genres.setText(sGenres.substring(0, sGenres.length() - 2));

        // Set de l'overview
        overview.setText(movie.getOverview());
    }
}
