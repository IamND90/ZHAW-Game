package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.models.AModel;

/**
 * Created by IamND on 09.10.2015.
 */
public abstract class Item extends AModel {


    protected final Item interactItem[];

    public Item(int id, Item... items) {
        super(id, TYPE.ITEM);
        interactItem = items;
    }




}
