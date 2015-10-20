package productions.pa.zulugame.game.models.items;

import android.util.Log;

import java.util.List;

import productions.pa.zulugame.game.Statistic;
import productions.pa.zulugame.game.manager.ContextManager;
import productions.pa.zulugame.game.manager.PersonManager;
import productions.pa.zulugame.game.manager.RiddleManager;
import productions.pa.zulugame.game.models.baseclasses.IModel;
import productions.pa.zulugame.game.models.baseclasses.AItem;
import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by Andrey on 15.10.2015.
 * A model of type Riddle: if you solve the content you get extra life points, if not, you lose the same amount
 */
public class Riddle extends AItem {

    public static final String HINT = "\nHint: Type answer like this 'answer:MYANSWER'";
    private static final String TAG = "Riddle";

    /**
     * @param name the title diplayed if not opened
     * @param content the riddle text
     * @param riddleLevel the amount of points you receive/lose if you try to answer
     * @param answer possible answers */
    boolean isSolved = false;
    private String name, content;
    private int riddleLevel = 0;
    private String answer[];

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public Riddle(String name, String riddle, int level, String... answer) {
        super(TYPE.RIDDLE);
        this.name = name;
        this.content = riddle;
        this.riddleLevel = level;
        this.answer = answer;
        spaceUsed = SPACE_RIDDLE;
    }


    /**
     * Intersects the answer to the riddle
     * Here are being handled the alternative functions of a command , for more information
     * goto base-function @AModel (super.processCommand)
     */

    @Override
    public Answer processCommand(Command command) {
        Log.i(TAG, "Processing command");
        if (command.hasActionOf(HitWord.OPEN, HitWord.USE, HitWord.SHOW)) {
            RiddleManager.get().setCurrentRiddle(this);
            return new Answer(content + HINT, Answer.DECORATION.RIDDLE);
        }
        if (command.getPointer().equalsIgnoreCase(HitWord.ANSWER)) {
            Log.i(TAG, "Answer incoming");
            if (isSolved)
                return new Answer("You have already solved this content", Answer.DECORATION.FAIL);
            if (isCorrectAnswer(command.getAttribute())) {
                Log.i(TAG, "Answer has value:" + command.getAttribute());
                Statistic.getCurrent().solvedRiddles++;
                if (!isSolved) {
                    PersonManager.get().getPerson().appendLife(getRiddleLevel());
                }
                isSolved = true;
                ContextManager.get().getCurrentcontext().removeItemById(getId());
                ContextManager.get().setCurrentContext(PersonManager.get().getPerson());
                return new Answer("Thats right! You gained [" + getRiddleLevel() + "] life", Answer.DECORATION.RIDDLE);
            }
            Statistic.getCurrent().failedRiddles++;
            PersonManager.get().getPerson().appendLife(-getRiddleLevel());
            return new Answer("Wrong! You lost [" + getRiddleLevel() + "] life", Answer.DECORATION.RIDDLE);
        }
        return super.processCommand(command);
    }


    @Override
    public String getName() {
        return "Riddle-" + name;
    }

    @Override
    public String getDescription() {
        return "Life gamble: [" + riddleLevel + "]";
    }

    public int getRiddleLevel() {
        return riddleLevel;
    }

    private boolean isCorrectAnswer(String answer) {
        for (String correct : this.answer) {
            if (correct.equalsIgnoreCase(answer.replaceAll("\t", ""))) return true;
        }
        return false;
    }


    /**
     * null because it has no subitems*/
    @Override
    public List<IModel> getSubItems() {
        return null;
    }



}
