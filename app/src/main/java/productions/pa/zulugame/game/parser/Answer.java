package productions.pa.zulugame.game.parser;

/**
 * Created by IamND on 09.10.2015.
 * <p/>
 * This class is the return object of a processed command.
 * it contains the output message and the decoration-type of the text on the UI
 */
public class Answer {

    /**
     * @param message processed to output
     * @param decorationType will be applied @Printer
     */
    String message;

    DECORATION decorationType;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public Answer(String message, DECORATION type) {
        this.message = message;
        decorationType = type;
    }

    public Answer(String message) {
        this.message = message;
        decorationType = DECORATION.SIMPLE;
    }

    /**
     * Append multiple answers to one message
     */
    public Answer addMessage(String message) {
        this.message += ("\n" + message);
        return this;
    }

    //  ============================================================
    //  GETTERS / FINDERS
    //  ============================================================

    public String getMessage() {
        return message;
    }


    public DECORATION getDecorationType() {
        return decorationType;
    }


    /**
     * Just some constants to define the decoration in the UI, see @Printer
     */


    public enum DECORATION {
        SIMPLE,
        ROOM_DESCRIPTION,
        RIDDLE,
        SETTINGS,
        STATUS_CHANGE,
        BOX_ITEMS,
        ADDING,
        REMOVING,
        DESCRIPTION,

        FAIL,
        ERROR,

        PLAYER_MESSAGE_ECHO

    }
}
