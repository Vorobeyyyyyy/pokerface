package by.bsuir.pokerface.command;

import by.bsuir.pokerface.command.impl.StartCommand;
import by.bsuir.pokerface.exception.ControllerException;
import by.bsuir.pokerface.responce.AbstractResponse;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;

public enum GameCommandProvider implements GameCommand {
    START_GAME(new StartCommand());

    GameCommand command;

    GameCommandProvider(GameCommand command) {
        this.command = command;
    }

    @Override
    public AbstractResponse perform(HttpServletRequest request) throws ControllerException {
        return command.perform(request);
    }

    public static Optional<GameCommand> defineCommand(String commandName) {
        if (commandName == null) {
            return Optional.empty();
        }

        commandName = commandName.toUpperCase(Locale.ROOT);
        GameCommand command;
        try {
            command = GameCommandProvider.valueOf(commandName);
        } catch (IllegalArgumentException exception) {
            command = null;
        }
        return Optional.ofNullable(command);
    }


}
