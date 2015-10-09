package productions.pa.zulugame.game.parser;

/**
 * Created by Andrey on 08.10.2015.
 */
public class HitWord {

    int number =0;
    String inputWord;
    HitWordType type;

    public HitWord(String inputWord, HitWordType type){
        this.inputWord = inputWord;
        this.type = type;
    }

    public HitWord(int inputNumber){
        inputWord = ""+number;
        number = inputNumber;
        this.type = HitWordType.NUMBER;
    }

    public int getNumber() {
        return number;
    }

    public String getInputWord() {
        return inputWord;
    }

    public HitWordType getType() {
        return type;
    }
}
