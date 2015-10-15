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

    Attribute attributes[] ;

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

    public abstract Answer interactWithItem(Item item);

    @Override
    public Attribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(Attribute... attributes) {
        this.attributes = attributes;
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
        for(IModel item : subItems)if(item.getName().equals(name))return (Item) item;
        return null;
    }

    public void removeItemByName(String name){
        for(IModel item : subItems)if(item.getName().equals(name)){
            subItems.remove(item);
            return;
        }
    }

    public boolean hasAttribute(Attribute.STATUS status){
        if(attributes== null)return false;
        for(Attribute attribute : attributes)if(attribute.getStatus().equals(status))return true;
        return false;
    }
    public boolean hasAttribute(Attribute.ACTING status){if(attributes== null)return false;
        for(Attribute attribute : attributes)if(attribute.getAction().equals(status))return true;
        return false;
    }
    public boolean hasAttribute(Attribute.POINTING status){
        if(attributes== null)return false;
        for(Attribute attribute : attributes)if(attribute.getPointer().equals(status))return true;
        return false;
    }
    public boolean hasAllAttributes(Attribute attribute){
        if(attributes== null)return false;
            if(hasAttribute(attribute.getAction())
               && hasAttribute(attribute.getStatus())
                    && hasAttribute(attribute.getPointer()))return true;
        return false;
    }
    public boolean hasOneAttribute(Attribute attribute){
        if(attributes== null)return false;
            if(hasAttribute(attribute.getAction())
                    || hasAttribute(attribute.getStatus())
                    || hasAttribute(attribute.getPointer()))return true;
        return false;
    }
    protected boolean hasNoAttributes(){return attributes == null ? true : false;}
}
