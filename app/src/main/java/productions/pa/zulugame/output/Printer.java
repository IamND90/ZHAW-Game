package productions.pa.zulugame.output;

import android.app.Activity;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Andrey on 15.10.2015.
 */
public class Printer {

    static final int DELAY_MS = 6;
    static final int DELAY_DOT_MS = 200;
    static final int DELAY_NEW_LINE_MS = 360;

    final TextView outputView;
    final Activity activity;
    String currentText ="";
    int textLenght = 0 ;
    int currentIndex = 0 ;

    List<String> waitingString = new ArrayList<>();

    AtomicBoolean isRunningTask= new AtomicBoolean();

    long lastTime;
    long timePassed;


    public Printer(Activity activity,TextView outputView){
        this.outputView = outputView;
        currentText = outputView.getText().toString();
        this.activity = activity;
    }


    public void print(final String text){
        if(isRunningTask.get()){
            synchronized (waitingString) {
                waitingString.add(text);
            }
            return;
        }
        currentText = text;
        currentIndex =0;
        textLenght = text.length();
        lastTime = System.currentTimeMillis();
        timePassed = 0;

        run(text);
    }

    public void append(String text){


    }

    public void clear(){
        print("");
    }

    private void run(final String text){
        if(!waitingString.isEmpty())waitingString.remove(0);
        isRunningTask.set(true);
        new Thread(new Runnable() {
            char current = text.charAt(0);
            @Override
            public void run() {
                while(currentIndex < textLenght){
                    if(timePassed >= ((current != '\n' || current != '.') ? (current == '.' ? DELAY_DOT_MS : DELAY_NEW_LINE_MS) : DELAY_MS)){
                        String next = text.substring(0,currentIndex++);
                        setText(next);
                        lastTime = System.currentTimeMillis();
                        current = text.charAt(currentIndex);
                    }

                    timePassed = System.currentTimeMillis() -lastTime;
                }

                isRunningTask.set(false);
                if(!waitingString.isEmpty()){
                    Printer.this.print(waitingString.get(0));
                }
            }
        }).start();
    }

    private void setText(final String value){
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                outputView.setText(value);
            }
        });
    }
}
