package productions.pa.zulugame.android;

import android.content.Context;

import productions.pa.zulugame.game.parser.Answer;

/**
 * Created by Andrey on 08.10.2015.
 */
public interface UIHandler {

    void onMessageReceived(String message,Answer.DECORATION... decorationType);
    void onErrorReceived(String message);
    void clearScreen();

    Context getContext();
}
