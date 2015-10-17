package productions.pa.zulugame.game.parser;

import productions.pa.zulugame.game.models.IModel;

/**
 * Created by Andrey on 08.10.2015.
 */
public class HitWord {

    //HELP AND ADMIN COMMANDS
    public static final String HELP = "help";
    public static final String INFO = "info";
    public static final String START = "start";
    public static final String NAME = "name";
    public static final String ANSWER = "answer";

    //MOVING
    public static final String GO = "go";
    public static final String ENTER = "enter";
    public static final String LEAVE = "leave";
    public static final String MOVE = "move";
    public static final String WALK = "walk";
    public static final String RUN = "run";
    public static final String TURN = "turn";

    //ACTING
    public static final String OPEN = "open";
    public static final String CLOSE = "close";
    public static final String TAKE = "take";
    public static final String USE = "use";
    public static final String GET = "get";
    public static final String SHOW = "show";
    public static final String BUY = "buy";
    public static final String EXCHANGE = "exchange";

    //POINTING
    public static final String THIS = "this";
    public static final String THE = "the";
    public static final String ALL = "ALL";
    public static final String ME = "me";




    //PLECES
    public static final String ROOM = "room";
    public static final String CHAMBER = "chamber";
    public static final String DOOR = "door";
    public static final String WINDOW = "window";
    public static final String FLOOR = "floor";
    public static final String WALL = "wall";
    public static final String BOX = "box";

    //ITEMS
    public static final String ITEMS = "items";
    public static final String ITEM = "item";
    public static final String ITEM_KEY = "KEY";
    public static final String ITEM_BACKACK = "BACKPACK";


    public static final String HITWORDS_SUDO[] = {
            INFO,HELP,START
    };

    public static final String HITWORDS_MOVING[] = {
            GO,LEAVE,MOVE,WALK,RUN,TURN,ENTER
    };

    public static final String HITWORDS_ACTING[] = {
            OPEN,CLOSE,TURN,TAKE,GET,USE,SHOW,BUY,EXCHANGE
    };

    public static final String HITWORDS_POINTERS[] = {
            ALL,THIS,THE,ME
    };

    public static final String HITWORDS_PLACES[] = {
            //Big walktrough items
            ROOM,CHAMBER,DOOR,WINDOW,FLOOR,WALL
    };

    public static final String HITWORDS_ITEMS[] = {
            ITEM,ITEMS,BOX,ITEM_KEY,ITEM_BACKACK
    };

    public static HitWord findHitWord(String word){

        // Chekc if it is and help or info word
        for(String hitword: HITWORDS_SUDO){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword,HitWordType.SUDO);
        }

        //Chekc if it is a number
        if(word.length() <4) {
            boolean isNumber = false;
            int number = 0;
            try {
                number = Integer.parseInt(word);
                isNumber = true;
            } catch (Exception e) {
            }

            if (isNumber) return new HitWord(number);
        }

        // Find the word in our library, most common start first
        for(String hitword: HITWORDS_MOVING){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword,HitWordType.MOVING);
        }
        for(String hitword: HITWORDS_ACTING){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword,HitWordType.ACTING);
        }

        for(String hitword: HITWORDS_POINTERS){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword,HitWordType.POINTER);
        }

        for(String hitword: HITWORDS_PLACES){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword,HitWordType.PLACE);
        }
        for(String hitword: HITWORDS_ITEMS){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword,HitWordType.ITEM);
        }

        for(IModel.COLOR hitword: IModel.COLOR.values()){
            if(hitword.name().equalsIgnoreCase(word)) return new HitWord(hitword.name(),HitWordType.POINTER);
        }

        return new HitWord(word,HitWordType.NOT_FOUND);
    }

    public String isAction(String value){
        for(String hitword: HITWORDS_MOVING){
            if(hitword.equalsIgnoreCase(value)) return value;
        }
        for(String hitword: HITWORDS_ACTING){
            if(hitword.equalsIgnoreCase(value)) return value;
        }
        return null;
    }
    public String isPointer(String value){

        for(String hitword: HITWORDS_POINTERS){
            if(hitword.equalsIgnoreCase(value))return value;
        }
        for(IModel.COLOR hitword: IModel.COLOR.values()){
            if(hitword.name().equalsIgnoreCase(value)) return value;
        }
        return null;
    }
    public String isAttribute(String value){
        for(String hitword: HITWORDS_PLACES){
            if(hitword.equalsIgnoreCase(value))return value;
        }
        for(String hitword: HITWORDS_ITEMS){
            if(hitword.equalsIgnoreCase(value))return value;
        }
        return null;
    }


    // NOT STATIC PART
    private int number =0;
    private String inputWord;
    private HitWordType type;

    public HitWord(String inputWord, HitWordType type){
        this.inputWord = inputWord;
        this.type = type;
    }

    public HitWord(String inputWord, HitWordType type,int inputNumber){
        this.inputWord = inputWord;
        number = inputNumber;
        this.type = type;
    }


    public HitWord(int inputNumber){
        inputWord = ""+number;
        number = inputNumber;
        this.type = HitWordType.NUMBER;
    }

    public int getNumber() {
        return number;
    }

    public String getString() {
        return inputWord == null ? "" : inputWord;
    }

    public HitWordType getType() {
        return type;
    }
}
