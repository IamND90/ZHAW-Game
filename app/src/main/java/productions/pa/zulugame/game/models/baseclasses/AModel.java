package productions.pa.zulugame.game.models.baseclasses;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.manager.ContextManager;
import productions.pa.zulugame.game.manager.PersonManager;
import productions.pa.zulugame.game.models.items.Backpack;
import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by IamND on 09.10.2015.
 * <p/>
 * This class is a parent for all the models in the game
 * It contains the id, type, (group), color and a list of possible subModels
 * It also implements the interface IModel which has all the functions and constants that a Model must have
 */
public abstract class AModel implements IModel {

    private static final String TAG = "AModel";
    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------
    int parentIndex = -1;
    final List<IModel> subItems = new ArrayList<>();
    /**
     * []
     *
     * @param mGroup the group of the model like: item, room etc..
     * @param mType the specific type of a model like: bottle, key etc..
     * @param color the color
     * @param id unique id from model which is generated in ContextManager
     * @param subItems the model can contain other models like: box can have a key in it
     */
    private final TYPE mType, mGroup;
    private final int id;
    private COLOR color;


    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================
    public AModel(TYPE... type) {
        this.id = ContextManager.get().getNextId();
        mType = type[0];
        if (type.length == 1) mGroup = mType;
        else mGroup = type[1];
        color = COLOR.getRandom();
        ContextManager.get().registerModel(this);
    }

    public AModel(COLOR color, TYPE... type) {
        this.id = ContextManager.get().getNextId();
        mType = type[0];
        if (type.length == 1) mGroup = mType;
        else mGroup = type[1];
        this.color = color;
        ContextManager.get().registerModel(this);
    }

    public int getParentIndex() {
        return parentIndex;
    }
    public void setParentIndex(int index){
        parentIndex = index;
    }

    //  ============================================================
    //  GETTERS / FINDERS
    //  ============================================================


    @Override
    public String getShortdescription() {
        return "[" + getColor() + " " + getName() + "]";
    }

    @Override
    public TYPE getType() {
        return mType;
    }

    @Override
    public TYPE getGroup() {
        return mGroup;
    }

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



    @Override
    public boolean addModel(IModel item) {
        ((AModel)item).setParentIndex(subItems.size());
        subItems.add(item);
        return true;
    }

    /**
     * @return Description of the model and its subModels
     */
    @Override
    public String getDescription() {
        if (subItems.isEmpty()) return "";
        String description = "This " + getName() + " contains " + getSubItems().size() + " things.";

        for (IModel item : subItems) {
            description += ("\n" + item.getName());
        }

        return description;
    }


    // =============================================================
    //  PUBLIC METHODS
    //  ============================================================

    /**
     * There methods are iterative, that means, it searches upp to all subModels and their subModels and so on
     * The method names should speak for them self..
     */

    public IModel findByTypeOrName(String... types) {
        for (String type : types) {
            if (getName().equalsIgnoreCase(type) || getType().name().equalsIgnoreCase(type)) return this;

            for (IModel item : subItems) {
                IModel foundModel = ((AModel) item).findByTypeOrName(type);
                if (foundModel != null) return foundModel;
            }
        }
        return null;
    }

    public List<IModel> findAllByTypeOrName(String... types) {
        List<IModel> list = new ArrayList<>();
        for (String type : types) {
            for (IModel item : subItems) {
                if (item.getName().equalsIgnoreCase(type) || item.getType().name().equalsIgnoreCase(type)) list.add(item);
            }
        }
        return list;
    }


    public IModel findByTypeAndColor(String name, String color) {

        if (( getName().equalsIgnoreCase(name) || getType().name().equalsIgnoreCase(name))
                        && getColor().name().equalsIgnoreCase(color)){
            return this;
        }
        for (IModel item : subItems) {
            IModel foundModel = ((AModel) item).findByTypeAndColor(name, color);
            if (foundModel != null){
                Log.i(getName(),"Found by name and color" + item.getColor() + " " + item.getName());
                return foundModel;
            }
        }

        return null;
    }


    public boolean findById(int id) {
        if (this.getId() == id) return true;

        for (int i = 0; i < subItems.size(); i++) {
            if (subItems.get(i).findById(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeItemById(int id) {
        if (this.getId() == id) return true;

        for (int i = 0; i < subItems.size(); i++) {
            if (subItems.get(i).removeItemById(id)) {
                subItems.remove(i);
                return true;
            }
        }
        return false;
    }

    //  ============================================================
    //  @Override METHODS
    //  ============================================================

    /**
     * This method is also iterative:
     * First the highest model in Context ( for example a Room) checks for its own exceptions of commands
     * if nothing found, its processes it to aren't and up to here, where the most general command for each item are handled
     * <p/>
     * If nothing found here, its processes from here to subModels @checkSubModels(Command)
     * ->Iteration and back to the top with the answer. In this case every model can handle it own exceptions of commands and their functionality
     *
     * @param command The command that has been generated by the parser
     * @return ist the answer which is displayed on the UI after it has been processed
     */

    @Override
    public Answer processCommand(Command command) {

        IModel item;
        Answer answer;

        // Search by index
        int numberTyped = command.getNumber();
        if(numberTyped != NOT_A_NUMBER){
            if(numberTyped == getParentIndex() && command.getColor().equals(getColor())){
                answer=  processThisFound(command);
                if (answer != null) return answer;
            }

            if(numberTyped < getSubItems().size() && numberTyped >= 0 && getSubItems().size() >=0){
                item = getSubItems().get(numberTyped);
                answer  = item.processCommand(command);
                if(answer != null)return answer;
            }

        }

        //  Search by name
        if (command.hasAttributeOf(getType().name(), getName())) {
            answer=  processThisFound(command);
            if (answer != null) return answer;
        }


        return checkSubModels(command);
    }

    private Answer processThisFound(Command command) {
        Log.i(TAG,"Command found: " + getName() + " " + getColor().name() + " " + getType().name());
        Answer answer = null;
        if (command.hasActionOf(HitWord.INFO, HitWord.SHOW)) {
            ContextManager.get().setCurrentContext(this);
            return new Answer(this.getDescription(), Answer.DECORATION.DESCRIPTION);
        }
        if (command.hasActionOf(HitWord.DROP,HitWord.REMOVE)) {
            return dropItem(this);
        }
        if (command.hasActionOf(HitWord.TAKE,HitWord.STORE)) {
            return PersonManager.get().getBackPack().addItem(this);
        }

        return null;
    }

    private Answer dropItem(IModel model) {

        Backpack backpack = PersonManager.get().getPerson().getBackpack();

        if(backpack.findById(model.getId())){
            return new Answer("You dropped " + model.getShortdescription(), Answer.DECORATION.SIMPLE);
        }
        else return new Answer("You dont have "+ model.getShortdescription() + " in your backpack", Answer.DECORATION.FAIL);

    }


    private Answer checkSubModels(Command command) {
        Answer answer = null;
        if (subItems == null) {
            return null;
        }

        IModel item = null;
        if(command.hasPointer())
            item = findByTypeAndColor(command.getAttribute(), command.getPointer());
        else {
            List<IModel> items = findAllByTypeOrName(command.getAttribute());
            if(items.size() > 1){
                return new Answer("Multiple items found, please specify color.", Answer.DECORATION.FAIL);
            }else if(items.size() != 0) {
                item = items.get(0);
            }
        }
        if (item != null) {
            if(item.getId() != getId()) {
                answer = item.processCommand(command);
                if (answer != null) return answer;
            }else
                answer =  processThisFound(command);
            if (answer != null) return answer;
        }

        return answer;
    }


}
