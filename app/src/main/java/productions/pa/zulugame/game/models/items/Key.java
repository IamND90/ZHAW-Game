package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;

/**
 * Created by Andrey on 15.10.2015.
 */
public class Key extends Item {

    static int counter = 0;


    public Key() {
        super(counter++, TYPE.KEY);

    }
    public static Key generateKey(COLOR color){
        Key key = new Key();
        key.setColor(color);
        return key;
    }


    @Override
    public String getName() {
        return "Key";
    }

    @Override
    public String getDescription() {
        return "Can open doors";
    }

    @Override
    public Answer processCommand(Command command) {
        return null;
    }


    @Override
    protected int getSpaceUsed() {
        return 4;
    }
}
