package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.models.places.AbstractStoryPlace;
import productions.pa.zulugame.game.models.places.PlaceStart;

/**
 * Created by Andrey on 09.10.2015.
 */
public class PlaceManager {

    static PlaceManager mThis;

    final AbstractStoryPlace myPlaces[] = {
            new PlaceStart(1)
    };

    AbstractStoryPlace currentPlace;


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

    public AbstractStoryPlace getPlaceAtId(int currentId, int moveToId){

        return null;
    }

    public boolean moveAtPlace(int toPlaceId){
        AbstractStoryPlace place = findPlaceFromCurrentPlace(toPlaceId);
        if(place == null) return false;

        currentPlace = place;

        return true;
    }

    private AbstractStoryPlace findPlaceFromCurrentPlace(int id) {
        for( AbstractStoryPlace nextPlace: currentPlace.getNextPlaces()){
            if(nextPlace.getId() == id) return nextPlace;
        }

        return null;
    }

    public AbstractStoryPlace getCurrentPlace() {
        return currentPlace;
    }
}
