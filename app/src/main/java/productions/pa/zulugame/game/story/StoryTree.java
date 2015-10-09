package productions.pa.zulugame.game.story;

import productions.pa.zulugame.game.story.places.AbstractStoryPlace;
import productions.pa.zulugame.game.story.places.PlaceStart;

/**
 * Created by Andrey on 09.10.2015.
 */
public class StoryTree {

    static StoryTree mThis;

    static final int ID_JUMP = 100;

    static AbstractStoryPlace currentPlace;


    private StoryTree(){

        currentPlace = new PlaceStart(0,null);

    }
    public static StoryTree get(){
        if(mThis == null){
            synchronized (StoryTree.class){
                mThis = new StoryTree();
            }
        }
        return mThis;
    }

    public AbstractStoryPlace getPlaceAtId(int currentId, int moveToId){

        return null;
    }


    public AbstractStoryPlace getCurrentPlace() {
        return currentPlace;
    }
}
