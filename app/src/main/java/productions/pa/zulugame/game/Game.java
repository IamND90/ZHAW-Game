package productions.pa.zulugame.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import productions.pa.zulugame.android.InputCallback;
import productions.pa.zulugame.android.UIHandler;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.parser.DialogFactory;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.parser.HitWordFactory;
import productions.pa.zulugame.game.parser.HitWordType;
import productions.pa.zulugame.game.parser.ParsedInput;
import productions.pa.zulugame.game.parser.Parser;
import productions.pa.zulugame.game.story.PlaceManager;
import productions.pa.zulugame.game.models.places.AbstractStoryPlace;

/**
 * Created by Andrey on 08.10.2015.
 */
public class Game implements InputCallback {

    static Game mThis;
    static UIHandler messageCallback;

    PlaceManager mStoryTree;

    public Game(UIHandler messageCallback) {
        this.messageCallback = messageCallback;

        //Let the user know, that the game is ready to start
        messageCallback.onMessageReceived(MessageFactory.MESSAGE_WELCOME_GAME);
    }

    public static SharedPreferences getSharedPrefs() {
        return ((Activity)messageCallback).getSharedPreferences("sp",Context.MODE_PRIVATE);
    }


    @Override
    public void onInputString(String input) {
        ParsedInput parsedInput = Parser.parseInputMessage(input);

        List<HitWord> myParsedCommands = parsedInput.getAllHitwordsFound();

        //check if we have understood anything
        if (myParsedCommands.size() == 0) {
            messageCallback.onErrorReceived(
                    "Message completely not understandable:\n[" + parsedInput.getOriginalString() + "]");
            //Stop executing
            return;
        }

        //Get the command out of the parsed input
        Command command = parsedInput.createCommand();

        // Check whether the command is defined or not
        if(command.getType().equals(HitWordType.UNKNOWN)){
            messageCallback.onErrorReceived(
                    "The command is not known: [" + parsedInput.getOriginalString() + "]");
            //Stop executing
            return;
        }

        processCommand(command);

    }

    private void processCommand(Command command) {

        //Process a help or info command
        if (command.getType().equals(HitWordType.SUDO)) {
            switch (command.getAction().getName()) {
                case HitWordFactory.INFO:
                    messageCallback.clearScreen();
                    messageCallback.onMessageReceived(getCurrentInfo());
                    break;
                case HitWordFactory.HELP:
                    //this functions creates a pop-up dialog and show the help message
                    DialogFactory.createDialog((Context) messageCallback, DialogFactory.TITLE_HELP, MessageFactory.getHelpMessage());
                    messageCallback.onMessageReceived("");
                    break;
                case HitWordFactory.START:
                    startGame();
                    break;
            }
            return;
        }

        if(mStoryTree == null){
            messageCallback.onMessageReceived(MessageFactory.MESSAGE_ENTER_START);
            return;
        }

        AbstractStoryPlace place = mStoryTree.getCurrentPlace();

        if(place != null) {
            place.executeCommand(command);
            //dummy
            messageCallback.onMessageReceived("Some action :" + command.getString());
        }
    }

    private void startGame() {
        //TODO
        mStoryTree = PlaceManager.get();
        messageCallback.clearScreen();
        messageCallback.onMessageReceived( mStoryTree.getCurrentPlace().getStory());
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
