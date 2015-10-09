package productions.pa.zulugame.game.commands;

import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.parser.HitWordType;

/**
 * Created by Andrey on 09.10.2015.
 */
public class Command {

    HitWordType mType;
    HitWord mAction;
    HitWord mPointer;
    HitWord mAttribute;

    public Command(HitWordType type, HitWord action, HitWord pointer, HitWord attribute){
        mAction = action == null ? new HitWord("",HitWordType.UNKNOWN) : action;
        mPointer = pointer== null ? new HitWord("",HitWordType.UNKNOWN) : pointer;
        mType = type;
        mAttribute = attribute== null ? new HitWord("",HitWordType.UNKNOWN) : attribute;
    }

    public HitWord getPointer() {
        return mPointer;
    }

    public HitWord getAttribute() {
        return mAttribute;
    }

    public HitWordType getType() {
        return mType;
    }

    public HitWord getAction() {
        return mAction;
    }

    public String getString(){
        StringBuilder builder = new StringBuilder("[" + mType.name() + "]\t");

        builder.append("Action: [" + (mAction== null ? "" : mAction.getString()) + "], ");
        builder.append("Pointer: [" + (mPointer== null ? "" : mPointer.getString()) + "], ");
        builder.append("Attribute: [" + (mAttribute== null ? "" : mAttribute.getString()) + "]");

        return builder.toString();
    }
}
