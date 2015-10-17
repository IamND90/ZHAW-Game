package productions.pa.zulugame.game.parser;

/**
 * Created by Andrey on 08.10.2015.
 */
public class Parser {

    public static ParsedInput parseInputMessage(String message){

        //Split by "," to execute mutiple commands
        //create the instance to return
        ParsedInput parsedInput = new ParsedInput(message);

        String sudoSetting[] = message.split(":");
        if(sudoSetting.length == 2){
            HitWord pointer = new HitWord(sudoSetting[0],HitWordType.SETTINGS);
            HitWord attribute = new HitWord(sudoSetting[1],HitWordType.SETTINGS);
            parsedInput.addHitWord(pointer);
            parsedInput.addHitWord(attribute);

            return parsedInput;
        }

        // Split the input by TAB to get the single words
        // We assume, that we have no writing mistakes
        String words[] = message.split(" ");

        // Find and add all the word in our HitWordFactory
        // If the word if not found it will be added anyways, it as a type that is called NOT_FOUND for filtering later
        for (String word : words) {
            HitWord foundHitword = HitWord.findHitWord(word);
            //Add to found array if it is a found word
            if( !foundHitword.getType().equals(HitWordType.NOT_FOUND))
                parsedInput.addHitWord(foundHitword);
        }


        return parsedInput;
    }



    //TODO
    public static String parseMessage(){
        return null;
    }
}
