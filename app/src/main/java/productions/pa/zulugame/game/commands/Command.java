package productions.pa.zulugame.game.commands;

import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.parser.HitWordType;

/**
 * Created by Andrey on 09.10.2015.
 */
public class Command {

    HitWordType mType;
    HitWord mAction= null;
    HitWord mPointer=null;
    HitWord mAttribute=null;

    public Command(HitWordType type, HitWord action, HitWord pointer, HitWord attribute){
        mAction = action;
        mPointer = pointer;
        mType = type;
        mAttribute = attribute;
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

        builder.append("Action: [" + (mAction== null ? "" : mAction.getName()) + "], ");
        builder.append("Pointer: [" + (mPointer== null ? "" : mPointer.getName()) + "], ");
        builder.append("Attribute: [" + (mAttribute== null ? "" : mAttribute.getName()) + "]");

        return builder.toString();
    }
}
