package productions.pa.zulugame.game.models.places;

import productions.pa.zulugame.game.MessageFactory;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.items.Item;
import productions.pa.zulugame.game.parser.HitWordFactory;

/**
 * Created by Andrey on 09.10.2015.
 */
public class PlaceRoom1 extends Room {

    private static final String NAME = "Tiny chamber";
    private static final String DESCRIPTION =
            "The room is dark, approximately 1 by 1 meters, no light.\n";
    private static final String STORY = "You enter a small chamber with no light in it, right.\n";



    public PlaceRoom1(int id){
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
        return DESCRIPTION + super.getDescription();
    }

    @Override
    public Answer processCommand(Command command) {

        return checkSubModels(command);
    }

    @Override
    public Answer interactWithItem(Item item) {
        return new Answer(MessageFactory.MESSAGE_CANNOT_INTERACT, Answer.TYPE.FAIL);
    }


}
