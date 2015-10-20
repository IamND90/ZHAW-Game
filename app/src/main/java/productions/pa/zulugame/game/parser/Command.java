package productions.pa.zulugame.game.parser;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.models.baseclasses.IModel;

import static productions.pa.zulugame.game.parser.HitWord.TYPE;

/**
 * Created by Andrey on 09.10.2015.
 * <p/>
 * Command created by input of the user. It is iteratively processed though the modes.
 * read more @AModel
 */
public class Command {

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------

    /**
     * A command can have up to three hitwords:
     *  DO      (Action)
     *  WHERE   (Pointer)
     *  WHAT    (Attribute)
     *
     *  it is later processed and combined by the model what it has to do with it
     *
     * @param typedCommand original input string of the user
     * @param mType type of the command to filter out at processing
     * @param mAction like use, take, open etc..
     * @param mPointer ist mostly the color, can also be other like "me" etc..
     * @param mAttribute list of all attributes: mostly name of models to find the context*/

    final String typedCommand;
    TYPE mType;
    HitWord mAction;
    HitWord mPointer;
    List<HitWord> mAttribute = new ArrayList<>();

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================


    public Command(String originalCommand, TYPE type, String answer) {
        mAttribute.add(new HitWord(answer, type));
        mType = type;
        typedCommand = originalCommand;
    }

    public Command(String originalCommand, TYPE type, HitWord action, HitWord pointer, HitWord attribute) {
        mAction = action;
        mPointer = pointer;
        mType = type;
        mAttribute.add(attribute == null ? new HitWord("", TYPE.UNKNOWN) : attribute);
        typedCommand = originalCommand;
    }

    public Command(String originalCommand, TYPE type, HitWord action, HitWord pointer, List<HitWord> attributes) {
        mAction = action;
        mPointer = pointer;
        mType = type;
        mAttribute = attributes;
        typedCommand = originalCommand;
    }

    //  ============================================================
    //  GETTERS / FINDERS
    //  ============================================================

    public TYPE getType() {
        return mType;
    }

    public void setType(TYPE mType) {
        this.mType = mType;
    }

    public String getAction() {
        return mAction == null ? "" : mAction.getString();
    }

    public String getPointer() {
        return mPointer == null ? "" : mPointer.getString();
    }


    public int getNumber(){
        int number = IModel.NOT_A_NUMBER;
        if(mAttribute.size() < 0){
            try {
                number = Integer.parseInt(mAttribute.get(0).getString());
            }catch (Exception ex){
                number = IModel.NOT_A_NUMBER;
            }
        }
        return number;
    }


    public boolean hasPointer(){return mPointer == null ? false : true;}
    public String getAttribute() {
        return mAttribute.isEmpty() ? "" : mAttribute.get(0).getString();
    }

    public String getColor() {
        if (mPointer == null) return "";
        if (mPointer.getType().equals(TYPE.COLOR)) return mPointer.getString();
        return "";
    }

    public String getTypedCommand() {
        return typedCommand;
    }

    public boolean hasAllOf(String... attributes) {

        boolean hasAttr[] = new boolean[attributes.length];

        for (int i = 0; i < attributes.length; i++) {
            hasAttr[i] = false;
            if (attributes[i].equalsIgnoreCase(getAction()) ||
                    attributes[i].equalsIgnoreCase(getPointer()) ||
                    attributes[i].equalsIgnoreCase(getAttribute())) hasAttr[i] = true;
        }

        for (boolean has : hasAttr) {
            if (!has) return false;
        }

        return true;
    }

    public boolean hasOneOf(String... attributes) {
        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].equalsIgnoreCase(getAction()) ||
                    attributes[i].equalsIgnoreCase(getPointer()) ||
                    attributes[i].equalsIgnoreCase(getAttribute())) return true;
        }
        return false;
    }

    public boolean hasActionOf(String... attributes) {

        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].equalsIgnoreCase(getAction())) return true;
        }

        return false;
    }

    public boolean hasPointerOf(String... attributes) {

        for (int i = 0; i < attributes.length; i++) {
            if (attributes[i].equalsIgnoreCase(getPointer())) return true;
        }

        return false;
    }

    public boolean hasAttributeOf(String... attributes) {
        for (int i = 0; i < attributes.length; i++) {
            for (int a = 0; a < mAttribute.size(); a++) {
                if (attributes[i].equalsIgnoreCase(mAttribute.get(a).getString())) {
                    return true;
                }
            }
        }

        return false;
    }

    // =============================================================
    //  PUBLIC METHODS
    //  ============================================================
    public String getString() {
        StringBuilder builder = new StringBuilder("\n[" + mType.name() + "]");

        if (!TextUtils.isEmpty(getAction()))
            builder.append("\nAction:[" + getAction() + "]");
        if (!TextUtils.isEmpty(getPointer()))
            builder.append("\nPointer:[" + getPointer() + "]");
        for (int i = 0; i < mAttribute.size(); i++)
            if (!TextUtils.isEmpty(mAttribute.get(i).getString()))
                builder.append("\nAttr:[" + (mAttribute.get(i).getString()) + "]");

        return builder.toString();
    }

    public boolean isEmpty() {
        if (mAction == null &&
                mPointer == null &&
                mAttribute == null
                ) return true;
        return false;
    }


}
