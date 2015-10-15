package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.models.items.Door;
import productions.pa.zulugame.game.models.places.APlace;
import productions.pa.zulugame.game.models.places.PlaceRoom1;
import productions.pa.zulugame.game.models.places.PlaceStart;
import productions.pa.zulugame.game.models.places.Room;
import productions.pa.zulugame.game.parser.Attribute;
import productions.pa.zulugame.game.parser.HitWordFactory;

/**
 * Created by Andrey on 09.10.2015.
 */
public class PlaceManager {

    static PlaceManager mThis;



    final Room ROOM2 = new PlaceRoom1(2);
    final Room ROOM1 = new PlaceRoom1(1);

    final Room START = new PlaceStart(0)
            .addBranch(ROOM1, new Attribute().setPointer(Attribute.POINTING.RIGHT).setStatus(Attribute.STATUS.CLOSED))
            .addBranch(ROOM2, new Attribute().setPointer(Attribute.POINTING.LEFT));

    final Room myPlaces[] = {
            START,
            ROOM1,
            ROOM2
    };

    Room currentPlace;


    private PlaceManager(){

        currentPlace = myPlaces[0];

    }
    public static PlaceManager get(){
        if(mThis == null){
            synchronized (PlaceManager.class){
                mThis = new PlaceManager();
            }
        }
        return mThis;
    }

    public APlace getPlaceAtId(int currentId, int moveToId){

        return null;
    }

    public boolean moveAtPlace(int toPlaceId){
        Room place = findPlaceFromCurrentPlace(toPlaceId);
        if(place == null) return false;

        currentPlace = place;

        return true;
    }

    private Room findPlaceFromCurrentPlace(int id) {
        for( Door nextPlace: currentPlace.getLinkedDoors()){
            if(nextPlace.getNextPlace().getId() == id) return nextPlace.getNextPlace();
        }

        return null;
    }

    public Room getCurrentPlace() {
        return currentPlace;
    }
}
