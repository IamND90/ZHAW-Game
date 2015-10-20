package productions.pa.zulugame.game.parser;

import android.util.Log;

import productions.pa.zulugame.game.models.IModel;

/**
 * Created by Andrey on 08.10.2015.
 */
public class HitWord {

    private static final String TAG = "Hitword";
    //HELP AND ADMIN COMMANDS
    public static final String HELP = "help";
    public static final String INFO = "info";
    public static final String START = "start";
    public static final String NAME = "name";
    public static final String ANSWER = "answer";

    //MOVING
    public static final String GO = "go";
    public static final String GOTO = "goto";
    public static final String ENTER = "enter";
    public static final String RETURN = "return";

    //ACTING
    public static final String OPEN = "open";
    public static final String CLOSE = "close";
    public static final String TAKE = "take";
    public static final String DROP = "drop";
    public static final String REMOVE = "remove";
    public static final String USE = "use";
    public static final String GET = "get";
    public static final String SHOW = "show";
    public static final String STORE = "store";


    //POINTING
    public static final String ALL = "all";
    public static final String ME = "me";


    //PLECES
    public static final String ROOM = "room";
    public static final String DOOR = "door";
    public static final String BOX = "box";

    //ITEMS
    public static final String ITEMS = "items";
    public static final String ITEM = "item";
    public static final String KEY = "key";
    public static final String BACKPACK = "backpack";
    public static final String BACKPACK_SHORT = "bp";
    public static final String BOTTLE = "bottle";
    public static final String RIDDLE = "riddle";


    public static final String HITWORDS_SUDO[] = {
            INFO,HELP,START,ANSWER
    };

    public static final String HITWORDS_ACTING[] = {
            GO,ENTER,OPEN,CLOSE, RETURN,TAKE,
            GET,USE,SHOW,STORE,DROP,
            REMOVE
    };

    public static final String HITWORDS_POINTERS[] = {
            ALL,ME,ANSWER
    };

    public static final String HITWORDS_PLACES[] = {
            //Big walktrough items
            ROOM,DOOR
    };

    public static final String HITWORDS_ITEMS[] = {
            ITEM,ITEMS,BOX, KEY, BACKPACK,BACKPACK_SHORT, BOTTLE,RIDDLE
    };

    public static HitWord findHitWord(String word){

        Log.i(TAG,"Checking HITWORDS_SUDO:");
        for(String hitword: HITWORDS_SUDO){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword, TYPE.SUDO);
        }
        // Find the word in our library, most common start first
        Log.i(TAG,"Checking HITWORDS_ACTING:");
        for(String hitword: HITWORDS_ACTING){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword, TYPE.ACTING);
        }

        Log.i(TAG,"Checking HITWORDS_POINTERS:");
        for(String hitword: HITWORDS_POINTERS){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword, TYPE.POINTER);
        }

        Log.i(TAG,"Checking HITWORDS_COLORS:");
        for(IModel.COLOR hitword: IModel.COLOR.values()){
            if(hitword.name().equalsIgnoreCase(word)) return new HitWord(hitword.name(), TYPE.POINTER);
        }

        Log.i(TAG,"Checking HITWORDS_PLACES:");
        for(String hitword: HITWORDS_PLACES){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword, TYPE.PLACE);
        }

        Log.i(TAG,"Checking HITWORDS_ITEMS:");
        for(String hitword: HITWORDS_ITEMS){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword, TYPE.ITEM);
        }


        Log.i(TAG,"NOT_FOUND");
        return new HitWord(word, TYPE.NOT_FOUND);
    }


    // NOT STATIC PART
    private String inputWord;
    private TYPE type;

    public HitWord(String inputWord, TYPE type){
        this.inputWord = inputWord;
        this.type = type;
    }




    public String getString() {
        return inputWord == null ? "" : inputWord;
    }

    public TYPE getType() {
        return type;
    }
    public enum TYPE {
        SUDO,
        SETTINGS,

        ACTING,
        POINTER, // mostly for colors
        COLOR,
        ITEM,
        PLACE,

        UNKNOWN,
        NOT_FOUND,

        NUMBER,

        ANSWER
    }

}
