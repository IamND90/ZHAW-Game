package productions.pa.zulugame.game.models.items;

import java.util.List;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.AModel;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.story.ModelManager;
import productions.pa.zulugame.game.story.PersonManager;

/**
 * Created by Andrey on 15.10.2015.
 */
public class Box extends AModel {

    final static String TAG = "Box";

    private static int count_boxes = 0;

    public Box() {
        super(count_boxes++, TYPE.BOX);
    }

    public Box(List<Item> containingItems) {
        super(count_boxes++, TYPE.BOX);
        getSubItems().addAll(containingItems);
    }

    public Box addItem(Item item) {
        getSubItems().add(item);
        return this;
    }


    @Override
    public String getName() {
        return "Box";
    }

    @Override
    public String getDescription() {
        String description = "\nThis box contains [" + getSubItems().size() + (getSubItems().size() >1 ? "] items" :"] item");

        for (IModel item : getSubItems()) {
            description += ("\n" + item.getColor() + " " + item.getName());
        }

        return description;
    }

    @Override
    public Answer processCommand(Command command) {

        /*
        //TODO chack utinls
        if (Utils.findInList(FLAGS_ACTIONS, command.getAction().getString()) != null) {
            if (command.getAttribute() != null) {
                String attribute = command.getAttribute().getString();
                Item item = Utils.findItemByName(getSubItems(), attribute);
                if (item == null) return new Answer(
                        getName() + " does not contain " + attribute, Answer.TYPE.ITEM_NOT_FOUND);

                if (PersonManager.get().getPerson().getBackpack().addItem(item)) {
                    getSubItems().remove(item);
                    return new Answer("You added " + item.getName() + " to your backpack.", Answer.TYPE.SUCCESS);
                }
            }
            */
        if (command.getAttribute() != null) {
            String attribute = command.getAttribute().getString();
            String action = command.getAction() == null ? null: command.getAction().getString();
            if (attribute.equalsIgnoreCase(HitWord.BOX)) {
                if (HitWord.OPEN.equalsIgnoreCase(action)) {
                    if (command.getPointer().getString().equalsIgnoreCase(this.getColor().name())) {
                        ModelManager.get().setCurrentContext(this);
                        return new Answer("You opended thr box:\n" + getDescription(), Answer.TYPE.SUCCESS, Answer.TYPE.SIMPLE_OUTPUT);
                    }
                }
            }


            if(action.equalsIgnoreCase(HitWord.TAKE) || action.equalsIgnoreCase(HitWord.GET)){
                // TAKE ALL ITEMS
                if(command.getPointer().getString().equalsIgnoreCase(HitWord.ALL)){
                    for(IModel item: getSubItems()){
                        if(!PersonManager.get().getPerson().getBackpack().addItem((Item)item)){
                            //You havent took all the items
                            return new Answer("You took[" + getSubItems().size() + "] items", Answer.TYPE.FAIL, Answer.TYPE.SIMPLE_OUTPUT);
                        }
                    }
                    return new Answer("You took[" + getSubItems().size() + "] items", Answer.TYPE.SUCCESS, Answer.TYPE.SIMPLE_OUTPUT);
                }

                // FIND ITEM BY NAME AND COLOR
                Item item = findItemByNameAndColor(attribute, command.getPointer().getString());
                if (item != null) {
                    if (PersonManager.get().getPerson().getBackpack().addItem(item)) {
                        return new Answer("You took the item [" + item.getName() + "]", Answer.TYPE.SUCCESS, Answer.TYPE.SIMPLE_OUTPUT);
                    } else {
                        return new Answer("Your backpack is full", Answer.TYPE.FAIL, Answer.TYPE.SIMPLE_OUTPUT);
                    }
                }
            }
        }


        return new Answer("Item not found", Answer.TYPE.FAIL, Answer.TYPE.ITEM_NOT_FOUND);

    }


}
