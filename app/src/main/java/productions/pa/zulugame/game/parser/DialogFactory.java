package productions.pa.zulugame.game.parser;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;

/**
 * Created by IamND on 09.10.2015.
 */
public class DialogFactory {

    public static final String TITLE_HELP ="Help menu";
    public static final String TITLE_INFO ="Info";



    public static void createDialog(Context context, String title, String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);

        dialog.setTitle(title).
                setMessage(message);

        dialog.create().show();

    }
}
