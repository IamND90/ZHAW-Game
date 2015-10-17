package productions.pa.zulugame.game.models;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.items.Backpack;
import productions.pa.zulugame.game.models.items.Item;
import productions.pa.zulugame.game.story.ModelManager;

/**
 * Created by IamND on 09.10.2015.
 */
public class Person extends AModel {


    String mName;
    int lifeLeft = 1000;

    public Person(String name) {
        super(ModelManager.ID_MYPERSON,TYPE.PERSON);
        mName = name;

        subItems.add(new Backpack());
    }

    @Override
    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Override
    public String getDescription() {

        String info = getName() +", have " + lifeLeft + " life left.\n";
        info += getBackpack().getDescription();

        return info;
    }

    @Override
    public Answer processCommand(Command command) {
        Answer answer = null;



        return answer;
    }

    public Backpack getBackpack(){
        return (Backpack) subItems.get(0);
    }

    public void appendLife(int value) {
        lifeLeft += value;
    }
}
