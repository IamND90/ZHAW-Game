package productions.pa.zulugame.game.story.places;

import productions.pa.zulugame.game.commands.Command;

/**
 * Created by Andrey on 09.10.2015.
 */
public class PlaceStart extends AbstractStoryPlace {

    private static final String NAME = "The Start of the Labyrinth";
    private static final String DESCRIPTION =
            "The room is dark, approximately 10 by 10 meters. You are surrounded by concrete walls.In front of you is a door.";
    private static final String STORY = "This is the story..";

    public PlaceStart(int id, AbstractStoryPlace parent){
        super(id,TYPE.ROOM, parent);
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
    public String executeCommand(Command command) {
        switch (command.getType()){
            case MOVING:

                break;
            case ITEM:

                break;
            case ACTING:

                break;
            case POINTER:

                break;
            case NUMBER:

                break;

        }
        return checkSubModels(command);
    }
}
