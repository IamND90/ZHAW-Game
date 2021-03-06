package productions.pa.zulugame.game.manager;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.models.baseclasses.IModel;
import productions.pa.zulugame.game.models.items.Riddle;

/**
 * Created by IamND on 09.10.2015.
 * This Manager is needed to know what context the layer currently is, like: looking in a box or searching his backpack etc..
 *
 */
public class ContextManager {

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------
    /**
     * @param mThis keeping the context on itself for not beeing deleted by java and beeing accessible from everywhere*/
    static ContextManager mThis;

    /**
     * @param id_counter keeps counting for model ids*/
    int id_counter =0;

    /**
     * @param currentContext the current model, that the user is interacting with*/
    IModel currentContext ;

    List<IModel> allExistingModels = new ArrayList<>();
    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    private ContextManager(){
        id_counter =0;
        currentContext = null;
    }

    //  ============================================================
    //  GETTERS / FINDERS
    //  ============================================================

    public static ContextManager get(){
        if(mThis == null){
            synchronized (ContextManager.class){
                mThis = new ContextManager();
            }

        }
        return mThis;
    }

    public IModel getCurrentcontext(){
        if(currentContext == null)currentContext = PersonManager.get().getPerson();
        return currentContext ;
    }

    public void setCurrentContext(IModel context){

        if(context == null){
            currentContext = PersonManager.get().getPerson();
            return;
        }
        if(IModel.TYPE.RIDDLE.equals(context.getType())){
            RiddleManager.get().setCurrentRiddle((Riddle) context);
        }
        currentContext = context;
    }

    public void registerModel(IModel model){
        allExistingModels.add(model);
    }

    public IModel findById(int id){

        IModel found = fastFind(id);
        if(found != null) return found;

        for(IModel model : allExistingModels){
            if(model.getId() == id)return model;
        }

        return null;
    }

    private IModel fastFind(int id) {
        if(allExistingModels.get(id).getId() == id)return allExistingModels.get(id);
        return null;
    }


    /**
     * @return returns the next unique id for a model*/
    public int getNextId(){
        return id_counter++;
    }
}
