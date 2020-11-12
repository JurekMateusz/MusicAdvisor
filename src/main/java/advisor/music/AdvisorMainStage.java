package advisor.music;

import advisor.http.service.RequestService;
import advisor.model.api.categories.Categories;
import advisor.model.api.news.Albums;
import advisor.model.api.playlist.Playlist;
import advisor.model.token.AccessToken;
import advisor.music.commands.Command;
import advisor.music.commands.CommandHistory;
import advisor.music.commands.categories.CategoriesCommand;
import advisor.music.commands.exit.ExitCommand;
import advisor.music.commands.featured.FeaturedCommand;
import advisor.music.commands.info.InfoCommand;
import advisor.music.commands.news.NewsCommand;
import advisor.music.commands.nextprevious.NextCommand;
import advisor.music.commands.nextprevious.NoNextPreviousCommand;
import advisor.music.commands.nextprevious.PreviousCommand;
import advisor.music.commands.playlist.PlaylistCommand;
import advisor.music.commands.unknown.UnknownCommand;
import advisor.music.guardian.AccessTokenGuardian;
import advisor.music.input.Input;
import advisor.music.input.InputProvider;
import io.vavr.control.Try;

import java.util.Objects;

public class AdvisorMainStage {
    private final CommandHistory history = new CommandHistory();
    private final InputProvider inputProvider;
    private final RequestService service;
    private final Command info = new InfoCommand();
    private AccessToken accessToken;

    AdvisorMainStage(RequestService service, InputProvider inputProvider, AccessToken accessToken) {
        assert Objects.nonNull(accessToken);

        this.service = service;
        this.inputProvider = inputProvider;
        this.accessToken = accessToken;

        new AccessTokenGuardian(this,service,accessToken);
    }

    public void start() {
       info.execute();
        for(;;) {
            Input input = inputProvider.getUserInput();
            Command command = switch (input.getTask()) {
                case CATEGORIES -> new CategoriesCommand(service);
                case FEATURED -> new FeaturedCommand(service);
                case PLAYLIST -> new PlaylistCommand(service,input.getCategory());
                case NEWS -> new NewsCommand(service);
                case NEXT -> createCommand(service, NextCommand.class);
                case PREVIOUS -> createCommand(service, PreviousCommand.class);
                case EXIT -> new ExitCommand();
                case INFO -> info;
                default -> new UnknownCommand(input);
            };
            executeCommand(command);
        }
    }
    private Command createCommand(RequestService service, Class<?> commandClass) {
        if(history.isEmpty()){
            return new NoNextPreviousCommand();
        }
        Try<Categories> categoriesTry = Try.of(() -> (Categories) history.getDataFromLast());
        if (categoriesTry.isSuccess()) {
            return getCommandWithCategories(service, categoriesTry.get(), commandClass);
        }
        Try<Albums> albumsTry = Try.of(() -> (Albums) history.getDataFromLast());
        if (albumsTry.isSuccess()) {
            return getCommandWithAlbums(service, albumsTry.get(), commandClass);
        }
        Try<Playlist> playlistTry = Try.of(() -> (Playlist) history.getDataFromLast());
        if (playlistTry.isSuccess()) {
            return getCommandWithPlaylist(service, playlistTry.get(), commandClass);
        }
        throw new IllegalStateException("Unsupported data casting");
    }

    private Command getCommandWithCategories(
            RequestService service, Categories categories, Class<?> commandClass) {
        return commandClass.equals(NextCommand.class)
                ? new NextCommand(service, categories)
                : new PreviousCommand(service, categories);
    }

    private Command getCommandWithAlbums(
            RequestService service, Albums albums, Class<?> commandClass) {
        return commandClass.equals(NextCommand.class)
                ? new NextCommand(service, albums)
                : new PreviousCommand(service, albums);
    }

    private Command getCommandWithPlaylist(
            RequestService service, Playlist playlist, Class<?> commandClass) {
        return commandClass.equals(NextCommand.class)
                ? new NextCommand(service, playlist)
                : new PreviousCommand(service, playlist);
    }

    private void executeCommand(Command command) {
        if (command.execute()) {
            history.push(command);
        }
    }

    public void updateAccessToken(AccessToken accessToken) {
        service.setAccessToken(accessToken.getAccessToken());
        this.accessToken = accessToken;
    }
}
