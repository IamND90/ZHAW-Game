package productions.pa.zulugame.game.models.baseclasses;

import android.util.Log;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.manager.RoomManager;
import productions.pa.zulugame.game.models.Box;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.models.items.Key;
import productions.pa.zulugame.game.models.places.Door;
import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by Andrey on 08.10.2015.
 * <p/>
 * This class is a parent for all the Rooms
 * For more information see @APlace
 */
public abstract class ARoom extends APlace {
    //  ------------------------------------------------------------
    //  STATIC FINAL FIELDS
    //  ------------------------------------------------------------

    private static final String TAG = "Room";

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------
    protected String roomName = "No Name";
    boolean isDiscovered = false;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================
    public ARoom() {
        super(TYPE.ROOM);
    }

    /**
     * Adds a new Box to the room*/
    public ARoom addBoxItem(Box boxItem) {
        getSubItems().add(boxItem);
        return this;
    }

    public ARoom setRoomColor(COLOR color) {
        super.setColor(color);
        return this;
    }

    /**
     * Links another room to this
     * Door is created automatically
     * color random*/
    public ARoom addBranch(ARoom place) {
        if (findRoomById(place.getId()) != null) {
            Log.e(TAG, "You cant link a room twice:" + getId() + " and " + place.getId());
            return this;
        }
        Door door = new Door(this, place);
        //auto find rghtn, not used colro in this room
        findColorForDoor(door);

        if (addDoor(door))
            place.addDoor(door);
        return this;
    }

    /**
     * Links another room to this
     * Door is created automatically
     * color given, is closed and can be opened only be this color*/
    public ARoom addBranch(ARoom place, COLOR colorToOpen) {
        if (findRoomById(place.getId()) != null) {
            Log.e(TAG, "You cant link a room twice:" + getId() + " and " + place.getId());
            return this;
        }
        Door door = new Door(this, place, colorToOpen);
        if (addDoor(door))
            place.addDoor(door);
        return this;
    }

    /**
     * Links another room to this
     * Door is created automatically
     * color given, is closed and can be opened only by the specific key
     * //TODO woks only like top function yet*/
    public ARoom addBranch(ARoom place, Key keyToOpen) {
        if (findRoomById(place.getId()) != null) {
            Log.e(TAG, "You cant link a room twice:" + getId() + " and " + place.getId());
            return this;
        }
        Door door = new Door(this, place, keyToOpen);
        if (addDoor(door))
            place.addDoor(door);
        return this;
    }

    /**
     * Filters all the door colors in the current room and sets random color*/
    private void findColorForDoor(Door door) {
        COLOR colors[] = COLOR.values();
        boolean free[] = new boolean[colors.length];

        for (int i = 0; i < colors.length; i++) {
            free[i] = true;
            for (IModel linked : linkedPlaces) {
                if (colors[i].equals(linked.getColor())) free[i] = false;
            }
        }

        for (int isUsed = 0; isUsed < free.length; isUsed++)
            if (free[isUsed]) {
                door.setColor(colors[isUsed]);
                return;
            }

    }


    //  ============================================================
    //  ABSTRACT METHODS
    //  ============================================================
    public String getStory() {
        String story = "\nRoom is called [" + getName() + "] ,the Number is: [" + getId() + "]\n";

        return story + getDescription();
    }

    //  ============================================================
    //  GETTERS / FINDERS
    //  ============================================================

    @Override
    public String getName() {
        return roomName;
    }


    public boolean isDiscovered() {
        return isDiscovered;
    }

    public void setIsDiscovered(boolean isDiscovered) {
        this.isDiscovered = isDiscovered;
    }

//  ============================================================
    //  PUBLIC METHODS
    //  ============================================================


    //  ============================================================
    //  @Override METHODS
    //  ============================================================


    @Override
    public Answer processCommand(Command command) {
        Log.i(TAG, "processCommand");
        //  Return to the last room
        if (command.hasActionOf(HitWord.RETURN)) {
            RoomManager.get().returnToLastPlace();
            return new Answer("You returned to the last room" + RoomManager.get().getCurrentPlace().getDescription(), Answer.DECORATION.SIMPLE);
        }

        //  enter a room by door or room name / color
        if (command.hasActionOf(HitWord.OPEN, HitWord.ENTER)) {
            // OPEN THE DOOR
            if (command.hasAttributeOf(HitWord.DOOR)) {
                Door door = findDoorByColor(command.getPointer());
                if (door == null && linkedPlaces.size() > 0) door = (Door) linkedPlaces.get(0);
                Log.i(TAG, "Is door: " + door == null ? "null" : "");
                if (door != null) {
                    if (door.openDoor()) {
                        return moveTo(door);
                    } else {
                        Log.e(TAG, door.getColor().name() + " is closed, you need a key of the same color to openDoor it");
                        return new Answer(MessageFactory.MESSAGE_NO_KEY, Answer.DECORATION.FAIL);
                    }
                }
                return new Answer("No [" + command.getPointer() + "] door in this room!", Answer.DECORATION.FAIL);
            }

            // ENTER A ROOM BY COLOR
            if (command.hasAttributeOf(HitWord.ROOM)) {
                ARoom room = (ARoom) findRoomByNameOrColor(command.getAttribute(), command.getPointer());
                if (room != null) {
                    if (room.openRoom(this)) {

                        return moveTo(room);
                    } else
                        return new Answer(MessageFactory.MESSAGE_NO_KEY, Answer.DECORATION.ERROR);
                }
                return new Answer("No [" + command.getPointer() + "] room is next to you!", Answer.DECORATION.ERROR);
            }

        }

        //  Other possibilietires to  move between rooms
        if (command.hasActionOf(HitWord.GO, HitWord.GOTO)) {
            ARoom room = (ARoom) findRoomByNameOrColor(command.getAttribute(), command.getPointer());
            if (room != null) {
                if (room.openRoom(this)) {
                    return moveTo(room);
                } else
                    return new Answer(MessageFactory.MESSAGE_NO_KEY, Answer.DECORATION.ERROR);
            }
            return new Answer("No [" + command.getPointer() + "] room is next to you!", Answer.DECORATION.ERROR);
        }

        Log.i(TAG, "No suitable command found, checking subModels");
        return super.processCommand(command);
    }


    @Override
    public String getDescription() {
        String description = "\tThis room contains:";
        for (IModel item : getSubItems()) {
            if (item.getType().equals(TYPE.BOX)) {
                Box box = (Box) item;
                description += "\n\t " + box.getColor().name() + " box";
            }
        }
        description += "\n";
        for (IModel door : linkedPlaces) {
            description += "\n\t " + door.getColor().name() + " " + door.getName();
        }

        return description;
    }


    //  ============================================================
    //  PRIVATE / PROTECTED METHODS
    //  ============================================================

    /**
     * @param door the door you want to move trough
     * @return answer should always be positive if there are no bug in code
     */
    private Answer moveTo(Door door) {
        ARoom nextPlace = door.getNextPlace();
        return moveTo(nextPlace);
    }

    private Answer moveTo(APlace nextPlace) {
        if (RoomManager.get().moveAtPlace(nextPlace.getId())) {
            return new Answer("Moving from [" + getName() + "] to ["
                    + nextPlace.getName() + "]"
                    , Answer.DECORATION.SIMPLE);
        }
        return new Answer("Error at moving between rooms", Answer.DECORATION.ERROR);
    }

    protected boolean addDoor(Door door) {

        Door existing;
        if ((existing = findDoorByColor(door.getColor().name())) != null) {
            if (existing.isClosed()) {
                Log.e(TAG, "Not possible having two same-colored doors at the same time!\n"
                        + "Rooms [" + door.getNextPlaces().get(0).getName() + "] and [" + door.getNextPlaces().get(1).getName() + "] may have two same-colored doors");
            } else {
                findColorForDoor(existing);
            }
            return true;
        }

        if (door != null) {
            linkedPlaces.add(door);
            return true;
        }
        return false;
    }

    protected boolean openRoom(ARoom parentRoom) {
        for (IModel model : linkedPlaces) {
            Door door = (Door) model;
            if (door.findByRooms(this, parentRoom)) return door.openDoor();
        }
        return false;
    }


    private Door findDoorByColor(String color) {
        for (IModel model : linkedPlaces) {
            Door door = (Door) model;
            if (door.getColor().name().equalsIgnoreCase(color)) return door;
        }
        return null;
    }


}
