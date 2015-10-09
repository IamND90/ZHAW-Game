package productions.pa.zulugame.android;

/**
 * Created by Andrey on 08.10.2015.
 */
public interface UIHandler {

    void onMessageReceived(String message);
    void onErrorReceived(String message);
    void clearScreen();
}
