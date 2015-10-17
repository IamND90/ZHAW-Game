package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.models.items.Box;
import productions.pa.zulugame.game.models.items.Door;
import productions.pa.zulugame.game.models.items.Key;
import productions.pa.zulugame.game.models.items.LifeBottle;
import productions.pa.zulugame.game.models.places.APlace;
import productions.pa.zulugame.game.models.places.PlaceRoom1;
import productions.pa.zulugame.game.models.places.PlaceStart;
import productions.pa.zulugame.game.models.places.Room;

/**
 * Created by Andrey on 09.10.2015.
 */
public class RoomManager {

    static RoomManager mThis;
    static final int LIFE_NEEDED_TO_SWITCH_ROOM = -12;


        final Key KEY1_BOX1 = Key.generateKey(IModel.COLOR.getRandom());
        final Key KEY2_BOX1 = Key.generateKey(IModel.COLOR.getRandomExclude(KEY1_BOX1.getColor()));

        final Box BOX1 = new Box()
                .addItem(KEY1_BOX1)
                .addItem(KEY2_BOX1)
                .addItem(new LifeBottle(56));

    final Room ROOM1 = new PlaceRoom1(1).addBoxItem(BOX1);


    final Room ROOM2 = new PlaceRoom1(2);



    final Room START = new PlaceStart(0)
            .addBranch(ROOM1)
            .addBranch(ROOM2)
            .addBoxItem(
                    new Box().addItem(Key.generateKey(ROOM1.getColor())).addItem(new LifeBottle(40)));








    final Room myPlaces[] = {
            START,
            ROOM1,
            ROOM2
    };

    Room currentPlace;


    private RoomManager(){

        currentPlace = myPlaces[0];

    }
    public static RoomManager get(){
        if(mThis == null){
            synchronized (RoomManager.class){
                mThis = new RoomManager();
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

        PersonManager.get().getPerson().appendLife(LIFE_NEEDED_TO_SWITCH_ROOM);
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
