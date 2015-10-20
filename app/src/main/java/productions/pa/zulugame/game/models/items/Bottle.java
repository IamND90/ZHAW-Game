package productions.pa.zulugame.game.models.items;

import android.os.SystemClock;
import android.util.Log;

import java.util.Random;

import productions.pa.zulugame.game.manager.PersonManager;
import productions.pa.zulugame.game.models.baseclasses.AItem;
import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by IamND on 17.10.2015.
 */
public class Bottle extends AItem {

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------

    /**
     * @param fullLife the amout of life in the bottle when created
     * @param lifeLeft amount of life left in the bottle
     */
    final int fullLife;
    int lifeLeft;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public Bottle(int life) {
        super(TYPE.BOTTLE);
        lifeLeft = life;
        fullLife = life;
        spaceUsed = SPACE_BOTTLE;
    }

    public Bottle() {
        super(TYPE.BOTTLE);
        lifeLeft = Math.abs(new Random().nextInt()) % MAXIMUM_LIFE_BOTTLE;
        Log.i("Bottle","Random:" + lifeLeft);
        if (lifeLeft < MINIMUM_LIFE_BOTTLE) lifeLeft = MINIMUM_LIFE_BOTTLE;
        fullLife = lifeLeft;
        spaceUsed = SPACE_BOTTLE;
    }

    public int getLife() {
        return lifeLeft;
    }

    @Override
    public String getName() {
        return TYPE.BOTTLE.name();
    }

    @Override
    public String getDescription() {
        return "Contains [" + lifeLeft + "] life points";
    }

    /**
     * Handler the alternative functions of a command , for more information
     * goto base-function @AModel (super.processCommand)
     */
    @Override
    public Answer processCommand(Command command) {
        if(command.hasAttributeOf(getName(),getType().name())) {
            if (command.hasActionOf(HitWord.USE)) {
                if (lifeLeft <= 0) {
                    return new Answer("Bottle is empty", Answer.DECORATION.FAIL);
                }
                int healedBy = PersonManager.get().getPerson().appendLife(lifeLeft);
                if (healedBy != lifeLeft) {
                    //  Set new life munt in bottle
                    lifeLeft -= healedBy;
                    return new Answer("Your life is full", Answer.DECORATION.SIMPLE);
                }
                return new Answer("Your life has been increased by " + healedBy, Answer.DECORATION.SIMPLE);
            }
        }
        return super.processCommand(command);
    }
}
