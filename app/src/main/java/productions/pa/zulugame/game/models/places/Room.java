package productions.pa.zulugame.game.models.places;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.AModel;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.models.items.Box;
import productions.pa.zulugame.game.models.items.Door;
import productions.pa.zulugame.game.models.items.Item;
import productions.pa.zulugame.game.parser.Attribute;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.story.PlaceManager;

/**
 * Created by Andrey on 08.10.2015.
 */
public  abstract  class Room extends APlace {

    protected Room parentPlace = null;
    List<Door> linkedDoors = new ArrayList<>();

    public Room(int id){
        super(id,TYPE.ROOM);
    }

    @Override
    public COLOR getColor() {
        return null;
    }

    @Override
    public void setColor(COLOR color) {

    }

    @Override
    public Room getNextPlace() {
        return linkedDoors.get(0).getNextPlace();
    }

    @Override
    public Room[] getNextPlaces() {

        Room rooms[] =  new Room[linkedDoors.size()];
        for(int i =0; i< linkedDoors.size();i++){
            Room room = linkedDoors.get(i).getNextPlace();
            rooms[i] = room;
        }
        return rooms;
    }

    public Room addBranch( Room place, Attribute... attributes){
        addDoor(new Door(this,place));
        place.setParentPlace(this);
        return this;
    }

    public Door findDoorByPlaces(int... id){
        for(int i =0 ; i < id.length ; i++){
            for(Door door : linkedDoors){
                if(door.getId() == id[i])return door;
            }
        }
        return null;
    }

    public List<Door> getLinkedDoors() {
        return linkedDoors;
    }

    public Room getPreviousPlace(){return parentPlace;}

    public Room getParentPlace() {
        return parentPlace;
    }

    protected void addDoor(Door door){
        if(door != null && findDoorByPlaces(getId()) != null)
            linkedDoors.add(door);
    }
    public void setParentPlace(Room parentPlace) {
        this.parentPlace = parentPlace;
        Door door = parentPlace.findDoorByPlaces(getId(),parentPlace.getId());
        //Do not add twice the same door
        if(door != null && findDoorByPlaces(getId()) != null)
            linkedDoors.add(door);
    }

    public abstract  String getStory();



    @Override
    public Answer processCommand(Command command) {
        String action = command.getAction().getString();
        if(action.equalsIgnoreCase(Attribute.ACTING.OPEN.name()) || action.equalsIgnoreCase(HitWordFactory.ENTER)) {
            // Open the door / enter the room
            // ================
            String attribute =command.getAttribute().getString();
            if(attribute.equalsIgnoreCase(HitWordFactory.DOOR) || attribute.equalsIgnoreCase(HitWordFactory.ROOM) ){
                Door door = (Door) findDoorByDirection(command.getPointer().getString());
                if(door != null){
                    if( door.open()){
                        if(PlaceManager.get().moveAtPlace(door.getNextPlace().getId())){
                            return new Answer(PlaceManager.get().getCurrentPlace().getStory(), Answer.TYPE.SUCCESS, Answer.TYPE.MOVE_TO_PLACE);
                        }
                        else{
                            //ERROR
                        }
                    }
                    else return new Answer(MessageFactory.MESSAGE_NO_KEY, Answer.TYPE.FAIL, Answer.TYPE.ITEM_NOT_FOUND);
                }
                return new Answer("At [" + command.getPointer().getString() + "] is no door",Answer.TYPE.FAIL, Answer.TYPE.PLACE_NOT_FOUND);
            }
            // Open a box
            // ================
            //TODO
        }

        return checkSubModels(command);
    }

    private Door findDoorByDirection(String string) {

        for(Door door :linkedDoors){

        }

        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        String description = "This box contains " +getSubItems().size() + "items";
        for(IModel item :getSubItems()){
            if(item.getType().equals(TYPE.BOX)){
                Box box = (Box) item;
                description +=  "A box" + (box.getAttributes() == null ? "": "on the " + box.getAttributes()[0].getPointer().name());
            }
        }
        for(Door door : linkedDoors){
            description += "A door on the " + (door.getAttributes() == null ? "": "on the " + door.getAttributes()[0].getPointer().name());
        }

        return description;
    }

    @Override
    public Answer interactWithItem(Item item) {
        return null;
    }
}
