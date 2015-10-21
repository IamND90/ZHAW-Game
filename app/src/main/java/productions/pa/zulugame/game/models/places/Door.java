package productions.pa.zulugame.game.models.places;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.manager.ContextManager;
import productions.pa.zulugame.game.manager.PersonManager;
import productions.pa.zulugame.game.manager.RoomManager;
import productions.pa.zulugame.game.models.baseclasses.IModel;
import productions.pa.zulugame.game.models.baseclasses.APlace;
import productions.pa.zulugame.game.models.baseclasses.ARoom;
import productions.pa.zulugame.game.models.items.Key;
import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by IamND on 09.10.2015.
 * <p/>
 * A Door link two rooms, can be locked by a color TODO or a keyid
 * for mor information read @APlace
 */
public class Door extends APlace {

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------

    /**
     * @param keyColorNeededToOpen the color of the key that can open this door, is always the color of the door actually. if null, not closed
     */
    private String keyColorNeededToOpen = null;
    private int keyIdNeededToOpen =-1;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public Door(ARoom room1, ARoom room2) {
        super(TYPE.DOOR);
        linkedPlaces.add(room1);
        linkedPlaces.add(room2);
    }

    public Door(ARoom room1, ARoom room2, Key closeKey) {
        super(TYPE.DOOR);
        linkedPlaces.add(room1);
        linkedPlaces.add(room2);
        keyIdNeededToOpen = closeKey.getId();
        setColor(closeKey.getColor());
    }

    public Door(ARoom room1, ARoom room2, COLOR closeColor) {
        super(TYPE.DOOR);
        linkedPlaces.add(room1);
        linkedPlaces.add(room2);
        keyColorNeededToOpen = closeColor.name();
        setColor(closeColor);
    }
    public Door(ARoom room1, ARoom room2, String doorColor) {
        super(TYPE.DOOR);
        linkedPlaces.add(room1);
        linkedPlaces.add(room2);
        setColor(COLOR.fromString(doorColor));
    }

    //  ============================================================
    //  @Override METHODS
    //  ============================================================

    @Override
    public String getName() {
        return isClosed() ? "Locked door" : "Door";
    }

    @Override
    public String getDescription() {
        return "This Door links room [" + linkedPlaces.get(0).getName() + "] and [" + linkedPlaces.get(1).getName() + "]";
    }


    // =============================================================
    //  PUBLIC METHODS
    //  ============================================================

    /**
     * Chekcs if the door is closed, if you have the right key, it will open it
     *
     * @return whether the door opened or not
     */
    public boolean openDoor() {

        ContextManager.get().setCurrentContext(this);
        if (isClosed()) {
            IModel key = PersonManager.get().getPerson().getBackpack().findByTypeAndColor(HitWord.KEY, getColor().name());
            if (key != null) {
                if(keyColorNeededToOpen != null) {
                    if (key.getColor().name().equalsIgnoreCase(keyColorNeededToOpen)) {
                        //Remove the key from backpack because used
                        PersonManager.get().getPerson().getBackpack().removeItemById(key.getId());
                        keyColorNeededToOpen = null;
                        return true;
                    }
                }
                if(keyIdNeededToOpen >= 0){
                    //TODO
                }
                return false;
            }
            return false;
        }
        return true;
    }

    /**
     * @return the place of the other side of this Door you are standing
     */
    public ARoom getNextPlace() {
        //TODO
        return (ARoom) (RoomManager.get().getCurrentPlace().getId() == linkedPlaces.get(0).getId() ? linkedPlaces.get(1) : linkedPlaces.get(0));
    }

    /**
     * @return return a list of the linked places as casted Rooms
     */
    public List<ARoom> getNextPlaces() {
        List<ARoom> list = new ArrayList<>();
        list.add((ARoom) linkedPlaces.get(0));
        list.add((ARoom) linkedPlaces.get(1));
        return list;
    }

    public boolean isClosed() {
        return (keyColorNeededToOpen == null && keyIdNeededToOpen < 0) ? false : true;
    }

    /**
     * validates if it is a door of the two rooms given
     */

    public boolean findByRooms(ARoom room1, ARoom room2) {

        if ((room1.getId() == linkedPlaces.get(0).getId() && room2.getId() == linkedPlaces.get(1).getId()) ||
                room1.getId() == linkedPlaces.get(1).getId() && room2.getId() == linkedPlaces.get(0).getId())
            return true;

        return false;
    }
}
