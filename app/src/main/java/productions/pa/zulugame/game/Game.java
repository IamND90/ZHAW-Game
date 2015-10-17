package productions.pa.zulugame.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.android.InputCallback;
import productions.pa.zulugame.android.UIHandler;
import productions.pa.zulugame.game.commands.Answer;
import productions.pa.zulugame.game.commands.Command;
import productions.pa.zulugame.game.models.places.APlace;
import productions.pa.zulugame.game.parser.DialogFactory;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.parser.HitWordType;
import productions.pa.zulugame.game.parser.ParsedInput;
import productions.pa.zulugame.game.parser.Parser;
import productions.pa.zulugame.game.story.PersonManager;
import productions.pa.zulugame.game.story.RoomManager;
import productions.pa.zulugame.game.story.RiddleManager;

/**
 * Created by Andrey on 08.10.2015.
 */
public class Game implements InputCallback {


    static UIHandler messageCallback;

    Gamestatus status = Gamestatus.NOT_STARTED;


    ArrayList<String> list = new ArrayList<>();

    public Game(UIHandler messageCallback) {
        this.messageCallback = messageCallback;


        //Let the user know, that the game is ready to start
        messageCallback.onMessageReceived(MessageFactory.MESSAGE_WELCOME_GAME);
    }

    public static SharedPreferences getSharedPrefs() {
        return ((Activity) messageCallback).getSharedPreferences("sp", Context.MODE_PRIVATE);
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
        if (command.getType().equals(HitWordType.UNKNOWN)) {
            messageCallback.onErrorReceived(
                    "The command is not known: [" + parsedInput.getOriginalString() + "]");
            //Stop executing
            return;
        }

        if(command.getType().equals(HitWordType.ANSWER)){
            Answer answer = RiddleManager.get().processAnswer(command);
            if(answer.getAnswerTypes()[0].equals(Answer.TYPE.ITEM_NOT_FOUND)) {
                processCommand(command);
            }
        }

        processCommand(command);

    }


    private void processCommand(Command command) {

        //Process a help or info command
        if(processSudoCommand(command))return;

        if (status.equals(Gamestatus.NOT_STARTED)) {
            messageCallback.onMessageReceived(MessageFactory.MESSAGE_ENTER_START);
            return;
        }

        APlace place = RoomManager.get().getCurrentPlace();

        Answer answer = place.processCommand(command);

        if(answer == null ){
            messageCallback.onErrorReceived("Action not found: " + command.getString());
            return;
        }
        if(answer.getAnswerTypes()[0] == Answer.TYPE.FAIL && TextUtils.isEmpty(answer.getMessage())){
            processFail(answer);
        }

        if(processPersonCommand(command))return;
        processOutput(answer);

    }

    private boolean processSudoCommand(Command command) {
        switch (command.getType()) {
            case SETTINGS:
                if (command.getPointer() != null) {
                    switch (command.getPointer().getString()){
                        case HitWord.NAME:
                            if(command.getAttribute() != null) {
                                PersonManager.get().saveUserName(command.getAttribute().getString());
                                messageCallback.onMessageReceived("Your name has been changed to " + command.getAttribute().getString());
                                return true;
                            }
                            break;

                    }
                }
                break;

            case SUDO:
                if (command.getAction() != null) {
                    switch (command.getAction().getString()) {
                        case HitWord.INFO:
                            messageCallback.clearScreen();
                            messageCallback.onMessageReceived(getCurrentInfo());
                            return true;
                        case HitWord.HELP:
                            //this functions creates a pop-up dialog and show the help message
                            DialogFactory.createDialog((Context) messageCallback, DialogFactory.TITLE_HELP, MessageFactory.getHelpMessage());
                            messageCallback.onMessageReceived("");
                            return true;
                        case HitWord.START:
                            startGame();
                            return true;
                    }
                }
                break;

        }
        return false;
    }

    private boolean processPersonCommand(Command command) {

        String attribute = command.getAttribute().getString();
        if(command.getAction().getString().equalsIgnoreCase(HitWord.OPEN) || command.getAction().getString().equalsIgnoreCase(HitWord.SHOW)) {
            // BACKPACK
            if (attribute.equalsIgnoreCase(HitWord.ITEM_BACKACK)) {
                messageCallback.onMessageReceived(PersonManager.get().getPerson().getBackpack().getDescription());
                return true;
            }
            if (command.getPointer().getString().equalsIgnoreCase(HitWord.ME)) {
                messageCallback.onMessageReceived(PersonManager.get().getPerson().getDescription());
                return true;
            }
        }
        return false;
    }

    private void processFail(Answer answer) {

        if(answer.getAnswerTypes().length>1){
            messageCallback.onMessageReceived(answer.getMessage());
        }

        messageCallback.onErrorReceived("Could not interact with the command");
    }

    private boolean processOutput(Answer answer) {
        if(answer == null){
            messageCallback.onErrorReceived("Answer is null");
            return false;
        }
        for(Answer.TYPE commands: answer.getAnswerTypes()){
            switch (commands){
                case SIMPLE_OUTPUT:
                    messageCallback.onMessageReceived(answer.getMessage());
                    return true;

                case MOVE_TO_PLACE:
                    if(RoomManager.get().moveAtPlace(answer.getContextId())){
                        messageCallback.onMessageReceived(RoomManager.get().getCurrentPlace().getStory());
                        return true;
                    }
            }
        }

        return false;
    }

    private void startGame() {
        //TODO
        status = Gamestatus.RUNNING;
        messageCallback.clearScreen();
        messageCallback.onMessageReceived(RoomManager.get().getCurrentPlace().getStory());
    }


    public String getCurrentInfo() {
        //TODO
        if (status == null || status.equals(Gamestatus.NOT_STARTED)) return MessageFactory.MESSAGE_ENTER_START;
        APlace place = RoomManager.get().getCurrentPlace();
        if (place != null) {
            return place.getDescription();
        }

        return "no info";
    }

    enum Gamestatus{
        NOT_STARTED,
        RUNNING,
        PAUSE,
        OVER
    }
}
