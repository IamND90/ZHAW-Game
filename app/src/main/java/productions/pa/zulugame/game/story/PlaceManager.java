package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.models.places.APlace;
import productions.pa.zulugame.game.models.places.PlaceRoom1;
import productions.pa.zulugame.game.models.places.PlaceStart;
import productions.pa.zulugame.game.parser.Attribute;
import productions.pa.zulugame.game.parser.HitWordFactory;

/**
 * Created by Andrey on 09.10.2015.
 */
public class PlaceManager {

    static PlaceManager mThis;



    final APlace ROOM2 = new PlaceRoom1(2);
    final APlace ROOM1 = new PlaceRoom1(1);

    final APlace START = new PlaceStart(0)
            .addBranch(ROOM1, new Attribute().setPointer(Attribute.POINTING.RIGHT).setStatus(Attribute.STATUS.CLOSED))
            .addBranch(ROOM2, new Attribute().setPointer(Attribute.POINTING.LEFT));

    final APlace myPlaces[] = {
            START,
            ROOM1
    };

    APlace currentPlace;


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
        APlace place = findPlaceFromCurrentPlace(toPlaceId);
        if(place == null) return false;

        currentPlace = place;

        return true;
    }

    private APlace findPlaceFromCurrentPlace(int id) {
        for( APlace nextPlace: currentPlace.getNextPlaces()){
            if(nextPlace.getId() == id) return nextPlace;
        }

        return null;
    }

    public APlace getCurrentPlace() {
        return currentPlace;
    }
}
