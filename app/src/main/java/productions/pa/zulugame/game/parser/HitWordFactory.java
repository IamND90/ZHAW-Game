package productions.pa.zulugame.game.parser;

/**
 * Created by Andrey on 08.10.2015.
 */
public class HitWordFactory {

    public static final String GO = "go";
    public static final String MOVE = "move";
    public static final String WALK = "walk";
    public static final String RUN = "run";
    public static final String TURN = "turn";

    public static final String OPEN = "go";
    public static final String CLOSE = "close";
    public static final String TAKE = "take";
    public static final String USE = "use";
    public static final String GET = "get";

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

    public static final String ROOM = "room";
    public static final String CHAMBER = "chamber";
    public static final String DOOR = "door";
    public static final String WINDOW = "window";
    public static final String FLOOR = "floor";
    public static final String WALL = "wall";

    private static final String HITWORDS_MOVING[] = {
            GO,MOVE,WALK,RUN,TURN
    };

    private static final String HITWORDS_ACTING[] = {
            OPEN,CLOSE,TURN,TAKE,GET,USE
    };

    private static final String HITWORDS_POINTERS[] = {
            AT,TO,FROM,IN,OUT,OF,BY,
            TROUGH,FORWARD,BACK,SIDE
    };

    private static final String HITWORDS_ITEMS[] = {
            //Big walktrough items
            ROOM,CHAMBER,DOOR,WINDOW,FLOOR,WALL
    };

    public static HitWord findHitWord(String word){

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

        for(String hitword: HITWORDS_ITEMS){
            if(hitword.equalsIgnoreCase(word)) return new HitWord(hitword,HitWordType.ITEM);
        }


        return new HitWord(word,HitWordType.NOT_FOUND);
    }
}
