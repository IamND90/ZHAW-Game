package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.models.AModel;

/**
 * Created by IamND on 09.10.2015.
 */
public abstract class Item extends AModel {


    public Item(int id, TYPE type) {
        super(id, type);
    }


    protected abstract int getSpaceUsed();

}
