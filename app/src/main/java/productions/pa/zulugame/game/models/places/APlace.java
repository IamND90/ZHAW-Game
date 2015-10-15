package productions.pa.zulugame.game.models.places;

import java.util.ArrayList;
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
public abstract class APlace extends AModel {

    //protected APlace parentPlace = null;
    //List<Door> linkedDoors = new ArrayList<>();

    public APlace(int id, TYPE type){
        super(id,type);
    }


    @Override
    public void setAttributes(Attribute... attributes) {
        super.setAttributes(attributes);


    }

    public abstract Room getPreviousPlace();

    public abstract Room getNextPlace();

    public abstract Room[] getNextPlaces();


    public Room findPlaceByDirection(String... attributes){
        for(Room door : getNextPlaces()){
            for(String attr: attributes){
                if(door.hasAttribute(Attribute.byPointer(attr).getPointer())) return door;
            }

        }

        return null;
    }


}
