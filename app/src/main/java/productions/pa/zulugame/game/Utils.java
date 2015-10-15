package productions.pa.zulugame.game;

import java.util.List;

import productions.pa.zulugame.game.models.AModel;
import productions.pa.zulugame.game.models.IModel;
import productions.pa.zulugame.game.models.items.Item;

/**
 * Created by IamND on 15.10.2015.
 */
public class Utils {

    public static String findInList(List<String> list, String value){
        for(String string: list)if(string.equalsIgnoreCase(value)) return value;
        return null;
    }
    public static String findInList(String list[] , String value){
        for(String string: list)if(string.equalsIgnoreCase(value)) return value;
        return null;
    }

    public static Item findItemByName(List<IModel> list, String input){
        for(IModel item:list){
            if(item.getType().equals(IModel.TYPE.ITEM)&& item.getName().equals(input))return (Item) item;
        }
        return null;
    }
}
