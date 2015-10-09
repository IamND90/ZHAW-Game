package productions.pa.zulugame.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import productions.pa.zulugame.R;
import productions.pa.zulugame.game.Game;

public class MainActivity extends AppCompatActivity implements MessageCallback{

    static final String SHAREDPREFS  = "sprefs";
    static final String BOOL_OPEN_DEBUG  = "debugopen";

    TextView textOutput;
    TextView textError;
    TextView textDebug;
    EditText textInput;
    ImageButton buttonEnterCommand;

    Game myGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textOutput = (TextView) findViewById(R.id.textOutput);
        textInput = (EditText) findViewById(R.id.textInput);
        textError = (TextView) findViewById(R.id.textError);
        textDebug = (TextView) findViewById(R.id.debug);
        buttonEnterCommand = (ImageButton) findViewById(R.id.imageButton);

        Debugger.get().init(textDebug);

        myGame = new Game(this);

        buttonEnterCommand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the input string and ceck if its not empty
                String text = textInput.getText().toString();
                if(TextUtils.isEmpty(text)) return;

                myGame.onInputString(text);
            }
        });
        if(!getSharedPreferences(SHAREDPREFS,MODE_PRIVATE).getBoolean(BOOL_OPEN_DEBUG,false)){
            textDebug.setVisibility(View.GONE);
        }
        textError.setVisibility(View.GONE);
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
            boolean openDebug = !getSharedPreferences(SHAREDPREFS,MODE_PRIVATE).getBoolean(BOOL_OPEN_DEBUG,false);
            if(openDebug){
                textDebug.setVisibility(View.VISIBLE);
            }
            else{
                textDebug.setVisibility(View.GONE);
            }
            getSharedPreferences(SHAREDPREFS, MODE_PRIVATE).edit().putBoolean(BOOL_OPEN_DEBUG, openDebug).commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onMessageReceived(int resultCode, String message) {
        switch(resultCode){
            case RESULT_CODE_OK:
                String text = textOutput.getText().toString();

                if(TextUtils.isEmpty(message))textOutput.setText("");
                else
                    textOutput.setText(text + "\n" + message);

                textInput.setText("");
                textError.setVisibility(View.GONE);
                break;
            case RESULT_CODE_PARSER_FAIL:
                textError.setText(message);
                textError.setVisibility(View.VISIBLE);
                break;
        }
    }
}
