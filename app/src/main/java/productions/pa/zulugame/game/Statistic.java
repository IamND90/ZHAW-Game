package productions.pa.zulugame.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import productions.pa.zulugame.game.models.baseclasses.AItem;

/**
 * Created by IamND on 17.10.2015.
 *
 * Just for fun, to track some statictic while gaming
 * Due to not much time, will not be explained further
 */
public class Statistic implements Serializable {

    static transient Statistic mThis;

    public int roomsDiscovered = 1;
    public int roomMovements = 0;


    public int solvedRiddles = 0;
    public int failedRiddles = 0;


    List<AItem> foundItems = new ArrayList<>();


    private Statistic() {

    }

    public static Statistic getNewInstance() {
        mThis = new Statistic();
        return mThis;
    }

    public static Statistic getCurrent() {
        if (mThis == null) {
            mThis = new Statistic();
        }
        return mThis;
    }

    public void itemFound(AItem item) {
        for (AItem exist : foundItems) if (exist.getId() == item.getId()) return;
        foundItems.add(item);
    }

    public String getString() {
        StringBuilder stats = new StringBuilder("\n*************Statistics*************\n");

        stats.append("Rooms discovered: [").append(roomsDiscovered).append("]\n");
        stats.append("Room movements: [").append(roomMovements).append("]\n");
        stats.append("Solved/Failed/Total Riddles: [").append(solvedRiddles).append("/").append(failedRiddles).append("/").append(solvedRiddles + failedRiddles).append("]\n");
        stats.append("Found items[" + foundItems.size() + "]:\n");
        for (AItem item : foundItems) {
            stats.append("[").append(item.getName() + " - " + item.getDescription() + "]\n");
        }
        stats.append("*************----------*************\n");

        return stats.toString();
    }
}
