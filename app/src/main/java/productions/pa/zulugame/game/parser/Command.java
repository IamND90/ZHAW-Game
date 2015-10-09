package productions.pa.zulugame.game.parser;

/**
 * Created by Andrey on 09.10.2015.
 */
public class Command {

    HitWordType mType;
    HitWord mCommand;
    HitWord mAction;
    HitWord mAttribute;

    public Command(HitWordType type, HitWord command){
        mCommand = command;
        mType = type;
    }
    public Command(HitWordType type, HitWord command, HitWord action){
        this(type,command);
        mAction = action;
    }
    public Command(HitWordType type, HitWord command, HitWord action, HitWord attribute){
        this(type,command,action);
        mAttribute = attribute;
    }

    public HitWord getmAction() {
        return mAction;
    }

    public HitWord getmAttribute() {
        return mAttribute;
    }

    public HitWordType getmType() {
        return mType;
    }

    public HitWord getmCommand() {
        return mCommand;
    }
}
