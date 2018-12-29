package appmobile.com.movieapp;

public final class Utils {
    // Retourne une chaine de caracètre inférieure à X caractères
    public static String shrinkString(String string, int nbChar) {
        if (string.length() > nbChar) {
            String shrinkedString = string.substring(0, Math.min(string.length(), nbChar));
            shrinkedString = shrinkedString.trim();
            shrinkedString += "...";
            return shrinkedString;
        }
        else return string;
    }

    // Retourne l'année sur une date type YYYY-MM-DD
    public static String getYear(String date) {
        return date.split("-")[0];
    }
}
