package productions.pa.zulugame.game.parser;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static productions.pa.zulugame.game.parser.HitWord.TYPE;

/**
 * Created by Andrey on 08.10.2015.
 * <p/>
 * This is the parser which makes a command out of a user input.
 * I Know it is not perfect, i don't need this ParsedInput class and so on but i don't have the time anymore to change this now (30h litmit)
 */
public class Parser {
    private static final String TAG = "Parser";

    final static List<HitWord> myHitwordsFound = new ArrayList<>();
    static String originalString;


    public static Command parseInputMessage(String message) {
        Log.i(TAG, "Message:<" + message + ">");
        myHitwordsFound.clear();
        originalString = message;
        //TODO Split by "," to execute mutiple commands (removed feature, that's why this ParsedInput class)
        //create the instance to return

        //  Checks if its a sudo command(look at hitwords), these commands are also processes when the game doesn't run
        String sudoSetting[] = message.split(":");
        if (sudoSetting.length == 2) {
            HitWord pointer = new HitWord(sudoSetting[0], TYPE.SETTINGS);
            HitWord attribute = new HitWord(sudoSetting[1], TYPE.SETTINGS);
            myHitwordsFound.add(pointer);
            myHitwordsFound.add(attribute);

            return createCommand();
        }

        // Split the input by TAB to get the single words
        // We assume, that we have no writing mistakes
        String words[] = message.split(" ");

        // Find and add all the word in our HitWordFactory
        // If the word if not found it will be added anyways, it as a type that is called NOT_FOUND for filtering later
        for (String word : words) {
            Log.i(TAG, "Search:<" + word + ">");
            HitWord foundHitword = HitWord.findHitWord(word);
            //Add to found array if it is a found word
            //if (!foundHitword.getType().equals(TYPE.NOT_FOUND))
                myHitwordsFound.add(foundHitword);
        }
        return createCommand();
    }

    public static Command createCommand() {

        // 0:command, 1: pointer, 2: attribute
        HitWord action = null;
        HitWord pointer = null;
        HitWord attribute = null;
        TYPE type = TYPE.UNKNOWN;

        //Search Command
        for (HitWord hitword : myHitwordsFound) {
            if (hitword.getType().equals(TYPE.SETTINGS) && myHitwordsFound.size() == 2) {
                return new Command(originalString, hitword.getType(), null, hitword, myHitwordsFound.get(1));
            }
        }


        //Search What
        for (HitWord hitword : myHitwordsFound) {
            if (hitword.getType().equals(TYPE.SUDO) || hitword.getType().equals(TYPE.ACTING)) {
                action = hitword;
                type = action.getType();
                break;
            }
        }
        //Search Pointer
        for (HitWord hitword : myHitwordsFound) {
            if (hitword.getType().equals(TYPE.POINTER)) {
                pointer = hitword;
                if (type.equals(TYPE.UNKNOWN)) type = pointer.getType();
                break;
            }
        }
        //Search Attribute
        for (HitWord hitword : myHitwordsFound) {
            if (hitword.getType().equals(TYPE.ITEM) || hitword.getType().equals(TYPE.PLACE) || hitword.getType().equals(TYPE.NUMBER)) {
                attribute = hitword;
                if (type.equals(TYPE.UNKNOWN)) type = attribute.getType();
                break;
            }

        }

        Command command = new Command(originalString, type, action, pointer, attribute);
        Log.w("Parser", command.getString());
        return command;
    }
}
