package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.quests.Riddle;

/**
 * Created by IamND on 15.10.2015.
 */
public class RiddleManager {

    private static RiddleManager mThis = null;

    Riddle currentQuest = null;

    public static RiddleManager get(){
        if(mThis == null){
            synchronized (RiddleManager.class){
                mThis = new RiddleManager();
            }
        }
        return mThis;
    }



    public Answer processAnswer(Command command) {
        if(currentQuest == null)
            return new Answer("No Quest you are working on", Answer.TYPE.ITEM_NOT_FOUND);

        currentQuest.processCommand(command);

        return null;
    }
}
