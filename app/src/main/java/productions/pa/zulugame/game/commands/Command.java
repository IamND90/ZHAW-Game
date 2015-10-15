package productions.pa.zulugame.game.commands;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.parser.HitWord;
import productions.pa.zulugame.game.parser.HitWordType;

/**
 * Created by Andrey on 09.10.2015.
 */
public class Command {

    HitWordType mType;
    HitWord mAction;
    HitWord mPointer;
    List<HitWord> mAttribute = new ArrayList<>();

    public Command(HitWordType type, HitWord action, HitWord pointer, HitWord attribute){
        mAction = action == null ? new HitWord("",HitWordType.UNKNOWN) : action;
        mPointer = pointer== null ? new HitWord("",HitWordType.UNKNOWN) : pointer;
        mType = type;
        mAttribute.add(attribute== null ? new HitWord("",HitWordType.UNKNOWN) : attribute);
    }

    public Command(HitWordType type, HitWord action, HitWord pointer, List<HitWord> attributes){
        mAction = action == null ? new HitWord("",HitWordType.UNKNOWN) : action;
        mPointer = pointer== null ? new HitWord("",HitWordType.UNKNOWN) : pointer;
        mType = type;
        mAttribute = attributes;
    }

    public void addAttribute(HitWord attribute){
        mAttribute.add(attribute);
    }

    public List<HitWord> getAttributes() {
        return mAttribute;
    }

    public HitWord getPointer() {
        return mPointer;
    }

    public HitWord getAttribute() {
        return mAttribute.get(0);
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
        for(int i =0; i < mAttribute.size(); i++)
        builder.append("Attribute: [" + (mAttribute.get(i)== null ? "" : mAttribute.get(i).getString()) + "]");

        return builder.toString();
    }
}
