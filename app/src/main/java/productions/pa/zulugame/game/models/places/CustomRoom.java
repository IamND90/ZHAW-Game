package productions.pa.zulugame.game.models.places;

import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.models.baseclasses.ARoom;

/**
 * Created by Andrey on 09.10.2015.
 *
 * Custom editable room with no special actions
 */
public class CustomRoom extends ARoom {


    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public CustomRoom(String name){
        super();
        roomName = name;
    }

}
