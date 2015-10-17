package productions.pa.zulugame.game.models.places;

import productions.pa.zulugame.game.models.AModel;

/**
 * Created by Andrey on 08.10.2015.
 */
public abstract class APlace extends AModel {

    //protected APlace parentPlace = null;
    //List<Door> linkedDoors = new ArrayList<>();

    public APlace(int id, TYPE type){
        super(id,type);
    }



}
