package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.models.AModel;

/**
 * Created by IamND on 09.10.2015.
 */
public class ModelManager {

    static ModelManager mThis;


    AModel currentContext;

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

    public AModel getCurrentcontext(){
        return currentContext;
    }

    public void setCurrentContext(AModel context){
        currentContext = context;
    }
}
