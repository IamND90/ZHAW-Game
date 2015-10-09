package productions.pa.zulugame.game.commands;

/**
 * Created by IamND on 09.10.2015.
 */
public class Answer {

    String message;

    String parameters[];

    boolean isValid ;

    public Answer(boolean valid, String message, String parameters[]){
        isValid = valid;
        this.message = message;
        this.parameters = parameters;
    }

    public String getMessage() {
        return message;
    }

    public String[] getParameters() {
        return parameters;
    }

    public boolean isValid() {
        return isValid;
    }
}
