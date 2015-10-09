package productions.pa.zulugame.game.models;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.items.Item;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.story.ModelManager;

/**
 * Created by IamND on 09.10.2015.
 */
public class Backpack extends Item {

    public Backpack() {
        super(ModelManager.ID_BACKPACK);
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
    public Answer executeCommand(Command command) {

        if (command.getAction().equals(HitWordFactory.GET) || command.getAction().equals(HitWordFactory.USE)) {
            if(isEmpty()){return new Answer(MessageFactory.MESSAGE_BACKPACK_IS_EMPTY).setContextId(getId());}
            if(command.getAttribute() != null){
                Item foundItem = getItem(-1,null,command.getAttribute().getString());
                if(foundItem == null)return new Answer(MessageFactory.MESSAGE_BACKPACK_DOES_NOT_HAS_ITEMS).setContextId(getId());

                ModelManager.get().setCurrentContext(this);
                //TODO what todo with the item?
            }
        }

        return null;
    }

    public boolean isEmpty() {
        return !(interactItem == null && interactItem.length == 0);
    }

    @Override
    public Answer interactWithItem(Item item) {
        return null;
    }
}
