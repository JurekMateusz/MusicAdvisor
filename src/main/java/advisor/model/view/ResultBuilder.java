package advisor.model.view;

import advisor.model.api.categories.Categories;
import advisor.model.api.news.Albums;
import advisor.model.api.playlist.Playlist;

import java.util.Arrays;

public class ResultBuilder {
  private static final String newLine = System.lineSeparator();

  public Result createResultOf(Categories categories) {
    StringBuilder builder = new StringBuilder();
    categories
        .getCategories()
        .forEach(category -> builder.append(category.getName()).append(newLine));

    builder.append(newLine).append(PageInfoCreator.createPageInfo(categories));
    return Result.of(builder.toString());
  }

  public Result createResultOf(Playlist playlist) {
    StringBuilder builder = new StringBuilder();
    playlist
        .getSongs()
        .forEach(
            song ->
                builder
                    .append(song.getName())
                    .append(newLine)
                    .append(song.getExternalUrls().toString())
                    .append(newLine));
    builder.append(newLine).append(PageInfoCreator.createPageInfo(playlist));
    return Result.of(builder.toString());
  }

  public Result createResultOf(Albums albums) {
    StringBuilder builder = new StringBuilder();
    albums
        .getSongs()
        .forEach(
            song ->
                builder
                    .append(song.getName())
                    .append(newLine)
                    .append(Arrays.toString(song.getArtists().toArray()))
                    .append(newLine)
                    .append(song.getExternalUrls().toString())
                    .append(newLine));
    builder.append(newLine).append(PageInfoCreator.createPageInfo(albums));
    return Result.of(builder.toString());
  }

  private static class PageInfoCreator {

    static String createPageInfo(Categories categories) {
      int offset = categories.getOffset();
      int limit = categories.getLimit();
      int totalPages = categories.getTotal();
      if (isTotalPagesModuloLimitIsOdd(totalPages, limit)) totalPages += 1;
      return concatPageInfo(offset, limit, totalPages);
    }

    static String createPageInfo(Albums albums) {
      int offset = albums.getOffset();
      int limit = albums.getLimit();
      int totalPages = albums.getTotal();
      if (isTotalPagesModuloLimitIsOdd(totalPages, limit)) totalPages += 1;
      return concatPageInfo(offset, limit, totalPages);
    }

    static String createPageInfo(Playlist playlist) {
      int offset = playlist.getOffset();
      int limit = playlist.getLimit();
      int totalPages = playlist.getTotal();
      if (isTotalPagesModuloLimitIsOdd(totalPages, limit)) totalPages += 1;
      return concatPageInfo(offset, limit, totalPages);
    }

    private static String concatPageInfo(int offset, int limit, int totalPages) {
      return "---PAGE "
          + countCurrentPage(offset, limit)
          + " OF "
          + countTotalPages(limit, totalPages)
          + "---";
    }

    private static boolean isTotalPagesModuloLimitIsOdd(int totalPages, int limit) {
      return totalPages % limit != 0;
    }

    private static String countTotalPages(int limit, int totalPages) {
      int pages = totalPages / limit;
      if (totalPages % limit != 0) pages += 1;
      return String.valueOf(pages);
    }

    private static String countCurrentPage(int offset, int limit) {
      return String.valueOf(offset / limit + 1);
    }
  }
}
