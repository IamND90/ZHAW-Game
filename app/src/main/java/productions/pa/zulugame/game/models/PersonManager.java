package productions.pa.zulugame.game.models;

/**
 * Created by IamND on 09.10.2015.
 */
public class PersonManager {

    static PersonManager mThis;

    Person mPerson;

    private PersonManager(){
        //TODO change name
        mPerson = new Person("Dude");
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
}
