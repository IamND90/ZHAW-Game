package productions.pa.zulugame.game.models;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.items.Item;
import productions.pa.zulugame.game.parser.Attribute;
import productions.pa.zulugame.game.story.ModelManager;

/**
 * Created by IamND on 09.10.2015.
 */
public abstract class AModel implements IModel {

    private final TYPE mType;
    private final int id;
    private COLOR color;

    final List<IModel> subItems = new ArrayList<>();


    public AModel(int id, TYPE type){
        this.id = id;
        mType =type;
        color = COLOR.getRandom();
    }

    @Override
    public TYPE getType(){return mType;}

    @Override
    public int getId() {
        return id;
    }

    @Override
    public List<IModel> getSubItems() {
        return subItems;
    }

    @Override
    public COLOR getColor() {
        return color;
    }

    @Override
    public void setColor(COLOR color) {
        this.color = color;
    }

    protected Item getItem(int id, TYPE type, String name){
        IModel found = null;
        if(id>0) {
            for (IModel subModel : subItems) {
                if (subModel.getId() == id) {
                    found = subModel;
                    break;
                }
            }
        }

        if(found != null && type != null) {
            for (IModel subModel : subItems) {
                if (subModel.getType().equals(type)) {
                    found = subModel;
                    break;
                }
            }
        }

        if(found == null && !TextUtils.isEmpty(name)){
            for (IModel subModel : subItems) {
                if (subModel.getName().equals(name)) {
                    found = subModel;
                    break;
                }
            }
        }

        ModelManager.get().setCurrentContext(found);
        return (Item) found;
    }


    @Override
    public String getDescription() {
        if(getSubItems().isEmpty())return "";
        String description = "This "+ getName() +" contains " +getSubItems().size() + " things.";

        for(IModel item :getSubItems()){
            description += ("\n" + item.getName() );
        }

        return description;
    }



    protected Answer checkSubModels(Command command){
        Answer answer = null;
        for(IModel subModel: subItems){
            answer = subModel.processCommand(command);
            if(answer != null && !TextUtils.isEmpty(answer.getMessage())) return answer;
        }

        return  new Answer(MessageFactory.MESSAGE_CANNOT_INTERACT, Answer.TYPE.FAIL);
    }


    protected boolean hasItem(Item item){

        for(IModel found :subItems){
            if(found.getName().equalsIgnoreCase(item.getName()))return true;
        }
        return false;
    }
    public Item findItemByName(String name){
        for(IModel item : subItems)if(item.getName().equalsIgnoreCase(name))return (Item) item;
        return null;
    }
    public IModel findByType(TYPE type){
        for(IModel item : subItems)if(item.getType().equals(type))return item;
        return null;
    }
    public Item findItemByNameAndColor(String name,String color){
        for(IModel item : subItems)if(item.getName().equalsIgnoreCase(name) &&
                item.getColor().name().equalsIgnoreCase(color))return (Item) item;
        return null;
    }

    public void removeItemByName(String name){
        for(IModel item : subItems)if(item.getName().equalsIgnoreCase(name)){
            subItems.remove(item);
            return;
        }
    }


}
