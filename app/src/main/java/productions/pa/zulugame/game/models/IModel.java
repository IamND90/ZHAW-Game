package productions.pa.zulugame.game.models;

import java.util.List;
import java.util.Random;

import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.parser.Attribute;

/**
 * Created by Andrey on 09.10.2015.
 */
public interface IModel {


    String getName();
    String getDescription();
    TYPE getType();
    int getId();
    COLOR getColor();
    void setColor(COLOR color);

    Attribute[] getAttributes();
    List<IModel> getSubItems();
    /**
     * @return String that should be diplayed*/
    Answer processCommand(Command command);

    enum TYPE{
        UNKNOW,

        ITEM,
        PERSON,
        ROOM,
        DDOR,
        BOX,

        RIDDLE,
        KEY
    }

    enum COLOR{
        BLUE,
        GREEN,
        RED,
        YELLOW,
        GREY,
        BLACK,
        WHITE;

        public static COLOR getRandom(){

            int random = Math.abs(new Random().nextInt());
            random = random%COLOR.values().length;

            return COLOR.values()[random];
        }
    }
}
