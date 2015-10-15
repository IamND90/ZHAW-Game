package productions.pa.zulugame.game.models.items;

import java.util.List;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.AModel;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.story.PersonManager;

/**
 * Created by Andrey on 15.10.2015.
 */
public class Box extends AModel{

    final static String FLAGS_ACTIONS[] ={HitWordFactory.TAKE,HitWordFactory.GET};
    private static int count_boxes = 0;

    public Box() {
        super(count_boxes++, TYPE.BOX);
    }

    @Override
    public Answer interactWithItem(Item item) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Answer executeCommand(Command command) {

        //TODO chack utinls
        // if(Utils.hasString(FLAGS_ACTIONS,command.getAction()){
        if(true){
            if(command.getAttribute() != null) {
                String attribute = command.getAttribute().getString();
                Item item = findByNameItem(getSubItems(), attribute);
                if (item == null) return new Answer(
                        getName() + " does not contain " + attribute, Answer.TYPE.ITEM_NOT_FOUND);

                if(PersonManager.get().getPerson().getBackpack().addItem(item)){

                }
            }
            return new Answer("Item not found", Answer.TYPE.ITEM_NOT_FOUND);
        }

        return null;
    }

    public static Item findByNameItem(List<Item> list, String input){
        for(Item item:list){
            if(item.getName().equals(input))return item;
        }
        return null;
    }
}
