package productions.pa.zulugame.game.story;

import android.text.TextUtils;

import productions.pa.zulugame.game.Game;
import productions.pa.zulugame.game.models.Person;

/**
 * Created by IamND on 09.10.2015.
 */
public class PersonManager {

    static PersonManager mThis;

    Person mPerson;

    private PersonManager(){
        //TODO change name
        mPerson = new Person(getUserName());
    }
    public static PersonManager get(){
        if(mThis == null){
            synchronized (PersonManager.class){
                mThis = new PersonManager();
            }
        }
        return mThis;
    }

    public Person getPerson() {
        return mPerson;
    }

    public String getUserName() {
        return Game.getSharedPrefs().getString("username", "Dude");
    }
    public void saveUserName(String name){
        if(!TextUtils.isEmpty(name) && name.length() >3) {
            Game.getSharedPrefs().edit().putString("username", name).commit();
            mPerson.setName(name);
        }
    }
}
