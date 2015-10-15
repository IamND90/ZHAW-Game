package productions.pa.zulugame.game.story.quests;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;

/**
 * Created by IamND on 15.10.2015.
 */
public class Quest1 extends Quest {


    public Quest1() {
        super("Quest 1", "How long is the third side of a rectangular triangle if the other two are 3 and 4?");
    }


    @Override
    public Answer executeCommand(Command command) {
        if(command.getAttribute().getString().equals("5")){
            return new Answer("Thats right!", Answer.TYPE.SUCCESS);
        }

        return new Answer("Wrong!", Answer.TYPE.FAIL);
    }
}
