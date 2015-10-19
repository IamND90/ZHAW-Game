package productions.pa.zulugame.game.models.items;

import android.util.Log;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.Statistic;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.models.baseclasses.AItem;
import productions.pa.zulugame.game.models.baseclasses.AModel;
import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by IamND on 09.10.2015.
 * <p/>
 * This is the backack of the player, where he can store his items
 * It has limited space
 */
public class Backpack extends AModel {

    private static final String TAG = "BackPack";
    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public Backpack() {
        super(TYPE.ITEM);
    }

    //  ============================================================
    //  GETTERS / FINDERS
    //  ============================================================

    @Override
    public String getName() {
        return getType().name();
    }

    @Override
    public String getDescription() {
        String descr = getName() + ":\t\t" + getSpaceUsedByItems() + "/" + getSpaceUsed() + " space used.";

        int index = 1;
        for (IModel item : getSubItems()) {
            if (((AItem) item).isShowed())
                descr += ("\n\t" + index++ + ".\t[" + item.getColor().name() + "\t" + item.getName() + "](" + item.getDescription() + ")");
        }

        return descr;
    }

    /**
     * Here are being handled the alternative functions of a command , for more information
     * goto base-function @AModel (super.processCommand)
     */
    @Override
    public Answer processCommand(Command command) {

        //  Check if its empty, else process command down
        if (command.hasActionOf(HitWord.USE)) {
            if (getSubItems().isEmpty()) {
                return new Answer(MessageFactory.MESSAGE_BACKPACK_IS_EMPTY, Answer.DECORATION.FAIL);
            }

        }

        return super.processCommand(command);
    }


    /**
     * Adds an item to the backpack if enough space etc
     *
     * @return Answer to process on UI
     */
    public Answer addItem(IModel model) {
        Log.i(TAG,"Trying to add item:"+model.getColor() + " " + model.getName() );
        AItem item = (AItem) model;
        if (getSpaceLeft() > item.getSpaceUsed()) {
            if (findByNameAndColor(item.getName(), item.getColor().name()) == null) {
                getSubItems().add(item);
                Statistic.getCurrent().itemFound(item);
                Log.i(TAG, "Added item:" + model.getColor() + " " + model.getName());
                return new Answer("Added " + item.getColor().name() + " " + item.getName() + " to backpack", Answer.DECORATION.ADDING);
            } else {
                Log.i(TAG,"Item has double");
                return new Answer("You cant have two items of the same color in your backpack", Answer.DECORATION.FAIL);
            }
        }
        return new Answer("You have not enough space to add " + item.getName() + " to your backpack", Answer.DECORATION.FAIL);
    }

    public int getSpaceLeft() {
        return getSpaceUsed() - getSpaceUsedByItems();
    }

    public int getSpaceUsed() {
        return SPACE_BACPACK;
    }

    private int getSpaceUsedByItems() {
        int space = 0;
        for (IModel model : getSubItems()) {
            space += ((AItem) model).getSpaceUsed();
        }
        return space;
    }

}
