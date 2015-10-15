package productions.pa.zulugame.game.models;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;

/**
 * Created by Andrey on 09.10.2015.
 */
public interface IModel {


    String getName();
    String getDescription();

    /**
     * @return String that should be diplayed*/
    Answer executeCommand(Command command);

    enum TYPE{
        UNKNOW,

        ITEM,
        PERSON,
        PLACE,
        BOX,

        QUEST
    }
}
