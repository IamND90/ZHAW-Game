package productions.pa.zulugame.game;

import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by Andrey on 08.10.2015.
 */
public class MessageFactory {

    public static final String MESSAGE_WELCOME_GAME = "Welcome!\nType 'start' to start a game.\nFor help type 'help'.\n"+
            "Attenetion: The command input in NOT case sensitive";
    public static final String MESSAGE_ENTER_START = "Plase enter 'start' to start a new game.\n";

    public static final String MESSAGE_BACKPACK_IS_EMPTY = "Your backpack is empty\n";
    public static final String MESSAGE_BACKPACK_DOES_NOT_HAS_ITEMS = "Your backpack does not has this item.\n";
    public static final String MESSAGE_CANNOT_INTERACT = "Your backpack is empty\n";

    public static final String MESSAGE_NO_KEY = "The door is close, you need to find a key first.\n";

    public static final String PLACEHOLDER_SUDO ="%arg1%";
    public static final String PLACEHOLDER_ACTIONS ="%arg2%";
    public static final String PLACEHOLDER_PLACES ="%arg3%";

    private static final String MESSAGE_HELP =
            "Here are some of the available commands:"+
            "\nGlobal help:\n"+
            PLACEHOLDER_SUDO+
            //"\nActions:\n"+
            //PLACEHOLDER_ACTIONS+
            "\nFind more by trying. Good luck!\n"+
            "To change your name, type for example:\nname:Sweet";
    public static final String DOOR_UNLOCKED = "The door has been unlocked";
    public static final String ENTER_THE_PLACE = "You entered the place";

    public static String getHelpMessage(){
        String message = MESSAGE_HELP;

        String actions = "[ ";
        for(String command: HitWord.HITWORDS_SUDO){
            actions+= (command + ",");
        }
        message = message.replace(PLACEHOLDER_SUDO,actions+ "]");

        /*
        actions = "[ ";
        for(String command:HitWordFactory.HITWORDS_ACTING){
            actions+= (command + ",");
        }
        message = message.replace(PLACEHOLDER_ACTIONS,actions+ "]");
        */

        return message;
    }


}
