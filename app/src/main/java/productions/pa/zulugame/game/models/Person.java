package productions.pa.zulugame.game.models;

import productions.pa.zulugame.game.Game;
import productions.pa.zulugame.game.models.baseclasses.AModel;
import productions.pa.zulugame.game.models.items.Backpack;

/**
 * Created by IamND on 09.10.2015.
 */
public class Person extends AModel {

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------

    /**
     * @param mName Name of the player, can be set with the command name:myName
     * @param lifeLeft amount of life the plyer has, if below 0, you loose*/
    String mName;
    int lifeLeft = START_LIFE_PERSON;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public Person(String name) {
        super(TYPE.PERSON);
        mName = name;
        getSubItems().add(new Backpack());
    }

    //  ============================================================
    //  @Override METHODS
    //  ============================================================

    @Override
    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Override
    public String getDescription() {

        String info = getName() + ", have " + lifeLeft + " life left.\n";
        info += getBackpack().getDescription();

        return info;
    }


    public Backpack getBackpack() {
        return (Backpack) getSubItems().get(0);
    }

    /**
     * apppends / removes life from user and check if <=0 : GAME OVER
     * @return by how much life healed. If dead: life left(<=0)
     */
    public int appendLife(int value) {
        lifeLeft += value;
        if (lifeLeft > MAXIMUM_LIFE_PERSON) {
            int diff = lifeLeft - MAXIMUM_LIFE_PERSON;
            lifeLeft = MAXIMUM_LIFE_PERSON;
            return diff;
        }

        if (lifeLeft <= 0) {
            // GAME OVER!!
            Game.getInstance().changeGameStatus(Game.Gamestatus.OVER, "Your life is empty:" + lifeLeft);

            return lifeLeft;
        }
        return value;
    }
}
