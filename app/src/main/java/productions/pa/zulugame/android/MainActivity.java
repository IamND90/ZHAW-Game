package productions.pa.zulugame.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import productions.pa.zulugame.R;
import productions.pa.zulugame.game.Game;
import productions.pa.zulugame.output.Printer;

public class MainActivity extends AppCompatActivity {

    static final String SHAREDPREFS  = "sprefs";
    static final String BOOL_OPEN_DEBUG  = "debugopen";

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
                if( textInput.getText().toString().endsWith("\n")){
                    handleInput();
                }
            }
        });

        myGame = new Game(new Printer(textOutput));


    }

    private void handleInput() {
        //Get the input string and ceck if its not empty
        String text = textInput.getText().toString();
        if(text.equals("\n")){
            textInput.setText("");
            return;
        }
        if(text.length() >= 2 &&text.endsWith("\n")) text = text.substring(0,text.length()-1);

        if(TextUtils.isEmpty(text)) return;
        textInput.setText("");

        myGame.onInputString(text);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
