package productions.pa.zulugame.game.parser;

/**
 * Created by IamND on 09.10.2015.
 */
public class Attribute {
    private STATUS mStatus = STATUS.NONE;
    private POINTING mPointer = POINTING.NONE;
    private ACTING mAction = ACTING.NONE;

    public static   Attribute byStatus(String input){
        Attribute attribute = new Attribute();
        for(STATUS status : STATUS.values())if(input.equalsIgnoreCase(status.name())){
            return attribute.setStatus(status);
        }
        return attribute;
    }

    public static   Attribute byAction(String input){
        Attribute attribute = new Attribute();
        for(ACTING action : ACTING.values())if(input.equalsIgnoreCase(action.name())){
            return attribute.setAction(action);
        }
        return attribute;
    }

    public static   Attribute byPointer(String input){
        Attribute attribute = new Attribute();
        for(POINTING pointer : POINTING.values())if(input.equalsIgnoreCase(pointer.name())){
            return attribute.setPointer(pointer);
        }
        return attribute;
    }

    public STATUS getStatus() {
        return mStatus;
    }

    public Attribute setStatus(STATUS mStatus) {
        this.mStatus = mStatus;
        return this;
    }

    public POINTING getPointer() {
        return mPointer;
    }

    public Attribute setPointer(POINTING mPointer) {
        this.mPointer = mPointer;
        return this;
    }

    public ACTING getAction() {
        return mAction;
    }

    public Attribute setAction(ACTING mAction) {
        this.mAction = mAction;
        return this;
    }

    public enum STATUS{
        NONE,

        CLOSED,
        OPEN
    }
    public enum MOVING{
        NONE,

        GO,
        LEAVE,
        MOVE,
        WALK,
        RUN,
        TURN;

    }
    public enum ACTING{
        NONE,

        OPEN,
        CLOSE,
        TAKE,
        USE,
        GET,
        LOOK
    }

    public enum POINTING{
        NONE,

        LEFT,
        RIGHT,
        BACK,
        FORWARD,
        IN,
        OUT
    }
}
