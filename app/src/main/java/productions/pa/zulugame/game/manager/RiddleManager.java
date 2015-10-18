package productions.pa.zulugame.game.manager;

import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.models.items.Riddle;

/**
 * Created by IamND on 15.10.2015.
 */
public class RiddleManager {

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------

    /**
     * @param mThis keeping the context on itself for not beeing deleted by java and beeing accessible from everywhere*/
    private static RiddleManager mThis = null;

    private Riddle currentRiddle = null;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public static RiddleManager get(){
        if(mThis == null){
            synchronized (RiddleManager.class){
                mThis = new RiddleManager();
            }
        }
        return mThis;
    }

    public void setCurrentRiddle(Riddle riddle){
        currentRiddle = riddle;
    }
    public Answer processCommand(Command command) {
        if(currentRiddle == null)
            return new Answer("No Quest you are working on", Answer.DECORATION.SIMPLE);

        return currentRiddle.processCommand(command);
    }
}
