package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.story.quests.Quest;

/**
 * Created by IamND on 15.10.2015.
 */
public class QuestManager {

    private static QuestManager mThis = null;

    Quest currentQuest = null;

    public static QuestManager get(){
        if(mThis == null){
            synchronized (QuestManager.class){
                mThis = new QuestManager();
            }
        }
        return mThis;
    }



    public Answer processAnswer(Command command) {
        if(currentQuest == null)
            return new Answer("No Quest you are working on", Answer.TYPE.ITEM_NOT_FOUND);

        currentQuest.processAnswer(command);

        return null;
    }
}
