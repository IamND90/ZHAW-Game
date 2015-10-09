package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.models.AbstractModel;

/**
 * Created by IamND on 09.10.2015.
 */
public class ModelManager {

    static ModelManager mThis;


    AbstractModel currentContext;

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

    public AbstractModel getCurrentcontext(){
        return currentContext;
    }

    public void setCurrentContext(AbstractModel context){
        currentContext = context;
    }
}
