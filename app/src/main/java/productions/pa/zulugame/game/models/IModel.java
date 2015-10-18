package productions.pa.zulugame.game.models;

import android.util.Log;

import java.util.List;
import java.util.Random;

import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;

/**
 * Created by Andrey on 09.10.2015.
 */
public interface IModel {
    /**
     * @return unique id of the model*/
    int getId();

    /**
     * @return name of the model*/
    String getName();

    /**
     * @return description of the model: appends on the type of model what description will appear*/
    String getDescription();

    /**
     * @return type of the model like: Key, Bottle (not-abstract classes)*/
    TYPE getType();
    /**
     * @return name of the model like: Item,Place (abstract classes), the group can be also the same like the type*/
    TYPE getGroup();

    /**
     * @return color of the model*/
    COLOR getColor();
    /**
     * @param color can set the color of the model*/
    void setColor(COLOR color);

    /**
     * @return list of all models inside this model, can not be null, only empty*/
    List<IModel> getSubItems();

    /**
     * Removes iteratively the item of the givem id*/
    boolean removeItemById(int Id);
    /**
     * @return String that should be diplayed*/
    Answer processCommand(Command command);


    //  ------------------------------------------------------------
    //  STATIC FINAL FIELDS ( Constants for game , experimentally set)
    //  ------------------------------------------------------------

    int MAXIMUM_LIFE_PERSON = 100;
    int START_LIFE_PERSON = 64;

    int MAXIMUM_LIFE_BOTTLE = 54;
    int MINIMUM_LIFE_BOTTLE = 12;

    int SPACE_BACPACK = 100;
    int SPACE_KEY = 14;
    int SPACE_RIDDLE = 23;
    int SPACE_BOTTLE = 44;

    int LIFE_USED_MOVE_BETWEEN_ROOMS = -8;
    int LIFE_USED_OPEN_BOX = -3;

    int RIDDLE_DIFFICULTY_1= 12;
    int RIDDLE_DIFFICULTY_2= 21;
    int RIDDLE_DIFFICULTY_3= 37;
    int RIDDLE_DIFFICULTY_4= 56;
    int RIDDLE_DIFFICULTY_5= 71;

    /**
     * The type and/or group of the models*/

    enum TYPE {
        //  Groups:
        ITEM,
        PLACE,
        STORAGE,

        //  Groups+Types:
        PERSON,
        RIDDLE,

        //  Types:
        ROOM,
        DOOR,
        BOX,
        KEY,
        BOTTLE
    }

    /**
     * The colors of the models*/

    enum COLOR{
        BLUE,
        GREEN,
        RED,
        YELLOW,
        ORANGE;

        public static COLOR getRandom(){
            return getRandom(COLOR.values());
        }

        public static COLOR getRandomExclude(COLOR... exclude){

            if(exclude.length >= COLOR.values().length){
                Log.e("IModel","Trying to create more colors that you have!");
                return  getRandom();
            }

            COLOR list[] = new COLOR[COLOR.values().length-exclude.length];

            boolean hasColor;
            int listIndex = 0;
            for(int i =0 ; i < COLOR.values().length; i++){
                hasColor = false;
                list[listIndex] = ORANGE;
                for(COLOR color: exclude){
                    if(color.equals(COLOR.values()[i])){
                        hasColor = true;
                        break;
                    }
                }
                if(!hasColor)list[listIndex++] = COLOR.values()[i];
            }

            return getRandom(list);
        }

        private static COLOR getRandom(COLOR[] values){
            if(values.length <= 0){
                Log.e("IModel","Trying to create more colors that you have!");
                return  COLOR.RED;
            }
            int random = Math.abs(new Random().nextInt());
            random = random%values.length;
            return  values[random];
        }

    }



}
