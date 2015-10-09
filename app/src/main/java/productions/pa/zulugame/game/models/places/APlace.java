package productions.pa.zulugame.game.models.places;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.AModel;
import productions.pa.zulugame.game.models.items.Door;
import productions.pa.zulugame.game.parser.Attribute;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.story.PlaceManager;

/**
 * Created by Andrey on 08.10.2015.
 */
public abstract class APlace extends AModel {

    protected final List<APlace> nextPlaces = new ArrayList<>();
    protected APlace parentPlace = null;


    public APlace(int id){
        super(id,TYPE.PLACE);
    }


    public APlace addBranch( APlace place, Attribute... attributes){
        Door linkDoor= new Door(place.getId()+ Door.DOOR_START_ID);
        linkDoor.setParentPlace(this);
        linkDoor.setNextPlace(place);
        linkDoor.setAttributes(attributes);

        nextPlaces.add(place);
        place.setParentPlace(this);
        return this;
    }

    public List<APlace> getNextPlaces() {
        return nextPlaces;
    }

    APlace getPreviousPlace(){return parentPlace;}

    public APlace getParentPlace() {
        return parentPlace;
    }

    public void setParentPlace(APlace parentPlace) {
        this.parentPlace = parentPlace;
    }

    public abstract String getStory();

    public APlace findPlaceByDirection(String... attributes){
        for(APlace door : nextPlaces){
            for(String attr: attributes){
                if(door.hasAttribute(Attribute.byPointer(attr).getPointer())) return door;
            }

        }

        return null;
    }

    @Override
    public Answer executeCommand(Command command) {
        String action = command.getAction().getString();
        if(action.equalsIgnoreCase(Attribute.ACTING.OPEN.name()) || action.equalsIgnoreCase(HitWordFactory.ENTER)) {
            // Open the door / enter the room
            // ================
            String attribute =command.getAttribute().getString();
            if(attribute.equalsIgnoreCase(HitWordFactory.DOOR) || attribute.equalsIgnoreCase(HitWordFactory.ROOM) ){
                Door door = (Door) findPlaceByDirection(command.getPointer().getString());
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
}
