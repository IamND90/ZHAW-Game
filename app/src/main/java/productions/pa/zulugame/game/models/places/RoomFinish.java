package productions.pa.zulugame.game.models.places;

import productions.pa.zulugame.game.Game;
import productions.pa.zulugame.game.Statistic;
import productions.pa.zulugame.game.models.baseclasses.ARoom;
import productions.pa.zulugame.game.manager.PersonManager;

/**
 * Created by IamND on 17.10.2015.
 *
 * This is the finish room, if you reach it, you win the game
 * it triggers the gamestatus, which shows you the statistics
 */
public class RoomFinish extends ARoom {

    private static final String STORY =
            "|| !Congratilations "+PersonManager.get().getPerson().getName() +"! ||\n" +
            "You have made it to the last room alive and won the game.\n"+
            "Below you cna see your statistics, type 'save:stats SOMENAME' to store your statistics.\n"+
                    Statistic.getCurrent().getString();


    @Override
    public String getName() {
        return "Finish";
    }

    @Override
    public String getStory() {

        Game.getInstance().changeGameStatus(Game.GameStatus.OVER,"You have won the game!");

        return STORY;
    }
}
