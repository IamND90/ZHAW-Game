package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.models.baseclasses.AItem;

/**
 * Created by Andrey on 15.10.2015.
 *
 * A Key can open doors if it has the same color or if needed the needed id by the door
 */
public class Key extends AItem {

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public Key() {
        super(TYPE.KEY);
        spaceUsed = SPACE_KEY;

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
        return "Can open "+ getColor().name() + " doors";
    }


}
