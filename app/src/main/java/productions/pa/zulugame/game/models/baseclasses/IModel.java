package productions.pa.zulugame.game.models.baseclasses;

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
     * @return name ,color and maybe some other attributes from childclasses*/
    String getShortdescription();

    /**
     * @return type of the model like: Key, Bottle (not-abstract classes)*/
    TYPE getType();
    /**
     * @return group of the model like: Item,Place (abstract classes), the group can be also the same like the type*/
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
     * Add the model and sets its parentIndex*/
    boolean addModel(IModel item);

    /**
     * Searches iteratively the item of the given id*/
    boolean findById(int Id);
    /**
     * Removes iteratively the item of the given id*/
    boolean removeItemById(int Id);
    /**
     * @return String that should be diplayed*/
    Answer processCommand(Command command);


    //  ------------------------------------------------------------
    //  STATIC FINAL FIELDS ( Constants for game , experimentally set)
    //  ------------------------------------------------------------

    int NOT_A_NUMBER= -23423;

    int MAXIMUM_LIFE_PERSON = 86;
    int START_LIFE_PERSON = 55;

    int MAXIMUM_LIFE_BOTTLE = 21;
    int MINIMUM_LIFE_BOTTLE = 8;

    int SPACE_BACPACK = 55;
    int SPACE_KEY = 8;
    int SPACE_RIDDLE = 13;
    int SPACE_BOTTLE = 21;

    int LIFE_USED_MOVE_BETWEEN_ROOMS = -8;
    int LIFE_USED_OPEN_BOX = -5;

    int RIDDLE_DIFFICULTY_1= 5;
    int RIDDLE_DIFFICULTY_2= 8;
    int RIDDLE_DIFFICULTY_3= 13;
    int RIDDLE_DIFFICULTY_4= 21;
    int RIDDLE_DIFFICULTY_5= 34;

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
        ORANGE
        ,PURPLE;

        public static COLOR getRandom(){
            return getRandom(COLOR.values());
        }

        public static COLOR getRandomExclude(COLOR... exclude){

            if(exclude.length >= COLOR.values().length){
                Log.e("IModel","Trying to create more colors than you have!");
                return  getRandom();
            }

            for(COLOR color : COLOR.values()){
                boolean isFree;
                for(COLOR excl : exclude){
                    isFree = false;
                    if(!excl.equals(color)){
                        isFree = true;
                    }
                    if(isFree) return color;
                }

            }

            return COLOR.RED;
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

        public static COLOR fromString(String doorColor) {

            for(COLOR color: COLOR.values())if(color.name().equalsIgnoreCase(doorColor))return color;
            return RED;
        }
    }



}
