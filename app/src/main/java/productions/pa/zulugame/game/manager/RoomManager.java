package productions.pa.zulugame.game.manager;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.Statistic;
import productions.pa.zulugame.game.models.Box;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.models.baseclasses.ARoom;
import productions.pa.zulugame.game.models.items.Bottle;
import productions.pa.zulugame.game.models.items.Key;
import productions.pa.zulugame.game.models.places.CustomRoom;
import productions.pa.zulugame.game.models.places.Door;
import productions.pa.zulugame.game.models.places.RoomFinish;
import productions.pa.zulugame.game.models.places.RoomStart;
import productions.pa.zulugame.game.models.quests.RiddleFactory;

/**
 * Created by Andrey on 09.10.2015.
 * <p/>
 * This Class keeps tracking what room you are in
 * Its also, due i don't have the time to make this included the 30 hours,
 * a level creator, where i link all my rooms and put boxes in etc.. MANUALLY
 */
public class RoomManager {

    /**
     * @param mThis keeping the context on itself for not beeing deleted by java and beeing accessible from everywhere
     */
    static RoomManager mThis;

    //  ------------------------------------------------------------
    //  STATIC FINAL FIELDS
    //  ------------------------------------------------------------

    //  My rooms, boxed, keys
    final ARoom ROOM1;
    final ARoom ROOM2;
    final ARoom ROOM3;
    final ARoom ROOM4;
    final ARoom ROOM5;
    final ARoom ROOM6;
    final ARoom ROOM7;
    final ARoom ROOM8;
    final ARoom START;
    final ARoom FINISH;

    final Key KEY_BLUE;
    final Key KEY_PURPLE;
    final Key KEY_RED;
    final Key KEY_GREEN;
    final Key KEY_YELLOW;


    final Box BOX0;
    final Box BOX1;
    final Box BOX2;
    final Box BOX3;
    final Box BOX4;
    final Box BOX5;
    final Box BOX6;
    final Box BOX7;
    final Box BOX8;

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------

    /**
     * @param roomsWalkLine the line of rooms you walked, if you enter a new room, its added, if you return, yo
     * @param currentRoom the index of the current room in the list
     */
    List<ARoom> roomsWalkLine = new ArrayList<>();

    int currentRoom = 0;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    private RoomManager() {

        KEY_BLUE = Key.generateKey(IModel.COLOR.BLUE);
        KEY_PURPLE = Key.generateKey(IModel.COLOR.ORANGE);
        KEY_RED = Key.generateKey(IModel.COLOR.RED);
        KEY_YELLOW = Key.generateKey(IModel.COLOR.YELLOW);
        KEY_GREEN = Key.generateKey(IModel.COLOR.GREEN);


        BOX0 = new Box()
                .addItem(KEY_RED)
                .addItem(new Bottle())
                .addItem(RiddleFactory.getTelephoneNumbers())
                .addItem(RiddleFactory.getTime())
                .addItem(Key.generateKey(IModel.COLOR.getRandomExclude(KEY_RED.getColor())));
        BOX1 = new Box()
                .addItem(KEY_YELLOW)
                .addItem(new Bottle())
                .addItem(RiddleFactory.getSand());
        BOX2 = new Box()
                .addItem(KEY_PURPLE)
                .addItem(KEY_BLUE)
                .addItem(RiddleFactory.getTriangle())
                .addItem(new Bottle());
        BOX3 = new Box()
                .addItem(new Bottle());
        BOX4 = new Box()
                .addItem(new Bottle())
                .addItem(Key.generateKey(IModel.COLOR.getRandom()))
                .addItem(RiddleFactory.getNightmare());
        BOX5 = new Box()
                .addItem(RiddleFactory.getMailBox());
        BOX6 = new Box()
                .addItem(RiddleFactory.getSkydive());
        BOX7 = new Box()
                .addItem(new Bottle())
                .addItem(Key.generateKey(IModel.COLOR.getRandom()));
        BOX8 = new Box()
                .addItem(RiddleFactory.getTelephoneNumbers())
                .addItem(KEY_GREEN)
                .addItem(Key.generateKey(IModel.COLOR.getRandomExclude(KEY_GREEN.getColor())))
                .addItem(new Bottle());

        START = new RoomStart()
                .addBoxItem(BOX0);
        ROOM1 = new CustomRoom("Nibula")
                .addBoxItem(BOX1);
        ROOM2 = new CustomRoom("Jutenheim")
                .addBoxItem(BOX2);
        ROOM3 = new CustomRoom("Canyon")
                .addBoxItem(BOX3);
        ROOM4 = new CustomRoom("Disneyland")
                .addBoxItem(BOX4)
                .addBoxItem(BOX5);
        ROOM5 = new CustomRoom("Rainbowworld")
                .addBoxItem(BOX6);
        ROOM6 = new CustomRoom("Altron")
                .addBoxItem(BOX7);
        ROOM7 = new CustomRoom("Neptun");
        ROOM8 = new CustomRoom("Uranus")
                .addBoxItem(BOX8);
        FINISH = new RoomFinish();

        createTree();
        roomsWalkLine.add(START);
    }

    public static void reset() {
        mThis = null;
    }

    public static RoomManager get() {
        if (mThis == null) {
            synchronized (RoomManager.class) {
                mThis = new RoomManager();
            }
        }
        return mThis;
    }

    private void createTree() {

        START
                .addBranch(ROOM2, KEY_RED)
                .addBranch(ROOM3, KEY_BLUE)
                .addBranch(ROOM1);
        ROOM3
                .addBranch(ROOM4, KEY_YELLOW)
                .addBranch(ROOM8);
        ROOM4
                .addBranch(ROOM5);
        ROOM5
                .addBranch(FINISH, KEY_PURPLE)
                .addBranch(ROOM6);
        ROOM6
                .addBranch(ROOM7, KEY_GREEN);
        ROOM7
                .addBranch(ROOM8);
    }


    // =============================================================
    //  PUBLIC METHODS
    //  ============================================================

    /**
     * return to last place
     */
    public void returnToLastPlace() {
        if (currentRoom == 0) return;
        moveAtPlace(roomsWalkLine.get(currentRoom - 1).getId());
    }

    /**
     * @param toPlaceId id of the model
     * @return false if no place is not connected to it
     */

    public boolean moveAtPlace(int toPlaceId) {
        ARoom place = findPlaceFromCurrentPlace(toPlaceId);
        //  If no connected place found, return false
        if (place == null) return false;

        //  Move to next room or move back
        if (currentRoom == 0) {
            //  You move to next room
            roomsWalkLine.add(place);
            currentRoom++;
        } else if (roomsWalkLine.get(currentRoom - 1).getId() == toPlaceId) {
            //  You move back
            roomsWalkLine.remove(currentRoom--);
        } else {
            //  You move to next room
            roomsWalkLine.add(place);
            currentRoom++;
        }


        //  Remove life from user for moving
        PersonManager.get().getPerson().appendLife(IModel.LIFE_USED_MOVE_BETWEEN_ROOMS);

        //  Reset context to person
        ContextManager.get().setCurrentContext(null);
        //  Some statistics update
        Statistic.getCurrent().roomMovements++;
        if (!getCurrentPlace().isDiscovered()) Statistic.getCurrent().roomsDiscovered++;

        //  set this place to discovered
        getCurrentPlace().setIsDiscovered(true);
        return true;
    }

    /**
     * Searches all connected places by model id
     */
    private ARoom findPlaceFromCurrentPlace(int id) {
        for (IModel nextPlace : getCurrentPlace().getLinkedPlaces()) {
            //  the linked places in Rooms are always Doors
            Door door = (Door) nextPlace;
            for (ARoom room : door.getNextPlaces()) {
                if (room.getId() == id) return room;
            }
        }

        return null;
    }

    public ARoom getCurrentPlace() {
        return roomsWalkLine.get(currentRoom);
    }
}
