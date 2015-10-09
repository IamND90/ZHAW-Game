package productions.pa.zulugame.game.parser;

/**
 * Created by Andrey on 08.10.2015.
 */
public class Parser {

    public static ParsedInput[] parseInputMessage(String message){

        //Split by "," to execute mutiple commands
        String commands[] = message.split(",");

        //create the instance to return
        ParsedInput parsedInput[] = new ParsedInput[commands.length];


        for(int coms =0; coms < commands.length ; coms++) {

            //create instance
            parsedInput[coms] = new ParsedInput(commands[coms]);
            // Split the input by TAB to get the single words
            // We assume, that we have no writing mistakes
            String words[] = message.split(" ");

            // Find and add all the word in our HitWordFactory
            // If the word if not found it will be added anyways, it as a type that is called NOT_FOUND for filtering later
            for (String word : words) {
                HitWord foundHitword = HitWordFactory.findHitWord(word);
                parsedInput[coms].addHitWord(foundHitword);
            }

        }
        return parsedInput;
    }



    //TODO
    public static String parseMessage(){
        return null;
    }
}
