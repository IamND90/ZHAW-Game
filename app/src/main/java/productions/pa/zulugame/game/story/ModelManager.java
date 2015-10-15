package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.models.AModel;
import productions.pa.zulugame.game.models.IModel;

/**
 * Created by IamND on 09.10.2015.
 */
public class ModelManager {

    static ModelManager mThis;


    IModel currentContext;

    public static final int ID_BACKPACK = 100001;
    public static final int ID_MYPERSON = 100002;

    public static ModelManager get(){
        if(mThis == null){
            synchronized (ModelManager.class){
                mThis = new ModelManager();
            }

        }
        return mThis;
    }

    public IModel getCurrentcontext(){
        return currentContext;
    }

    public void setCurrentContext(IModel context){
        currentContext = context;
    }
}
