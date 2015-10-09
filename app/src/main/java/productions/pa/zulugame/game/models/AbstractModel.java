package productions.pa.zulugame.game.models;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;

/**
 * Created by IamND on 09.10.2015.
 */
public abstract class AbstractModel implements IModel {

    private final TYPE mType;
    private final int id;

    final List<AbstractModel> subModels = new ArrayList<>();

    public AbstractModel(int id, TYPE type){
        this.id = id;
        mType =type;
    }
    public TYPE getType(){return mType;}

    public int getId() {
        return id;
    }

    public TYPE getmType() {
        return mType;
    }

    public List<AbstractModel> getSubModels() {
        return subModels;
    }



    protected AbstractModel getItem(int id, TYPE type, String name){
        AbstractModel found = null;
        if(id>0) {
            for (AbstractModel subModel : subModels) {
                if (subModel.getId() == id) {
                    found = subModel;
                    break;
                }
            }
        }

        if(found != null && type != null) {
            for (AbstractModel subModel : subModels) {
                if (subModel.getType().equals(type)) {
                    found = subModel;
                    break;
                }
            }
        }

        if(found == null && !TextUtils.isEmpty(name)){
            for (AbstractModel subModel : subModels) {
                if (subModel.getName().equals(name)) {
                    found = subModel;
                    break;
                }
            }
        }

        return found;
    }

    protected Answer checkSubModels(Command command){
        Answer answer = null;
        for(AbstractModel subModel: subModels){
            answer = subModel.executeCommand(command);
            if(!TextUtils.isEmpty(answer.getMessage())) return answer;
        }

        return answer;
    }
}
