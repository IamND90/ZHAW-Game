package productions.pa.zulugame.game.models.quests;

import java.util.List;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.parser.Attribute;

/**
 * Created by Andrey on 15.10.2015.
 */
public abstract class Riddle implements IModel{

    private String name, description;
    private int riddleLevel = 0;

    //TODO attrubutes plus

    public Riddle(String name, String description, int level) {
        this.name = name;
        this.description = description;
        this.riddleLevel = level;
    }


    @Override
    public Answer processCommand(Command command) {
        if(command.getAttribute().getString().equals(getCorrectAnswer())){
            return new Answer("Thats right!", Answer.TYPE.SUCCESS);
        }

        return new Answer("Wrong!", Answer.TYPE.FAIL);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public int getRiddleLevel() {
        return riddleLevel;
    }

    public abstract String getCorrectAnswer();

    @Override
    public TYPE getType() {
        return TYPE.RIDDLE;
    }

    @Override
    public int getId() {
        return -1;
    }

    @Override
    public Attribute[] getAttributes() {
        return new Attribute[0];
    }

    @Override
    public List<IModel> getSubItems() {
        return null;
    }

    @Override
    public COLOR getColor() {
        return null;
    }

    @Override
    public void setColor(COLOR color) {

    }
}
