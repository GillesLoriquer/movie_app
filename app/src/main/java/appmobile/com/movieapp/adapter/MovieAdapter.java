package appmobile.com.movieapp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import appmobile.com.movieapp.OnItemClickListener;
import appmobile.com.movieapp.R;
import appmobile.com.movieapp.TheMovieDB;
import appmobile.com.movieapp.Utils;
import appmobile.com.movieapp.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    /** ATTRIBUTES */
    private List<Movie> mMovies;
    private OnItemClickListener mListener;

    /** CONSTRUCTOR */
    public MovieAdapter(List<Movie> dataList, OnItemClickListener listener) {
        this.mMovies = dataList;
        this.mListener = listener;
    }

    /** INNER CLASS */
    class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView movieThumbnail;
        private TextView movieTitle;
        private TextView movieReleaseDate;
        private TextView movieOverview;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            movieThumbnail = itemView.findViewById(R.id.thumbnail);
            movieTitle = itemView.findViewById(R.id.title);
            movieReleaseDate = itemView.findViewById(R.id.release_date);
            movieOverview = itemView.findViewById(R.id.overview);
        }
    }

    /** METHODS */
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.row_layout,
                parent,
                false);

        final MovieViewHolder movieViewHolder = new MovieViewHolder(movieView);

        movieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onItemClick(view, movieViewHolder.getAdapterPosition());
            }
        });

        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie currentMovie = mMovies.get(position);
        String thumbnailUrl = TheMovieDB.THUMBNAIL_W500.toString() + currentMovie.getPosterPath();

        Picasso.get()
                .load(thumbnailUrl)
                .placeholder(R.drawable.no_image)
                .into(holder.movieThumbnail);
        holder.movieTitle.setText(currentMovie.getTitle());
        holder.movieReleaseDate.setText(Utils.getYear(currentMovie.getReleaseDate()));
        holder.movieOverview.setText(Utils.shrinkString(currentMovie.getOverview(), 150));
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

}
