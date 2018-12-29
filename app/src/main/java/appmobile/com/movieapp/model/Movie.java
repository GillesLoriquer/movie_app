package appmobile.com.movieapp.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    /** ATTRIBUTES */
    @SerializedName("id")
    @Expose
    private Integer id;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    @SerializedName("title")
    @Expose
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    @SerializedName("poster_path")
    @Expose
    @Nullable
    private String posterPath;
    public String getPosterPath() {
        return posterPath;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @SerializedName("backdrop_path")
    @Expose
    @Nullable
    private String backdropPath;
    public String getBackdropPath() {
        return backdropPath;
    }
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @SerializedName("overview")
    @Expose
    private String overview;
    public String getOverview() {
        return overview;
    }
    public void setOverview(String overview) {
        this.overview = overview;
    }

    @SerializedName("release_date")
    @Expose
    private String releaseDate;
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @SerializedName("runtime")
    @Expose
    private String runtime;
    public String getRuntime() {
        return runtime;
    }
    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    @SerializedName("genres")
    @Expose
    private List<Genre> genres = null;
    public List<Genre> getGenres() {
        return genres;
    }
    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    /** CONSTRUCTORS */
    public Movie() { }

    public Movie(
            Integer id,
            String title,
            @Nullable String posterPath,
            @Nullable String backdropPath,
            String overview,
            String releaseDate,
            List<Genre> genres) {
        this.id = id;
        this.title = title;
        this.posterPath = posterPath;
        this.backdropPath = backdropPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genres = genres;
    }
}
