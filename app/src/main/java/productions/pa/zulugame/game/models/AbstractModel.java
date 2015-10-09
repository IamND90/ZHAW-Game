package productions.pa.zulugame.game.models;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.commands.Command;

/**
 * Created by IamND on 09.10.2015.
 */
public abstract class AbstractModel implements IModel {

    private TYPE mType = TYPE.UNKNOW;
    private int id;

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

    public enum TYPE{
        UNKNOW,

        ITEM,
        PERSON,
        ROOM
    }

    protected String checkSubModels(Command command){
        String answer = null;
        for(AbstractModel subModel: subModels){
            answer = subModel.executeCommand(command);
            if(!TextUtils.isEmpty(answer)) return answer;
        }

        return "";
    }
}
