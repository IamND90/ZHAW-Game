package productions.pa.zulugame.game.models.baseclasses;

import productions.pa.zulugame.game.manager.PersonManager;
import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by IamND on 09.10.2015.
 *
 * This class is a prent for an item like model: Key, Bottle etc..
 * It has the property, that it uses space and can be stored in a backpack
 */
public abstract class AItem extends AModel {


    /**
     * @param spaceUsed amount of space claimed by the item
     * @param isShowed Little hack due to not flexible levels and rooms, if not showed -> "it does not exist anymore"
     */
    protected int spaceUsed = 0;
    boolean isShowed = true;

    public AItem(TYPE type) {
        super(type, TYPE.ITEM);
    }

    /**
     * Here are being handled the alternative functions of a command , for more information
     * goto base-function @AModel (super.processCommand)
     */
    @Override
    public Answer processCommand(Command command) {

        //  Add Item to your backpack
        if (command.hasActionOf(HitWord.GET, HitWord.TAKE)) {
            return PersonManager.get().getPerson().getBackpack().addItem(this);
        }
        //  Remove the item from your backpack
        if (command.hasActionOf(HitWord.DROP, HitWord.REMOVE)) {
            if (PersonManager.get().getPerson().getBackpack().removeItemById(this.getId())) {
                return new Answer("Removed " + getColor().name() + " " + getName() + " from your backpack, " + getName() + " is lost.", Answer.DECORATION.REMOVING);
            }
        }

        return super.processCommand(command);
    }

    /**
     * @return amount of space it uses in a backpack or box etc..
     */
    public int getSpaceUsed() {
        if (isShowed)
            return spaceUsed;
        return 0;
    }

    public boolean isShowed() {
        return isShowed;
    }

    protected void setShowed(boolean showed) {
        isShowed = showed;
    }
}
