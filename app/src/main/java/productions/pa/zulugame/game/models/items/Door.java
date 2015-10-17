package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.places.APlace;
import productions.pa.zulugame.game.models.places.Room;
import productions.pa.zulugame.game.parser.Attribute;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.story.PersonManager;
import productions.pa.zulugame.game.story.RoomManager;

/**
 * Created by IamND on 09.10.2015.
 */
public class Door extends APlace {

    public static final int DOOR_START_ID = 1000;
    private Room parentPlaces[];
    private String keyColorNeededToOpen = null;

    public Door(Room room1, Room room2) {
        super(room1.getId()*DOOR_START_ID + room2.getId(),TYPE.DDOR);
        parentPlaces = new Room[]{room1,room2};
    }

    public Door setClosed(String keyColor){
        keyColorNeededToOpen = keyColor;
        return this;
    }

    @Override
    public int getId() {
        return parentPlaces[0].getId() *DOOR_START_ID + parentPlaces[1].getId();
    }



    private void openDoor() {
        keyColorNeededToOpen = null;

    }

    @Override
    public String getName() {
        return "Door";
    }

    @Override
    public String getDescription() {
        return "This Door links room [" + parentPlaces[0].getName() + "] and [" + parentPlaces[1].getName()+"]";
    }

    @Override
    public Answer processCommand(Command command) {
        if(command.getAction().getString().equalsIgnoreCase(Attribute.ACTING.OPEN.name())) {
            if(command.getAttribute().getString().equalsIgnoreCase(HitWord.DOOR)){
                Room place =findRoomByColor(command.getPointer().getString());
                if(place != null){
                    return  place.processCommand(command);
                }
            }
        }
        return null;
    }

    private Room findRoomByColor(String color) {
        for(Room room : parentPlaces){
            if(room.getColor().name().equalsIgnoreCase(color))return room;
        }
        return null;
    }


    public boolean open() {

        if (isClosed()) {
            Item key = PersonManager.get().getPerson().getBackpack().findItemByNameAndColor(HitWord.ITEM_KEY, getColor().name());
            if (key != null) {
                //Remove the key from backpack because used
                PersonManager.get().getPerson().getBackpack().removeItemByName(HitWord.ITEM_KEY);
                return true;
            }
        }
        return true;
    }

    public Room getPreviousPlace() {
        return (RoomManager.get().getCurrentPlace().getId() == parentPlaces[1].getId() ?  parentPlaces[1] : parentPlaces[0]);
    }

    public Room getNextPlace(){
        //TODO
        return (RoomManager.get().getCurrentPlace().getId() == parentPlaces[0].getId() ?  parentPlaces[1] : parentPlaces[0]);
    }

    public Room[] getNextPlaces() {
        return parentPlaces;
    }

    public boolean isClosed() {
        return keyColorNeededToOpen == null ? true : false;
    }

    public boolean findByRooms(Room room, Room room2) {

        if((room.getId() == parentPlaces[0].getId() && room2.getId() == parentPlaces[1].getId()) ||
                room.getId() == parentPlaces[1].getId() && room2.getId() == parentPlaces[0].getId() )
            return true;

        return false;
    }
}
