package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.AModel;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.models.items.Item;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.story.ModelManager;

/**
 * Created by IamND on 09.10.2015.
 */
public class Backpack extends Item {

    //TODO add space
    final int MAXIMUM_SPACE = 50;



    public Backpack() {
        super(ModelManager.ID_BACKPACK, null);
    }

    @Override
    public String getName() {
        return "Backpack";
    }

    @Override
    public String getDescription() {
        return "Here you can store your items";
    }

    @Override
    public Answer processCommand(Command command) {

        if (command.getAction().equals(HitWordFactory.GET) || command.getAction().equals(HitWordFactory.USE)) {
            if(getSubItems().isEmpty()){return new Answer(MessageFactory.MESSAGE_BACKPACK_IS_EMPTY).setContextId(getId());}

            if(command.getAttribute() != null){
                Item foundItem = getItem(-1,null,command.getAttribute().getString());
                if(foundItem == null)return new Answer(MessageFactory.MESSAGE_BACKPACK_DOES_NOT_HAS_ITEMS).setContextId(getId());

                ModelManager.get().setCurrentContext(this);
                //TODO what todo with the item?
            }
        }

        return null;
    }

    @Override
    public Answer interactWithItem(Item item) {
        return null;
    }

    public boolean addItem(Item item) {

        if(getLeftSpace() > item.getSpaceUsed()) {
            getSubItems().add(item);
        }
        return false;
    }

    public int getLeftSpace() {
        return MAXIMUM_SPACE - getSpaceUsed();
    }

    public int getSpaceUsed() {
        int space = 0;
        for(IModel model : getSubItems()){
            if(model.getType().equals(TYPE.ITEM)) {
                space += ((Item) model).getSpaceUsed();
            }
        }
        return space;
    }
}
