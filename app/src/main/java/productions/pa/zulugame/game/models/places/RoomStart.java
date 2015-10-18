package productions.pa.zulugame.game.models.places;

import productions.pa.zulugame.game.models.baseclasses.ARoom;
import productions.pa.zulugame.game.manager.PersonManager;

/**
 * Created by Andrey on 09.10.2015.
 *
 * This is the Startroom, it also has some help information, which pop up when you start a new game
 */
public class RoomStart extends ARoom {

    boolean isFirstTimeShower = true;
    private static final String STORY =
                            "Hello " + PersonManager.get().getPerson().getName() +
                                    ",\nWelcome to the game.\n"+
                                    "Type 'help for help or 'info' to look around.\n"+
                                    "To fill up health, use bottles or solve riddles.\n"+
                                    "Moving or opening boxes costs you health.\n"+
                                    "There are a lot more commands.Try it out!";


    public RoomStart(){
        super();
        roomName ="Start";
        setIsDiscovered(true);
    }


    @Override
    public String getStory() {

        if(isFirstTimeShower) {
            isFirstTimeShower = false;
            return STORY + super.getStory();
        }
        return super.getStory();
    }


}
