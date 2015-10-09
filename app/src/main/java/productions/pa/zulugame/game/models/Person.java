package productions.pa.zulugame.game.models;

import android.text.TextUtils;

import productions.pa.zulugame.game.commands.Command;

/**
 * Created by IamND on 09.10.2015.
 */
public class Person extends AbstractModel {

    static final String DESCRIPTION = "Person";
    String mName;

    public Person(String name) {
        super(TYPE.PERSON);
        mName = name;

        subModels.add(new Backpack());
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String executeCommand(Command command) {
        String answer = null;



        return checkSubModels(command);
    }


}
