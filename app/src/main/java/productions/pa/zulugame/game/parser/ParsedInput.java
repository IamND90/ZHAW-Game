package productions.pa.zulugame.game.parser;

import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.commands.Command;

/**
 * Created by Andrey on 08.10.2015.
 */
public class ParsedInput {

    final List<HitWord> myHitwordsFound = new ArrayList<>();
    final String originalString;

    public ParsedInput(String original){
        originalString = original;
    }

    public void addHitWord(HitWord word){
        myHitwordsFound.add(word);
    }

    public String getOriginalString() {
        return originalString;
    }

    public List<HitWord> getAllHitwordsFound() {
        return myHitwordsFound;
    }


    public Command createCommand(){

        // 0:command, 1: pointer, 2: attribute
        HitWord command =null;
        HitWord pointer = null;
        HitWord attribute = null;
        HitWordType type = HitWordType.UNKNOWN;

        //Search Command
        for(HitWord hitword : myHitwordsFound){
            if(hitword.getType().equals(HitWordType.SETTINGS) && myHitwordsFound.size() == 2){
                return new Command(hitword.getType(),null,hitword,myHitwordsFound.get(1));
            }
        }


        //Search What
        for(HitWord hitword : myHitwordsFound){
            if(hitword.getType().equals(HitWordType.SUDO) ||hitword.getType().equals(HitWordType.MOVING) || hitword.getType().equals(HitWordType.ACTING)){
                command = hitword;
                type =  command.getType();
                break;
            }
        }
        //Search Pointer
        for(HitWord hitword : myHitwordsFound){
            if(hitword.getType().equals(HitWordType.POINTER)){
                pointer = hitword;
                if(type.equals(HitWordType.UNKNOWN))type = pointer.getType();
                break;
            }
        }
        //Search Attribute
        for(HitWord hitword : myHitwordsFound){
            if(hitword.getType().equals(HitWordType.ITEM) || hitword.getType().equals(HitWordType.PLACE)){
                attribute = hitword;
                if(type.equals(HitWordType.UNKNOWN))type = attribute.getType();
                break;
            }
        }
        //If all the words not hitwords, maybe its an answer
        if(command == null && pointer == null && attribute == null){
            return new Command(HitWordType.ANSWER,command,pointer,myHitwordsFound);
        }

        return new Command(type,command,pointer,attribute);
    }

}
