package productions.pa.zulugame.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import productions.pa.zulugame.R;
import productions.pa.zulugame.game.Game;
import productions.pa.zulugame.game.Printer;

/**
 * This is the android part
 * onCreate is the first method that is calles, there we find our two views and create the game*/
public class MainActivity extends AppCompatActivity {

    TextView textOutput;
    EditText textInput;

    Game myGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textOutput = (TextView) findViewById(R.id.textOutput);
        textInput = (EditText) findViewById(R.id.textInput);

        textInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (textInput.getText().toString().endsWith("\n")) {
                    handleInput();
                }
            }
        });

        myGame = new Game(new Printer(textOutput));
    }

    /**
     * is triggered bu hitting the "esnter" on the keyboard
     * passes the input to the game module*/
    private void handleInput() {

        //  Get the input string and check if its not empty
        String text = textInput.getText().toString();
        if (text.equals("\n")) {
            textInput.setText("");
            return;
        }
        //  remove empty line
        if (text.length() >= 2 && text.endsWith("\n")) text = text.substring(0, text.length() - 1);

        if (TextUtils.isEmpty(text)) return;
        textInput.setText("");

        myGame.onInputString(text);
    }

}
