package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.story.ModelManager;

/**
 * Created by IamND on 09.10.2015.
 */
public class Backpack extends Item {

    //TODO add space



    public Backpack() {
        super(ModelManager.ID_BACKPACK, null);
    }

    @Override
    public String getName() {
        return "Backpack";
    }

    @Override
    public String getDescription() {
        String descr =  "Here you can store your items.\nYou have " + getUsedByItems() + "/" + getSpaceUsed() + " space used.";

        int index = 1;
        for(IModel item : getSubItems()){
            descr += ("\n\t" + index++ + ".\t[" +item.getColor().name() + "\t" + item.getName() + "](" + item.getDescription() + ")");
        }

        return descr;
    }

    @Override
    public Answer processCommand(Command command) {

        if (command.getAction().equals(HitWord.GET) || command.getAction().equals(HitWord.USE)) {
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


    public boolean addItem(Item item) {

        if(getLeftSpace() > item.getSpaceUsed()) {
            getSubItems().add(item);
            return true;
        }
        return false;
    }

    public int getLeftSpace() {
        return getSpaceUsed() - getUsedByItems();
    }

    public int getSpaceUsed() {
        return 50;
    }

    private int getUsedByItems(){
        int space = 0;
        for(IModel model : getSubItems()){
            space += ((Item) model).getSpaceUsed();
        }
        return space;
    }

}
