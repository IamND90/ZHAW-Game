package productions.pa.zulugame.game.models.places;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.models.items.Box;
import productions.pa.zulugame.game.models.items.Door;
import productions.pa.zulugame.game.parser.Attribute;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.story.RoomManager;

/**
 * Created by Andrey on 08.10.2015.
 */
public abstract class Room extends APlace {
    private static final String TAG = "Room";
    List<Door> linkedDoors = new ArrayList<>();

    public Room(int id) {
        super(id, TYPE.ROOM);
    }



    public Room addBoxItem(Box boxItem) {
        getSubItems().add(boxItem);
        return this;
    }

    public Room addBranch(Room place) {
        Door door = new Door(this, place);
        addDoor(door);
        place.addDoor(door);
        return this;
    }

    public Room addBranch(Room place, COLOR colorToOpenRoom) {
        Door door = new Door(this, place);
        door.setClosed(colorToOpenRoom.name());
        addDoor(door);
        place.addDoor(door);
        return this;
    }

    public Door findDoorByPlaces(int... id) {
        for (int i = 0; i < id.length; i++) {
            for (Door door : linkedDoors) {
                if (door.getId() == id[i]) return door;
            }
        }
        return null;
    }

    public Door findDoorByColor(COLOR color) {
        for (Door door : linkedDoors) {
            if (door.getColor().equals(color)) return door;
        }
        return null;
    }

    public List<Door> getLinkedDoors() {
        return linkedDoors;
    }


    protected void addDoor(Door door) {
        if (door != null && findDoorByColor(door.getColor()) == null)
            linkedDoors.add(door);
    }


    public abstract String getStory();


    @Override
    public Answer processCommand(Command command) {
        String action = command.getAction().getString();
        if (action.equalsIgnoreCase(Attribute.ACTING.OPEN.name()) || action.equalsIgnoreCase(HitWord.ENTER)) {
            String attribute = command.getAttribute().getString();
            // OPEN THE DOOR
            if (attribute.equalsIgnoreCase(HitWord.DOOR)) {
                Door door = findDoorByColor(command.getPointer().getString());
                if (door == null && linkedDoors.size() > 0) door = linkedDoors.get(0);
                Log.i(TAG, "Is door: " + door == null ? "null" : "");
                if (door != null) {
                    if (door.open()) {
                        Log.d(TAG, "moving from " + getId() + " to place " + door.getNextPlace().getId());
                        return new Answer("", Answer.TYPE.SUCCESS, Answer.TYPE.MOVE_TO_PLACE).setContextId(door.getNextPlace().getId());
                    } else {
                        Log.e(TAG, door.getColor().name() + " is closed, you need a key of the same color to open it");
                        return new Answer(MessageFactory.MESSAGE_NO_KEY, Answer.TYPE.FAIL, Answer.TYPE.ITEM_NOT_FOUND);
                    }
                }
                return new Answer("No [" + command.getPointer().getString() + "] door in this room!", Answer.TYPE.SIMPLE_OUTPUT, Answer.TYPE.PLACE_NOT_FOUND);
            }

            // ENTER A ROOM BY COLOR
            if (attribute.equalsIgnoreCase(HitWord.ROOM)) {
                Room room = findRoomByColor(command.getPointer().getString());
                if (room != null) {
                    if (room.open(this)) {

                        Log.d(TAG, "moving from " + getId() + " to place " + RoomManager.get().getCurrentPlace().getId());
                        return new Answer("", Answer.TYPE.SUCCESS, Answer.TYPE.MOVE_TO_PLACE).setContextId(room.getId());

                    } else
                        return new Answer(MessageFactory.MESSAGE_NO_KEY, Answer.TYPE.FAIL, Answer.TYPE.ITEM_NOT_FOUND);
                }
                return new Answer("No [" + command.getPointer().getString() + "] room is next to you!", Answer.TYPE.FAIL, Answer.TYPE.PLACE_NOT_FOUND);
            }

            // Open a box
            // ================
            //TODO
        }

        return checkSubModels(command);
    }


    protected boolean open(Room parentRoom) {
        for (Door door : linkedDoors) {
            if (door.findByRooms(this, parentRoom)) return door.open();
        }
        return false;
    }

    private Door findDoorByColor(String color) {
        for (Door door : linkedDoors) {
            if (door.getColor().name().equalsIgnoreCase(color)) return door;
        }
        return null;
    }

    private Room findRoomByColor(String color) {
        for (Door door : linkedDoors) {
            if (door.getNextPlace().getColor().name().equalsIgnoreCase(color))
                return door.getNextPlace();

        }
        return null;
    }

    @Override
    public String getDescription() {
        String description = "\nYou are in a " + getColor().name() + " room";
        for (IModel item : getSubItems()) {
            if (item.getType().equals(TYPE.BOX)) {
                Box box = (Box) item;
                description += "\n\tA " + box.getColor().name() + " box";
            }
        }
        description += "\n";
        for (Door door : linkedDoors) {
            description += "\n\tA " + door.getColor().name() + " door";
        }

        return description;
    }


}
