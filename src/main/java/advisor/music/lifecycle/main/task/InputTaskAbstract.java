package advisor.music.lifecycle.main.task;

import advisor.args.ServerDetails;
import advisor.exception.ContentNotFoundException;
import advisor.http.service.RequestService;
import advisor.model.api.categories.Categories;
import advisor.model.api.categories.Category;
import advisor.model.api.news.Albums;
import advisor.model.api.news.Song;
import advisor.model.api.playlist.Playlist;
import advisor.model.view.Result;
import advisor.music.lifecycle.Task;
import advisor.music.lifecycle.UserInput;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class InputTaskAbstract {
    protected static final RequestService service = RequestService.getInstance();
    protected static final String newLine = System.lineSeparator();
    public static ServerDetails serverDetails;
    //todo delete it
    public static int lastPage;
    public static Albums newsByUrl;
    public static Playlist playlistsByUrl;
    public static Categories topCategoriesByUrl;
    protected static List<Category> categories = Collections.emptyList();
    protected static OffsetInfo offsetInfo = new OffsetInfo();
    protected static Task previousTask;

    protected static Result takePageFromUrl(String accessToken, String url) throws IOException, InterruptedException {
        if (lastPage == -1) {
            lastPage = 0;
            return Result.of("No more pages");
        }
        switch (previousTask) {
            case NEW:
//                Albums newsByUrl = service.getNewsByUrl(accessToken, url);
//                updateOffsetInfo(newsByUrl);
                return createResultOf(newsByUrl);
            case FEATURED:
            case PLAYLISTS:
//                Playlist playlistsByUrl = service.getPlaylistsByUrl(accessToken, url);
//                updateOffsetInfo(playlistsByUrl);
                return createResultOf(playlistsByUrl);
            case CATEGORIES:
//                Categories topCategoriesByUrl = service.getTopCategoriesByUrl(accessToken, url);
//                updateOffsetInfo(topCategoriesByUrl);
                return createResultOf(topCategoriesByUrl);
            default:
                return Result.of("Default in NextTask/previous");
        }
    }

    protected static void updateOffsetInfo(Albums albums) {
        offsetInfo.setNext(albums.getNext());
        offsetInfo.setOffset(albums.getOffset());
        offsetInfo.setPrevious(albums.getPrevious());
        offsetInfo.setTotal(albums.getTotal());
        offsetInfo.setLimit(albums.getLimit());
    }

    protected static void updateOffsetInfo(Categories categories) {
        offsetInfo.setNext(categories.getNext());
        offsetInfo.setOffset(categories.getOffset());
        offsetInfo.setPrevious(categories.getPrevious());
        offsetInfo.setTotal(categories.getTotal());
        offsetInfo.setLimit(categories.getLimit());
    }

    protected static void updateOffsetInfo(Playlist playlist) {
        offsetInfo.setNext(playlist.getNext());
        offsetInfo.setOffset(playlist.getOffset());
        offsetInfo.setPrevious(playlist.getPrevious());
        offsetInfo.setTotal(playlist.getTotal());
        offsetInfo.setLimit(playlist.getLimit());
    }

    //    protected static Result createResultOf(Categories categories) {
//        StringBuilder builder = new StringBuilder();
//        categories.getCategories().forEach(category -> builder
//                .append(category.getName())
//                .append(newLine)
//        );
//        builder.append(newLine)
//                .append(createPageInfo())
//                .append(newLine);
//        return Result.of(builder.toString());
//    }
//
//    protected static Result createResultOf(Playlist playlist) {
//        StringBuilder builder = new StringBuilder();
//        playlist.getSongs().forEach(song -> builder
//                .append(song.getName())
//                .append(newLine)
//                .append(song.getExternalUrls().toString())
//                .append(newLine)
//        );
//        builder.append(newLine)
//                .append(createPageInfo())
//                .append(newLine);
//        return Result.of(builder.toString());
//    }
//
//    protected static Result createResultOf(Albums albums) {
//        StringBuilder builder = new StringBuilder();
//        albums.getSongs().forEach(song -> builder
//                .append(song.getName())
//                .append(newLine)
//                .append(Arrays.toString(song.getArtists().toArray()))
//                .append(newLine)
//                .append(song.getExternalUrls().toString())
//                .append(newLine)
//        );
//        builder.append(newLine)
//                .append(createPageInfo())
//                .append(newLine);
//        return Result.of(builder.toString());
//    }
    protected static Result createResultOf(Categories categories) {
        StringBuilder builder = new StringBuilder();
        for (int i = lastPage; i < lastPage + serverDetails.getNumberOfEntriesInPage(); i++) {
            Category category = categories.getCategories().get(i);
            builder.append(category.getName())
                    .append(newLine);
        }
        builder.append(newLine)
                .append(createPageInfo())
                .append(newLine);
        return Result.of(builder.toString());
    }

    protected static Result createResultOf(Playlist playlist) {
        StringBuilder builder = new StringBuilder();

        for (int i = lastPage; i < lastPage + serverDetails.getNumberOfEntriesInPage(); i++) {
            advisor.model.api.playlist.Song song1 = playlist.getSongs().get(i);
            builder.append(song1.getName())
                    .append(newLine)
                    .append(song1.getExternalUrls().toString())
                    .append(newLine);
        }
        builder.append(newLine)
                .append(createPageInfo())
                .append(newLine);
        return Result.of(builder.toString());
    }


    protected static Result createResultOf(Albums albums) {
        StringBuilder builder = new StringBuilder();
        if(albums.getSongs().size() == lastPage){
            lastPage--;
            return Result.of("No more pages");
        }
        for (int i = lastPage; i < lastPage + serverDetails.getNumberOfEntriesInPage(); i++) {
            Song song = albums.getSongs().get(i);
            builder.append(song.getName())
                    .append(newLine)
                    .append(Arrays.toString(song.getArtists().toArray()))
                    .append(newLine)
                    .append(song.getExternalUrls().toString())
                    .append(newLine);
        }
        builder.append(newLine)
                .append(createPageInfo())
                .append(newLine);
        return Result.of(builder.toString());
    }

    protected static String createPageInfo() {
        int page = InputTaskAbstract.lastPage + 1;
        int totalPages = offsetInfo.getTotal();
        return "---PAGE " + page + " OF " + totalPages + "---";
    }

//    protected static String createPageInfo() {
//        int page = offsetInfo.getOffset() / offsetInfo.getLimit();
//        int totalPages = offsetInfo.getTotal() / offsetInfo.getLimit();
//        if (totalPages % offsetInfo.getLimit() != 0) totalPages += 1;
//        return "---PAGE " + page + " OF " + totalPages + "---";
//    }

    //todo method needed only to pass test in 5 stage. Server in tests dont behave like spotify server.
    public void setDetails(ServerDetails serverDetails) {
        InputTaskAbstract.serverDetails = serverDetails;
    }

    public abstract Result perform(String accessToken, UserInput input) throws IOException, InterruptedException,
            ContentNotFoundException, InterruptedException;
}