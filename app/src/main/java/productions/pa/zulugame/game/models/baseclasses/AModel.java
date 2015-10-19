package productions.pa.zulugame.game.models.baseclasses;

import android.text.TextUtils;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.manager.ContextManager;
import productions.pa.zulugame.game.models.IModel;
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
    }

    public AModel(COLOR color, TYPE... type) {
        this.id = ContextManager.get().getNextId();
        mType = type[0];
        if (type.length == 1) mGroup = mType;
        else mGroup = type[1];
        this.color = color;
    }


    //  ============================================================
    //  GETTERS / FINDERS
    //  ============================================================
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

    protected AItem getItem(int id, TYPE type, String name) {
        IModel found = null;
        if (id > 0) {
            for (IModel subModel : subItems) {
                if (subModel.getId() == id) {
                    found = subModel;
                    break;
                }
            }
        }

        if (found != null && type != null) {
            for (IModel subModel : subItems) {
                if (subModel.getType().equals(type)) {
                    found = subModel;
                    break;
                }
            }
        }

        if (found == null && !TextUtils.isEmpty(name)) {
            for (IModel subModel : subItems) {
                if (subModel.getName().equals(name)) {
                    found = subModel;
                    break;
                }
            }
        }

        ContextManager.get().setCurrentContext(found);
        return (AItem) found;
    }

    /**
     * @return Description of the model and its subModels
     */
    @Override
    public String getDescription() {
        if (getSubItems().isEmpty()) return "";
        String description = "This " + getName() + " contains " + getSubItems().size() + " things.";

        for (IModel item : getSubItems()) {
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

    public IModel findByTypes(String... types) {
        for (String type : types) {
            if (getType().name().equalsIgnoreCase(type)) return this;

            for (IModel item : subItems) {
                IModel foundModel = ((AModel) item).findByTypes(type);
                if (foundModel != null) return foundModel;
            }
        }
        return null;
    }

    public IModel findByGroup(TYPE group) {

        for (IModel item : subItems) if (item.getGroup().equals(group)) return item;
        return null;
    }

    public IModel findByNameAndColor(String name, String color) {

        if (getType().name().equalsIgnoreCase(name) && getColor().name().equalsIgnoreCase(color))
            return this;

        for (IModel item : subItems) {
            IModel foundModel = ((AModel) item).findByNameAndColor(name, color);
            if (foundModel != null){
                Log.i(getName(),"Found by name and color" + item.getColor() + " " + item.getName());
                return foundModel;
            }
        }

        return null;
    }

    public boolean removeItemByName(String name) {
        if (getName().equalsIgnoreCase(name)) return true;

        for (int i = 0; i < subItems.size(); i++) {
            if (((AModel) subItems.get(i)).removeItemByName(name)) {
                subItems.remove(i);
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


        //  Check whether the command has a pointer or not
        //  iIf pointing at not hits item, process further
        if (!TextUtils.isEmpty(command.getPointer())) {
            if (!command.getColor().equalsIgnoreCase(getColor().name())) {
                return checkSubModels(command);
            }
        }

        if (command.hasAttributeOf(getType().name(), getName())) {
            if (command.hasActionOf(HitWord.INFO, HitWord.SHOW)) {
                IModel item;
                Answer answer;

                if (ContextManager.get().getCurrentcontext().getId() == this.getId() && TextUtils.isEmpty(command.getAttribute())) {
                    return new Answer(getDescription(), Answer.DECORATION.DESCRIPTION);
                } else {
                    if (getSubItems().size() > 1)
                        item = findByNameAndColor(command.getAttribute(), command.getPointer());
                    else item = findByTypes(command.getAttribute());
                    if (item != null) {
                        ContextManager.get().setCurrentContext(this);
                        return new Answer(item.getDescription(), Answer.DECORATION.DESCRIPTION);
                    }
                    item = ContextManager.get().getCurrentcontext();
                    if (item != null && item.getId() != getId()) {
                        ContextManager.get().setCurrentContext(item);
                        return new Answer(item.getDescription(), Answer.DECORATION.DESCRIPTION);
                    }

                }
            }
        }
        return checkSubModels(command);
    }

    private Answer checkSubModels(Command command) {
        Answer answer = null;
        if (subItems == null) {
            return new Answer("No subitems found", Answer.DECORATION.ERROR);
        }
        //  Filter the models with the possible attribute
        List<IModel> validModels = new ArrayList<>();
        for (IModel subModel : subItems) {
            String searchName = subModel.getType().name();

            if (command.hasAttributeOf(searchName)) {
                validModels.add(subModel);
            }
        }
        //  Check if there is only one model and no pointer given, so no pointer needed
        if (validModels.size() == 1 && TextUtils.isEmpty(command.getPointer())) {
            command.setPointer(validModels.get(0).getColor().name());
            return validModels.get(0).processCommand(command);
        }

        for (IModel subModel : validModels) {
            Log.i("AModel", "Found submodel by attribute or name");
            answer = subModel.processCommand(command);
            if (answer != null) return answer;
        }


        return answer;
    }


}
