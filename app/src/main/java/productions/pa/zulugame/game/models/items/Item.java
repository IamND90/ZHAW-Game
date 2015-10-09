package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.AbstractModel;

/**
 * Created by IamND on 09.10.2015.
 */
public abstract class Item extends AbstractModel {


    public Item(int id) {
        super(id, TYPE.ITEM);
    }


}
