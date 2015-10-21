package productions.pa.zulugame.game.models.baseclasses;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.models.places.Door;

/**
 * Created by Andrey on 08.10.2015.
 * <p/>
 * This class is a parent for Room and Door:
 * Both are technically seen placed in this game, a door can have only two linked places, which are Rooms
 * Rooms can have more and those are all Doors leading to other rooms
 * The method names should be self speaking
 */
public abstract class APlace extends AModel {

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------

    protected List<IModel> linkedPlaces = new ArrayList<>();

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public APlace(TYPE type) {
        super(type, TYPE.PLACE);
    }

    // =============================================================
    //  PUBLIC METHODS
    //  ============================================================

    public List<IModel> getLinkedPlaces() {
        return linkedPlaces;
    }

    //  ============================================================
    //  PRIVATE / PROTECTED METHODS
    //  ============================================================

    protected ARoom findRoomById(int id) {
        if (getType().equals(TYPE.ROOM)) {
            for (IModel model : linkedPlaces) {
                Door door = (Door) model;
                for (ARoom room : door.getNextPlaces()) {
                    if (room.getId() == id) return room;
                }
            }
        }
        return null;
    }

    protected IModel findRoomByName(String name) {
        for (IModel model : linkedPlaces) {
            Door door = (Door) model;
            if(door.getNextPlace().getName().equalsIgnoreCase(name))return door.getNextPlace();
        }
        return null;
    }



}
