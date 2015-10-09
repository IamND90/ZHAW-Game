package productions.pa.zulugame.game.parser;

/**
 * Created by Andrey on 08.10.2015.
 */
public enum HitWordType {

    SUDO,

    MOVING,
    ACTING,
    POINTER, //Like "at", "to", "from"
    NUMBER,
    ITEM,
    PLACE,

    UNKNOWN,
    NOT_FOUND
}
