package productions.pa.zulugame.game.models;

import android.util.Log;

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
        KEY,
        LIFEBOTTLE
    }

    enum COLOR{
        BLUE,
        GREEN,
        RED,
        YELLOW,
        GREY,
        BLACK,
        PURPLE,
        ORANGE,
        WHITE;

        public static COLOR getRandom(){

            int random = Math.abs(new Random().nextInt());
            random = random%COLOR.values().length;
            COLOR color = COLOR.values()[random];

            Log.i("IModel" , "Color set " + color.name());

            return color;
        }

        public static COLOR getRandomExclude(COLOR... exclude){

            int random = Math.abs(new Random().nextInt());
            random = random%COLOR.values().length;
            COLOR color = COLOR.values()[random];

            for(COLOR excl: exclude){
                if(excl.equals(color))return getRandomExclude(exclude);
            }

            Log.i("IModel" , "Color set " + color.name());

            return color;
        }

        public static COLOR getRandomMust(COLOR... include){

            int random = Math.abs(new Random().nextInt());
            random = random%COLOR.values().length;
            COLOR color = COLOR.values()[random];

            for(COLOR incl: include){
                if(incl.equals(color))return color;
            }

            Log.i("IModel" , "Color set " + color.name());

            return color;
        }
    }
}
