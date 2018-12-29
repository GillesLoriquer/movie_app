package appmobile.com.movieapp;

public enum TheMovieDB {
    //Objets directement construits
    BASE_URL("https://api.themoviedb.org/3/"),
    THUMBNAIL_W500("https://image.tmdb.org/t/p/w500/"),
    THUMBNAIL_ORI("https://image.tmdb.org/t/p/original/"),
    API_KEY("c3c040ac65375efea4bded3bed2fa2ca"),
    LANGUAGE_FR("fr-FR"),
    SORT_BY_POPULARITY("popularity.desc");

    /** ATTRIBUTES */
    private String mString;

    /** CONSTRUCTOR */
    TheMovieDB(String string){
        this.mString = string;
    }

    /** METHOD */
    public String toString(){
        return mString;
    }
}
