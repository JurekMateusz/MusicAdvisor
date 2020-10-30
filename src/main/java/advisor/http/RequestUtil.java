package advisor.http;

import advisor.args.ServerDetails;

public class RequestUtil {
    public static   String SPOTIFY_TOKEN_URI = "https://accounts.spotify.com/api/token";
    public static  String API = "https://api.spotify.com/v1/browse";
    public static  String NEWS_URL = API + "/new-releases?limit=";
    public static  String FEATURED_PLAYLIST_URL = API + "/featured-playlists?limit=";
    public static  String CATEGORIES_URL = API + "/categories";

    public static void init(ServerDetails details) {
        SPOTIFY_TOKEN_URI = details.getServerAccessPath() + "/api/token";
        API = details.getServerApiPath() + "/v1/browse";
        String limit = "limit=" + details.getNumberOfEntriesInPage();
        NEWS_URL = API + "/new-releases?" + limit;
        FEATURED_PLAYLIST_URL = API + "/featured-playlists?" + limit;
        CATEGORIES_URL = API + "/categories?"+limit;
    }
}
