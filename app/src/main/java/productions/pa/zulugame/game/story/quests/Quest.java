package productions.pa.zulugame.game.story.quests;

import productions.pa.zulugame.game.models.IModel;

/**
 * Created by Andrey on 15.10.2015.
 */
public abstract class Quest implements IModel{

    private String name, description;

    public Quest(String name, String description) {
        this.name = name;
        this.description = description;
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

}
