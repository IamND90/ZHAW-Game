package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.models.AModel;

/**
 * Created by IamND on 09.10.2015.
 */
public abstract class Item extends AModel {

    protected int usedSpace = 22;

    public Item(int id, TYPE type) {
        super(id, type);
    }


    public int getSpaceUsed() {
        return usedSpace;
    }
}
