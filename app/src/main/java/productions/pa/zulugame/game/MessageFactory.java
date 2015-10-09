package productions.pa.zulugame.game;

import productions.pa.zulugame.game.parser.HitWordFactory;

/**
 * Created by Andrey on 08.10.2015.
 */
public class MessageFactory {

    public static final String MESSAGE_WELCOME_GAME = "Welcome!\nType 'start to start a game'.\nFor help type 'help'.\nAttenetion: The command input in NOT case sensitive";
    public static final String MESSAGE_ENTER_START = "Plase enter 'start' to start a new game.\n";

    public static final String PLACEHOLDER_SUDO ="%arg1%";
    public static final String PLACEHOLDER_ACTIONS ="%arg2%";
    public static final String PLACEHOLDER_PLACES ="%arg3%";

    private static final String MESSAGE_HELP = "Welcome to the help menu.\n"+
            "Here are all the available commands:"+
            "\nGlobal help:\n"+
            PLACEHOLDER_SUDO+
            "\nActions:\n"+
            PLACEHOLDER_ACTIONS;

    public static String getHelpMessage(){
        String message = MESSAGE_HELP;

        String actions = "[ ";
        for(String command: HitWordFactory.HITWORDS_SUDO){
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
