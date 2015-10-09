package productions.pa.zulugame.game;

import android.content.Context;

import java.util.List;

import productions.pa.zulugame.android.InputCallback;
import productions.pa.zulugame.android.MessageCallback;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.parser.DialogFactory;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.parser.HitWordType;
import productions.pa.zulugame.game.parser.ParsedInput;
import productions.pa.zulugame.game.parser.Parser;
import productions.pa.zulugame.game.story.StoryTree;
import productions.pa.zulugame.game.models.places.AbstractStoryPlace;

/**
 * Created by Andrey on 08.10.2015.
 */
public class Game implements InputCallback {

    MessageCallback messageCallback;

    StoryTree mStoryTree;

    public Game(MessageCallback messageCallback) {
        this.messageCallback = messageCallback;

        //Let the user know, that the game is ready to start
        messageCallback.onMessageReceived(MessageCallback.RESULT_CODE_OK, MessageFactory.MESSAGE_WELCOME_GAME);
    }


    @Override
    public void onInputString(String input) {
        ParsedInput commandArray[] = Parser.parseInputMessage(input);

        for(ParsedInput parseInput : commandArray) {
            List<HitWord> myParsedCommands = parseInput.getAllHitwordsFound();

            //check if we have understood anything
            if (myParsedCommands.size() == 0) {
                messageCallback.onMessageReceived(
                        MessageCallback.RESULT_CODE_PARSER_FAIL,
                        "Message completely not understandable:\n[" + parseInput.getOriginalString() + "]");
                //Stop executing
                return;
            }

            //Get the command out of the parsed input
            Command command = parseInput.createCommand();

            // Check whether the command is defined or not
            if(command.getType().equals(HitWordType.UNKNOWN)){
                messageCallback.onMessageReceived(
                        MessageCallback.RESULT_CODE_PARSER_FAIL,
                        "The command is not known: [" + parseInput.getOriginalString() + "]");
                //Stop executing
                return;
            }

            processCommand(command);
        }
    }

    private void processCommand(Command command) {

        //Process a help or info command
        if (command.getType().equals(HitWordType.SUDO)) {
            switch (command.getAction().getInputWord()) {
                case HitWordFactory.INFO:
                    messageCallback.clearScreen();
                    messageCallback.onMessageReceived(MessageCallback.RESULT_CODE_OK,getCurrentInfo());
                    break;
                case HitWordFactory.HELP:
                    //this functions creates a pop-up dialog and show the help message
                    DialogFactory.createDialog((Context) messageCallback, DialogFactory.TITLE_HELP, MessageFactory.getHelpMessage());
                    break;
                case HitWordFactory.START:
                    startGame();
                    break;
            }
            return;
        }

        if(mStoryTree == null){
            messageCallback.onMessageReceived(MessageCallback.RESULT_CODE_OK, MessageFactory.MESSAGE_ENTER_START);
            return;
        }

        AbstractStoryPlace place = mStoryTree.getCurrentPlace();

        if(place != null) {
            place.executeCommand(command);
            //dummy
            messageCallback.onMessageReceived(MessageCallback.RESULT_CODE_OK,"Some action :" + command.getString());
        }
    }

    private void startGame() {
        //TODO
        mStoryTree = StoryTree.get();
        messageCallback.clearScreen();
        messageCallback.onMessageReceived(MessageCallback.RESULT_CODE_OK, mStoryTree.getCurrentPlace().getStory());
    }


    public String getCurrentInfo() {
        //TODO
        if(mStoryTree == null)return MessageFactory.MESSAGE_ENTER_START;
        AbstractStoryPlace place = mStoryTree.getCurrentPlace();
        if(place != null){
            return place.getDescription();
        }

        return "no info";
    }
}
