package productions.pa.zulugame.game;

import java.util.List;

/**
 * Created by IamND on 15.10.2015.
 */
public class Utils {

    public static String findInList(List<String> list, String value){
        for(String string: list)if(string.equalsIgnoreCase(value)) return value;
        return null;
    }
}
