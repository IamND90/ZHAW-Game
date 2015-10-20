package productions.pa.zulugame.game.models;

import android.util.Log;

import java.util.List;

import productions.pa.zulugame.game.models.baseclasses.IModel;
import productions.pa.zulugame.game.models.items.Backpack;
import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.models.baseclasses.AModel;
import productions.pa.zulugame.game.models.baseclasses.AItem;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.manager.ContextManager;
import productions.pa.zulugame.game.manager.PersonManager;

/**
 * Created by Andrey on 15.10.2015.
 *
 * A Box can store items, which are filled up upon creation
 * you can take and use items out of it TODO and also put back
 */
public class Box extends AModel {

    //  ------------------------------------------------------------
    //  STATIC FINAL FIELDS
    //  ------------------------------------------------------------

    final static String TAG = "Box";

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------
    /**
     * @param hasBeenOpenes if the bos is opened the first time, you loose life, that why you need to know it it was already opened*/
    boolean hasBeenOpenes = false;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public Box() {
        super(TYPE.BOX, TYPE.STORAGE);
    }

    public Box(List<AItem> containingItems) {
        super(TYPE.BOX, TYPE.STORAGE);
        for(AItem item : containingItems){
            addModel(item);
        }
    }

    public Box addItem(IModel item) {
        addModel(item);
        return this;
    }


    //  ============================================================
    //  @Override METHODS
    //  ============================================================

    @Override
    public String getName() {
        return TAG;
    }

    /**
     * @return List of all contained items and their color*/
    @Override
    public String getDescription() {
        String description = "\nThis " + getColor().name()+ " box contains [" + getSubItems().size() + (getSubItems().size() > 1 ? "] items" : "] item");

        for (IModel item : getSubItems()) {
            description += ("\n" + item.getColor() + " " + item.getName());
        }

        return description;
    }

    /**
     * Handler the alternative functions of a command , for more information
     * goto base-function @AModel (super.processCommand)
     */
    @Override
    public Answer processCommand(Command command) {

        Answer answer = null;

        // Handle OPEN a box
        if (command.hasAttributeOf(HitWord.BOX)) {
            if (command.hasActionOf(HitWord.OPEN)) {

                ContextManager.get().setCurrentContext(Box.this);
                String message = "You opened the box ";
                if(!hasBeenOpenes){
                    PersonManager.get().getPerson().appendLife(IModel.LIFE_USED_OPEN_BOX);
                    hasBeenOpenes = true;
                    message += "and lost [" + IModel.LIFE_USED_OPEN_BOX + "] lifepoints:";
                }
                return new Answer(message + getDescription(), Answer.DECORATION.BOX_ITEMS);

            }
        }

        //  Needs to handle command ALL to take all items
        if (command.hasActionOf(HitWord.TAKE, HitWord.GET, HitWord.STORE)) {
            Log.i(TAG, "Getting all items out of the box");
            Backpack backpack =PersonManager.get().getPerson().getBackpack();

            // TAKE ALL ITEMS
            if (command.getPointer().equalsIgnoreCase(HitWord.ALL)) {
                answer = new Answer("Taking all items", Answer.DECORATION.ADDING);
                for (int i = 0; i < getSubItems().size(); i++) {

                    answer.addMessage(backpack.addItem(getSubItems().get(i)).getMessage());

                    if (!answer.getDecorationType().equals(Answer.DECORATION.FAIL)) {
                        getSubItems().remove(i--);
                    }else{
                        break;
                    }

                }
                return answer;
            }

        }


        return super.processCommand(command);
    }


}
