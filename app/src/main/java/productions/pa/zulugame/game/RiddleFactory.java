package productions.pa.zulugame.game;

import productions.pa.zulugame.game.models.baseclasses.IModel;
import productions.pa.zulugame.game.models.items.Riddle;

/**
 * Created by IamND on 17.10.2015.
 *
 * A collection of random Riddles
 */
public class RiddleFactory {


    public static Riddle get1_1(){
        return new Riddle(
                "The triangle",
                "How long is the third side of a rectangular triangle if the other, shorter two are 3 and 4?",
                IModel.RIDDLE_DIFFICULTY_1,
                "5");
    }

    public static Riddle get5_1(){
        return new Riddle(
                "Guess what",
                "You want me more, you want me less. This is just how I put your control drama's to test. Wont matter if you watch me, i'll only seem to stall. It's seems just when I'm up is when you'll miss me at all.",
                IModel.RIDDLE_DIFFICULTY_5,
                "Time");
    }

    public static Riddle get3_1(){
        return new Riddle(
                "Guess what",
                "Who goes up with the intention of falling down?",
                IModel.RIDDLE_DIFFICULTY_3,
                "Skydiver", "Sky diver","Basejumper");
    }

    public static Riddle get4_1(){
        return new Riddle(
                "What am I?",
                "I can be used to build castles,\n" +
                        "but I crumble in your hands.\n" +
                        "I can help a man see,\n" +
                        "and am found all around the lands.",
                IModel.RIDDLE_DIFFICULTY_4,
                "Sand");
    }
    public static Riddle get4_2(){
        return new Riddle(
                "What am I?",
                "I take what you receive and surrender it all by waving my flag.What am I?",
                IModel.RIDDLE_DIFFICULTY_4,
                "Mailbox", "A mailbox");
    }

    public static Riddle get2_1(){
        return new Riddle(
                "Telephone",
                "What number do you get when you multiply all of the numbers on a telephone's number pad?",
                IModel.RIDDLE_DIFFICULTY_2,
                "0");
    }
    public static Riddle get2_2(){
        return new Riddle(
                "What am I?",
                "I hide when you wake but upset you as you sleep... ",
                IModel.RIDDLE_DIFFICULTY_3,
                "Nightmare", "bad dream");
    }
    public static Riddle get5_2(){
        return new Riddle(
                "What am I?",
                "How high would you have to count before you use the letter 'A' in the spelling of a number?",
                IModel.RIDDLE_DIFFICULTY_3,
                "o");
    }
}
