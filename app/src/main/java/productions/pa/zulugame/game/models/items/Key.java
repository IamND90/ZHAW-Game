package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;

/**
 * Created by Andrey on 15.10.2015.
 */
public class Key extends Item {



    public Key(int doorId) {
        super(doorId, null);

    }

    @Override
    public Answer interactWithItem(Item item) {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public Answer executeCommand(Command command) {
        return null;
    }
}
