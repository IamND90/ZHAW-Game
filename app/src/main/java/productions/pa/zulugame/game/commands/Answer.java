package productions.pa.zulugame.game.commands;

/**
 * Created by IamND on 09.10.2015.
 */
public class Answer {

    String message;

    Command command;

    int contextId =0;
    boolean isError = false;

    public Answer(String message){
        this.message = message;
    }

    public Answer setContextId(int id){
        contextId = id;
        return this;
    }
    public String getMessage() {
        return message;
    }


    public Command getCommand() {
        return command;
    }

    public Answer setCommand(Command command) {
        this.command = command;
        return this;
    }

    public int getContextId() {
        return contextId;
    }

    public boolean isError() {
        return isError;
    }

    public Answer setIsError(boolean isError) {
        this.isError = isError;
        return this;
    }
}
