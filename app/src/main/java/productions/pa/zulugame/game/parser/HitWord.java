package productions.pa.zulugame.game.parser;

/**
 * Created by Andrey on 08.10.2015.
 */
public class HitWord {

    private int number =0;
    private String inputWord;
    private HitWordType type;

    public HitWord(String inputWord, HitWordType type){
        this.inputWord = inputWord;
        this.type = type;
    }

    public HitWord(String inputWord, HitWordType type,int inputNumber){
        this.inputWord = inputWord;
        number = inputNumber;
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

    public String getString() {
        return inputWord == null ? "" : inputWord;
    }

    public HitWordType getType() {
        return type;
    }
}
