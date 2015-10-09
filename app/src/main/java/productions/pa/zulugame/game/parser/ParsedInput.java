package productions.pa.zulugame.game.parser;

import java.util.ArrayList;
import java.util.List;

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
    public List<HitWord> getAllNotFound() {
        List<HitWord> hitwords = new ArrayList<>();

        for(int i = 0; i< myHitwordsFound.size(); i ++){
            if( myHitwordsFound.get(i).type.equals(HitWordType.NOT_FOUND)){
                hitwords.add(myHitwordsFound.get(i));
            }
        }
        return hitwords;
    }

    /**
     * Removes all HitWords of type NOT_FOUND
     * @return returns a new list from the original
     * */
    public List<HitWord> getRecognizedWordArray(){
        List<HitWord> hitwords = new ArrayList<>();

        for(int i = 0; i< myHitwordsFound.size(); i ++){
            if( !myHitwordsFound.get(i).type.equals(HitWordType.NOT_FOUND)){
                hitwords.add(myHitwordsFound.get(i));
            }
        }
        return hitwords;
    }


}
