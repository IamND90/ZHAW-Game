package productions.pa.zulugame.android;

import android.widget.TextView;

/**
 * Created by Andrey on 08.10.2015.
 */
public class Debugger {

    static Debugger mThis;
    TextView output;
    int countDebugs =0;

    public static Debugger get(){
        if(mThis == null){
            synchronized (Debugger.class){
                mThis = new Debugger();
            }

        }
        return mThis;
    }

    public void init(TextView debugOutput){
        output = debugOutput;
    }

    public void append(String message){
        if(output == null)return;
        String old = output.getText().toString();
        output.setText( old + "\n" + "#" + ++countDebugs + " " + message);
    }
    public void clear(){
        output.setText("");
    }
}
