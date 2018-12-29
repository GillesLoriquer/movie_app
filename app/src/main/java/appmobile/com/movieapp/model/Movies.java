package appmobile.com.movieapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movies {
    /** ATTRIBUTES */
    @SerializedName("results")
    @Expose
    private List<Movie> results = null;
    public List<Movie> getResults() {
        return results;
    }
    public void setResults(List<Movie> results) {
        this.results = results;
    }

    /** CONSTRUCTORS */
    public Movies() {
    }

    public Movies(List<Movie> results) {
        this.results = results;
    }
}
