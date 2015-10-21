package productions.pa.zulugame.game;

import android.content.Context;
import android.widget.ScrollView;
import android.widget.TextView;

import productions.pa.zulugame.game.manager.PersonManager;
import productions.pa.zulugame.game.parser.Answer;

/**
 * Created by Andrey on 15.10.2015.
 *
 * Printer class that can add decorations to text and process it to the UI
 */
public class Printer implements UIHandler{

    //  ------------------------------------------------------------
    //  STATIC FINAL FIELDS
    //  ------------------------------------------------------------
    /**
     * @param currentText the currently set text on the screen
     * @param maxDecoPerLine counts the amount of max chars for top/bot decoration
     * @param MAX_TEXT_LENGT max chars to not overload the android view*/
    private static final int maxDecoPerLine = 32;
    private static final int MAX_TEXT_LENGT = 680;
    static Printer mThis;

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------
    String currentText ="";

    /**
     * @param outputView android defined textview
     * @param scrollView android defined scrollview, to have a scrollview*/
    final TextView outputView;
    final ScrollView scrollView;


    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

    public Printer(TextView outputView){
        this.outputView = outputView;
        scrollView = (ScrollView) outputView.getParent();
        mThis = this;

    }

    public static Printer get(){
        return mThis;
    }

    //  ============================================================
    //  @Override METHODS
    //  ============================================================

    @Override
    public void onMessageReceived(String message,Answer.DECORATION... decorationType) {
        //  Add deco if exists
        if(decorationType != null && decorationType.length>0){
            message= addDecoration(message,decorationType[0]);
        }
        postToUI(message);

    }

    @Override
    public void onErrorReceived(String message) {
        postToUI(addDecoration(message, Answer.DECORATION.ERROR));
    }

    @Override
    public void clearScreen() {
        currentText= "";
        outputView.setText("");
    }

    //  Needed to get android local data store and resources
    @Override
    public Context getContext() {
        return outputView.getContext();
    }

    //  ============================================================
    //  PRIVATE / PROTECTED METHODS
    //  ============================================================

    private void postToUI(String text){
        // Shorten the text if its too long
        if(currentText.length()> MAX_TEXT_LENGT) {
            int total = currentText.length()-1;
            currentText = currentText.substring((int) (total*0.3),total);
        }

        //  Add to last text and make a new line
        currentText = currentText+"\n"+ text ;

        // Add text to UI
        outputView.setText(currentText);
        // Scroll full down
        scrollView.post(new Runnable() {
            @Override
            public void run() {

                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    /**
     * Here are the decorations defined for the different output-types*/
    private String addDecoration(String message,Answer.DECORATION deco) {
        message = message.replaceAll("\n\n","\n");
        if(message.startsWith("\n"))message = message.substring(1,message.length());

        switch (deco) {
            case SIMPLE:
                message = addSideDecoration(message, ">");
                break;
            case FAIL:
                message = addSideDecoration(message, ">f>");
                break;
            case RIDDLE:
                message = addSideDecoration(message, "?");
                message = addTopAndBotDecorations(message, "?");
                break;
            case ROOM_DESCRIPTION:
                message = addSideDecoration(message, "||");
                message = addTopAndBotDecorations(message, "=");
                break;
            case DESCRIPTION:
                message = addSideDecoration(message, "|");
                message = addTopAndBotDecorations(message, "-");
                break;
            case SETTINGS:
                message = addSideDecoration(message, "&");
                message = addTopAndBotDecorations(message, "&");
                break;
            case STATUS_CHANGE:
                message = addSideDecoration(message, "!!");
                message = addTopAndBotDecorations(message, "*");
                break;
            case BOX_ITEMS:
                message = addSideDecoration(message, "=");
                message = addTopAndBotDecorations(message, "=");
                break;
            case ADDING:
                message = addSideDecoration(message, "+");
                break;
            case REMOVING:
                message = addSideDecoration(message, "-");
                message = addTopAndBotDecorations(message, "-");
                break;
            case ERROR:
                message = addSideDecoration(message, ">ERR>");
                break;
            case PLAYER_MESSAGE_ECHO:
                message = "<" + PersonManager.get().getUserName()+ ">" + ": \"" + message + "\"";
                break;
        }
        return message;
    }

    private String addTopAndBotDecorations(String message, String deco) {
        String tabs = getTabs(maxDecoPerLine,deco);
        message = tabs +"\n" + message + "\n" + tabs;
        return message;
    }

    private String addSideDecoration(String message, String deco) {
        if(deco == null  || deco == "") return message;
        return deco + message.replaceAll("\n",("\n" + deco + "\t"));

    }

    private String getTabs(int count,String regex) {
        String out = "";
        for(; out.length() < count;)out+=regex;
        return out;
    }


}
