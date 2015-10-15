package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.places.APlace;
import productions.pa.zulugame.game.models.places.Room;
import productions.pa.zulugame.game.parser.Attribute;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.story.PersonManager;
import productions.pa.zulugame.game.story.PlaceManager;

/**
 * Created by IamND on 09.10.2015.
 */
public class Door extends APlace {

    public static final int DOOR_START_ID = 1000;
    private Room parentPlaces[];

    public Door(Room room1, Room room2) {
        super(room1.getId(),TYPE.DDOR);
        parentPlaces = new Room[]{room1,room2};

    }



    @Override
    public Answer interactWithItem(Item item) {
        boolean hasitem = hasItem(item);

        switch (item.getName()){
            case HitWordFactory.ITEM_KEY:
                if(hasitem){
                    //Open the door
                    openDoor();
                    return new Answer(MessageFactory.DOOR_UNLOCKED, Answer.TYPE.SUCCESS, Answer.TYPE.MOVE_TO_PLACE);
                }

                break;
        }
        if(!hasItem(item))return new Answer(MessageFactory.MESSAGE_BACKPACK_IS_EMPTY, Answer.TYPE.FAIL);
        return null;
    }

    private void openDoor() {


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
            if(command.getAttribute().getString().equalsIgnoreCase(HitWordFactory.DOOR)){
                Room place =findPlaceByDirection(command.getPointer().getString());
                if(place != null){
                    return  place.processCommand(command);
                }

            }


        }

        return null;
    }




    public boolean open(){

        if(hasAttribute(Attribute.STATUS.CLOSED)){
            Item key = PersonManager.get().getPerson().getBackpack().findItemByName(HitWordFactory.ITEM_KEY);
            if(key != null){
                //Remove the key from backpack because used
                PersonManager.get().getPerson().getBackpack().removeItemByName(HitWordFactory.ITEM_KEY);
                return true;
            }
        }
        return true;
    }


    @Override
    public Room getPreviousPlace() {
        return (PlaceManager.get().getCurrentPlace().getId() == parentPlaces[1].getId() ?  parentPlaces[1] : parentPlaces[0]);
    }

    public Room getNextPlace(){
        //TODO
        return (PlaceManager.get().getCurrentPlace().getId() == parentPlaces[0].getId() ?  parentPlaces[1] : parentPlaces[0]);
    }

    @Override
    public Room[] getNextPlaces() {
        return parentPlaces;
    }
}
