package productions.pa.zulugame.game.models.items;

import java.util.List;

import productions.pa.zulugame.game.Utils;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.AModel;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.story.PersonManager;

/**
 * Created by Andrey on 15.10.2015.
 */
public class Box extends AModel{

    final static String FLAGS_ACTIONS[] ={HitWordFactory.TAKE,HitWordFactory.GET};
    private static int count_boxes = 0;


    public Box(List<Item> containingItems) {
        super(count_boxes++, TYPE.BOX);
    }

    @Override
    public Answer interactWithItem(Item item) {
        return null;
    }

    @Override
    public String getName() {
        return "Box";
    }

    @Override
    public String getDescription() {
        String description = "This box contains " +getSubItems().size() + "items";

        int riddles =0;

        for(IModel item :getSubItems()){
            if(item.getType().equals(TYPE.RIDDLE))riddles++;
            if(item.getType().equals(TYPE.KEY)) description+= ("\nKey[" + item.getId() +"]");
        }

        description += (riddles== 0 ? "" : ("\n" + riddles + " riddles"));
        return description;
    }

    @Override
    public Answer processCommand(Command command) {

        //TODO chack utinls
         if(Utils.findInList(FLAGS_ACTIONS,command.getAction().getString()) != null){
            if(command.getAttribute() != null) {
                String attribute = command.getAttribute().getString();
                Item item = Utils.findItemByName(getSubItems(), attribute);
                if (item == null) return new Answer(
                        getName() + " does not contain " + attribute, Answer.TYPE.ITEM_NOT_FOUND);

                if(PersonManager.get().getPerson().getBackpack().addItem(item)){
                    getSubItems().remove(item);
                    return new Answer("You added " + item.getName() + " to your backpack.", Answer.TYPE.SUCCESS);
                }
            }
            return new Answer("Item not found", Answer.TYPE.ITEM_NOT_FOUND);
        }

        return null;
    }


}
