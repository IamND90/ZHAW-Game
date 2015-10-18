package productions.pa.zulugame.game;

import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by Andrey on 08.10.2015.
 *
 * Actually bad idea for this short project, but will be filled up and used better later maybe..
 */
public class MessageFactory {

    public static final String MESSAGE_WELCOME_GAME = "Welcome!\nType 'start' to start a game.\nFor help type 'help'.\n"+
            "The input in NOT case sensitive";
    public static final String MESSAGE_ENTER_START = "Plase enter 'start' to start a new game.\n";

    public static final String MESSAGE_BACKPACK_IS_EMPTY = "Your backpack is empty\n";

    public static final String MESSAGE_NO_KEY = "The door is close, you need to find a key first.\n";
    public static final String GAME_OVER = "!!!GAME OVER!!!.\n";


    public static final String PLACEHOLDER_SUDO ="%arg1%";


    private static final String MESSAGE_HELP =
            "Here are some of the available commands:"+
            "\nGlobal help:\n"+
            PLACEHOLDER_SUDO+

            "\nFind more by trying. Good luck!\n"+
            "To change your name, type for example:\nname:Sweet";

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
