package productions.pa.zulugame.game.models.items;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;

/**
 * Created by IamND on 17.10.2015.
 */
public class LifeBottle extends  Item {
    static int counter =0;

    int lifeAdded;
    public LifeBottle(int life) {
        super(counter++, TYPE.LIFEBOTTLE);
        lifeAdded = life;
    }

    public int getLife() {
        int temp = lifeAdded;
        lifeAdded=0;
        return temp;
    }

    @Override
    protected int getSpaceUsed() {
        return 0;
    }

    @Override
    public String getName() {
        return TYPE.LIFEBOTTLE.name();
    }

    @Override
    public String getDescription() {
        return "With this bottle you can fill up your life by [" + lifeAdded +"]";
    }

    @Override
    public Answer processCommand(Command command) {
        return null;
    }
}
