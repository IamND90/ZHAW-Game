package productions.pa.zulugame.game;

import java.util.List;

import productions.pa.zulugame.android.Debugger;
import productions.pa.zulugame.android.InputCallback;
import productions.pa.zulugame.android.MessageCallback;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.parser.ParsedInput;
import productions.pa.zulugame.game.parser.Parser;

/**
 * Created by Andrey on 08.10.2015.
 */
public class Game implements InputCallback {

    MessageCallback messageCallback;





    public Game(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;

        //Let the user know, that the game is ready to start
        messageCallback.onMessageReceived(MessageCallback.RESULT_CODE_OK, MessageFactory.MESSAGE_WELCOME_GAME);
    }


    @Override
    public void onInputString(String input) {
        ParsedInput commandArray[] = Parser.parseInputMessage(input);

        for(ParsedInput command : commandArray) {
            List<HitWord> myParsedCommands = command.getRecognizedWordArray();

            if (myParsedCommands.size() == 0) {
                messageCallback.onMessageReceived(
                        MessageCallback.RESULT_CODE_PARSER_FAIL,
                        "Message completely not understandable:\n[" + command.getOriginalString() + "]");
                //Stop executing
                return;
            }

            //TODO now interprete the message
            String debugString ="Found words: ";
            for(HitWord word: myParsedCommands) debugString += ("," + word.getInputWord());
            Debugger.get().append(debugString );

        }
    }


}
