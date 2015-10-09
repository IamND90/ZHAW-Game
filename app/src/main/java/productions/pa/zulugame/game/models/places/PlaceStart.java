package productions.pa.zulugame.game.models.places;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.story.PersonManager;

/**
 * Created by Andrey on 09.10.2015.
 */
public class PlaceStart extends AbstractStoryPlace {

    private static final String NAME = "The Start of the Labyrinth";
    private static final String DESCRIPTION =
            "The room is dark, approximately 10 by 10 meters. You are surrounded by concrete walls.To the right and left of you are two doors.";
    private static final String STORY = "We are in the year 2056.\n" +
            "The humanity has managed to map human thoughts to a computer six years ago. In the meantime first experiments of conciousness-transitions between computer and humans are running."+
            "Maybe you can't remember " + PersonManager.get().getPerson().getName() + ", but you are already in the Matrix."+
            "You agreed to this experiment due to science and we have transplanted you conciousness before you died in this human-like biorobot."+
            "Explore the old world in your new body!";

    public PlaceStart(int id){
        super(id);
    }



    @Override
    public String getStory() {
        return STORY;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public Answer executeCommand(Command command) {
        switch (command.getAction().getName()){
            case HitWordFactory.OPEN:


                break;
        }
        return checkSubModels(command);
    }
}
