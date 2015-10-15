package productions.pa.zulugame.game.models.quests;

import java.util.List;

import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.parser.Attribute;

/**
 * Created by IamND on 15.10.2015.
 */
public class RiddleTriangle extends Riddle {


    public RiddleTriangle() {
        super(
                "The triangle 1",
                "How long is the third side of a rectangular triangle if the other two are 3 and 4?",
                1);
    }

    @Override
    public String getCorrectAnswer() {
        return "5";
    }



}
