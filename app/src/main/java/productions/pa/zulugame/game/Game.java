package productions.pa.zulugame.game;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import productions.pa.zulugame.android.InputCallback;
import productions.pa.zulugame.android.UIHandler;
import productions.pa.zulugame.game.manager.ContextManager;
import productions.pa.zulugame.game.manager.PersonManager;
import productions.pa.zulugame.game.manager.RiddleManager;
import productions.pa.zulugame.game.manager.RoomManager;
import productions.pa.zulugame.game.models.baseclasses.APlace;
import productions.pa.zulugame.game.parser.Answer;
import productions.pa.zulugame.game.parser.Command;
import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.parser.Parser;

/**
 * Created by Andrey on 08.10.2015.
 *
 * Main class of the game.
 * Main function: processing command and returning the answer to the UI
 *
 */
public class Game implements InputCallback {

    //  ------------------------------------------------------------
    //  STATIC FINAL FIELDS
    //  ------------------------------------------------------------

    static final String TAG = "Game";
    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------
    /**
     * @param mThis singleton of the Game to be accessible from anywhere
     * @param myPrinter the Handler to process the Text to the UI
     * @param status the current game-status
     * */
    static Game mThis;
    UIHandler myPrinter;
    GameStatus status = GameStatus.NOT_STARTED;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    /**
     * Must be called first to init UI callback*/
    public Game(UIHandler printer) {
        mThis = this;
        this.myPrinter = printer;
        //Let the user know, that the game is ready to start
        pushAnswer(new Answer(MessageFactory.MESSAGE_WELCOME_GAME, Answer.DECORATION.STATUS_CHANGE));
    }

    public static Game getInstance() {
        return mThis;
    }

    //  ============================================================
    //  @Override METHODS
    //  ============================================================

    @Override
    public void onInputString(String input) {
        //  Show what user was written on the UI
        myPrinter.onMessageReceived(input, Answer.DECORATION.PLAYER_MESSAGE_REPEAT);

        //Get the command out of the parsed input
        Command command = Parser.parseInputMessage(input);

        // Check whether the commands are defined or not
        if (command.getType().equals(HitWord.TYPE.UNKNOWN)) {
            pushAnswer(new Answer("The command is not known: [" + command.getTypedCommand() + "]", Answer.DECORATION.ERROR));
            //Stop executing
            return;
        }
        processCommand(command);
    }


    //  ============================================================
    //  GETTERS / FINDERS
    //  ============================================================


    public SharedPreferences getSharedPrefs() {
        return myPrinter.getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }



    //  ============================================================
    //  PRIVATE / PROTECTED METHODS
    //  ============================================================

    /**
     * Main command processor and filter function*/
    private boolean processCommand(Command command) {

        if(command == null) return false;

        Answer answer = null;
        //  Process a help or info command
        if (processSettingsSudoCommand(command)) return true;
        if (processPersonCommand(command))return true;

        //  Reject if game not started
        if (status.equals(GameStatus.NOT_STARTED)) {
            return pushAnswer(new Answer(MessageFactory.MESSAGE_ENTER_START, Answer.DECORATION.STATUS_CHANGE));

        }
        //  Reject if game is over
        if (status.equals(GameStatus.OVER)) {
            return pushAnswer(new Answer("Your game is over, please type 'start' to start a new game", Answer.DECORATION.STATUS_CHANGE));
        }

        //  Check command by current context, if not search in the room
        if (ContextManager.get().getCurrentcontext() != null) {
            Log.i(TAG, "Context found : " + ContextManager.get().getCurrentcontext().getName());
            answer = ContextManager.get().getCurrentcontext().processCommand(command);
            if(answer != null && !answer.getDecorationType().equals(Answer.DECORATION.ERROR) )return pushAnswer(answer);
        }

        //  Try in the room
        answer = RoomManager.get().getCurrentPlace().processCommand(command);

        if (answer == null) {
            return pushAnswer(new Answer("\nAction not found:\n" + command.getString(), Answer.DECORATION.ERROR));
        }

        return pushAnswer(answer);
    }

    /**
     * Checks if the command is
     *  1. game-status-irrelevant (like start a game of help message)
     *  2.  answer to a riddle*/
    private boolean processSettingsSudoCommand(Command command) {
        switch (command.getType()) {
            case SETTINGS:
                Log.d(TAG, "Processing Sudo command");
                switch (command.getPointer().toLowerCase()) {
                    case HitWord.NAME:
                        if (!TextUtils.isEmpty(command.getAttribute())) {
                            PersonManager.get().saveUserName(command.getAttribute());
                            return pushAnswer(new Answer("Your name has been changed to " + command.getAttribute(), Answer.DECORATION.SETTINGS));
                        }
                        return true;

                    case HitWord.ANSWER:
                        Log.d(TAG, "Answer");
                        command.setType(HitWord.TYPE.ANSWER);
                        return pushAnswer(RiddleManager.get().processCommand(command));
                }

                break;

            case SUDO:
                if (TextUtils.isEmpty(command.getAttribute())) {
                    if (command.hasActionOf(HitWord.INFO)) {
                        pushAnswer(new Answer(getCurrentInfo(), Answer.DECORATION.ROOM_DESCRIPTION));
                        return true;
                    }
                    //this functions creates a pop-up dialog and show the help message
                    if (command.hasActionOf(HitWord.HELP)) {

                        myPrinter.clearScreen();
                        myPrinter.onMessageReceived(MessageFactory.getHelpMessage());
                        return true;
                    }
                    if (command.hasActionOf(HitWord.START)) {
                        changeGameStatus(GameStatus.RUNNING);
                        return true;
                    }
                }
                break;


        }
        return false;
    }

    /**
     * Checks quick command to observe player*/
    private boolean processPersonCommand(Command command) {
        if (command.hasActionOf(HitWord.SHOW, HitWord.INFO)) {
            // BACKPACK
            if (command.hasAttributeOf(HitWord.BACKPACK,HitWord.BACKPACK_SHORT)) {
                ContextManager.get().setCurrentContext(PersonManager.get().getPerson().getBackpack());
                return pushAnswer( new Answer(PersonManager.get().getPerson().getBackpack().getDescription(), Answer.DECORATION.BOX_ITEMS));
            }
            if (command.hasPointerOf(HitWord.ME)) {
                ContextManager.get().setCurrentContext(PersonManager.get().getPerson());
                return pushAnswer( new Answer(PersonManager.get().getPerson().getDescription(), Answer.DECORATION.BOX_ITEMS));
            }
        }
        return false;
    }

    /**
     * processes the answer to the UI,
     * only this method should be used for that*/

    private boolean pushAnswer(Answer answer) {
        if (answer == null) {
            myPrinter.onErrorReceived("Answer is null");
            return false;
        }

        String message = answer.getMessage();
        if(answer.getDecorationType().equals(Answer.DECORATION.ERROR)){
            myPrinter.onErrorReceived(message);
        }
        else{
            myPrinter.onMessageReceived(message,answer.getDecorationType());
        }

        return true;
    }



    /**
     * @return The description of the current room, checks if the game has started
     * */
    public String getCurrentInfo() {
        //TODO
        if (status == null || status.equals(GameStatus.NOT_STARTED))
            return MessageFactory.MESSAGE_ENTER_START;
        APlace place = RoomManager.get().getCurrentPlace();
        if (place != null) {
            return place.getDescription();
        }

        return "no info";
    }

    /**
     * Sets the current status and triggers the actions
     * @param status */
    public void changeGameStatus(GameStatus status, String... messages) {

        Answer answer = new Answer("Game status:" + status.name(), Answer.DECORATION.STATUS_CHANGE);
        switch (status) {

            case OVER:
                answer.addMessage(MessageFactory.GAME_OVER + Statistic.getCurrent().getString());
                break;
            case RUNNING:
                answer.addMessage(startGame().getMessage());
                break;
        }
        for(String message:messages)if(!TextUtils.isEmpty(message))answer.addMessage(message);
        pushAnswer(answer);

        this.status = status;
    }

    /**
     * init everything for a new game*/
    private Answer startGame() {
        //TODO
        if (status.equals(GameStatus.OVER)) {

        }
        if (status.equals(GameStatus.RUNNING)) {
            return new Answer("You are already running a game", Answer.DECORATION.SIMPLE);
        }
        if (status.equals(GameStatus.NOT_STARTED)) {
            status = GameStatus.RUNNING;
            myPrinter.clearScreen();
            Statistic.getNewInstance();
            return new Answer(RoomManager.get().getCurrentPlace().getStory(), Answer.DECORATION.STATUS_CHANGE);

        }
        return new Answer("startGame Error", Answer.DECORATION.ERROR);
    }


    public enum GameStatus {
        NOT_STARTED,
        RUNNING,
        OVER
    }
}
