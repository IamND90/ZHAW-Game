package productions.pa.zulugame.game.parser;

/**
 * Created by Andrey on 08.10.2015.
 */
public class HitWordFactory {

    enum ACTION_MOVING{
        GO,
        LEAVE,
        MOVE,
        WALK,
        RUN,
        TURN;

    }
    enum ACTION_ACTING{
        OPEN,
        CLOSE,
        TAKE,
        USE,
        GET
    }

    enum ACTION_POINTING{
        OPEN,
        CLOSE,
        TAKE,
        USE,
        GET
    }
    //HELP AND ADMIN COMMANDS
    public static final String HELP = "help";
    public static final String INFO = "info";
    public static final String START = "start";

    //MOVING
    public static final String GO = "go";
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
    public static final String AT = "at";
    public static final String TO = "to";
    public static final String FROM = "from";
    public static final String IN = "in";
    public static final String OUT = "out";
    public static final String OF = "of";
    public static final String BY = "by";
    public static final String TROUGH = "trough";
    public static final String FORWARD = "forward";
    public static final String BACK = "back";
    public static final String SIDE = "side";

    //PLECES
    public static final String ROOM = "room";
    public static final String CHAMBER = "chamber";
    public static final String DOOR = "door";
    public static final String WINDOW = "window";
    public static final String FLOOR = "floor";
    public static final String WALL = "wall";

    //ITEMS
    public static final String ITEMS = "items";
    public static final String ITEM = "item";

    public static final String HITWORDS_SUDO[] = {
            INFO,HELP,START
    };

    public static final String HITWORDS_MOVING[] = {
            GO,LEAVE,MOVE,WALK,RUN,TURN
    };

    public static final String HITWORDS_ACTING[] = {
            OPEN,CLOSE,TURN,TAKE,GET,USE,SHOW,BUY,EXCHANGE
    };

    public static final String HITWORDS_POINTERS[] = {
            AT,TO,FROM,IN,OUT,OF,BY,
            TROUGH,FORWARD,BACK,SIDE,
            THIS,THE
    };

    public static final String HITWORDS_PLACES[] = {
            //Big walktrough items
            ROOM,CHAMBER,DOOR,WINDOW,FLOOR,WALL
    };

    public static final String HITWORDS_ITEMS[] = {
        ITEM,ITEMS
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

        return new HitWord(word,HitWordType.NOT_FOUND);
    }
}
