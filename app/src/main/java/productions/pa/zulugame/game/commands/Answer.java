package productions.pa.zulugame.game.commands;

/**
 * Created by IamND on 09.10.2015.
 */
public class Answer {

    String message;

    TYPE answerType[];

    int contextId =-1;
    boolean isError = false;

    public Answer(TYPE... type){
        this.message = "No Answer set at " + contextId;
        answerType = type;
    }

    public Answer(String message, TYPE... type){
        this.message = message;
        answerType = type;
    }
    public Answer(String message){
        this.message = message;
        answerType = new TYPE[]{TYPE.SIMPLE_OUTPUT};
    }

    public Answer setContextId(int id){
        contextId = id;
        return this;
    }
    public String getMessage() {
        return message;
    }



    public int getContextId() {
        return contextId;
    }

    public boolean isError() {
        return isError;
    }

    public TYPE[] getAnswerTypes() {
        return answerType;
    }


    public Answer setIsError(boolean isError) {
        this.isError = isError;
        return this;
    }

    public boolean isFailAnswer(){
        return (answerType[0].equals(TYPE.FAIL) ? true : false);
    }
    public enum TYPE{

        ITEM_NOT_FOUND,
        PLACE_NOT_FOUND,
        SIMPLE_OUTPUT,

        SUCCESS,
        FAIL,

        INTERACT,

        MOVE_TO_PLACE
    }
}
