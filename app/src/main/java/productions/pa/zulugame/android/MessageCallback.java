package productions.pa.zulugame.android;

/**
 * Created by Andrey on 08.10.2015.
 */
public interface MessageCallback {

    int RESULT_CODE_OK = 0;
    int RESULT_CODE_PARSER_FAIL = -1;

    void onMessageReceived(int resultCode, String message);
    void clearScreen();
}
