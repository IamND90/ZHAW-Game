package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.parser.Command;

/**
 * Created by Andrey on 09.10.2015.
 */
public interface IModel {

    String getName();
    String getDescription();

    void executeCommand(Command command);

}
