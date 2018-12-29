package appmobile.com.movieapp.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre {
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

    @SerializedName("name")
    @Expose
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /** CONSTRUCTORS */
    public Genre() { }

    public Genre(Integer id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
}
