package productions.pa.zulugame.game.manager;

import android.text.TextUtils;

import productions.pa.zulugame.game.Game;
import productions.pa.zulugame.game.models.Person;
import productions.pa.zulugame.game.models.items.Backpack;
import productions.pa.zulugame.game.parser.HitWord;

/**
 * Created by IamND on 09.10.2015.
 * Keeping the information about the player, accessible from everywhere
 */
public class PersonManager {

    //  ------------------------------------------------------------
    //  FIELDS
    //  ------------------------------------------------------------
    /**
     * @param mThis keeing the context on itself for not beeing deleted by java and beeing accessible*/
    static PersonManager mThis;

    /**
     * @param mPerson The current player*/
    Person mPerson;

    //  ============================================================
    //  CONSTRUCTOR ITEMS
    //  ============================================================

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
    public Backpack getBackPack() {
        return mPerson.getBackpack();
    }

    /** loads the username from android memory
     */
    public String getUserName() {
        return Game.getInstance().getSharedPrefs().getString(HitWord.NAME, "Dude");
    }

    /** saves the username to android memory
     */
    public void saveUserName(String name){
        if(!TextUtils.isEmpty(name) && name.length() >3) {
            Game.getInstance().getSharedPrefs().edit().putString(HitWord.NAME, name).commit();
            mPerson.setName(name);
        }
    }
}
