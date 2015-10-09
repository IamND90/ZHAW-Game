package productions.pa.zulugame.game.models;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.parser.HitWordType;

/**
 * Created by IamND on 09.10.2015.
 */
public class Backpack extends AbstractModel{


    public Backpack() {
        super(ModelManager.ID_BACKPACK,IModel.TYPE.ITEM);
    }

    @Override
    public String getName() {
        return "Backpack";
    }

    @Override
    public String getDescription() {
        return "Here you can store your items";
    }

    @Override
    public Answer executeCommand(Command command) {


        if (command.getAction().equals(HitWordFactory.GET) || command.getAction().equals(HitWordFactory.USE)) {
            if(command.getAttribute() != null){

            }
        }


        return null;
    }
}
