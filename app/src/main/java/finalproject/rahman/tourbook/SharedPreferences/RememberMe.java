package finalproject.rahman.tourbook.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by rahma on 1/7/2017.
 */

public class RememberMe {
    Context context;
    private SharedPreferences sharedPreference;
    private SharedPreferences.Editor editor;

    private final static String REMEMBER_ME="remember_me";
    private final static String REMEMBER_USER="user_name";
    private final static String REMEMBER_PASS="password";
    private final static String DEFAULT="nothing";

    private Boolean savedPreferenceFlag=false;

    public RememberMe(Context context) {
        this.context=context;
        sharedPreference = this.context.getSharedPreferences(REMEMBER_ME, Context.MODE_PRIVATE);
    }

    public Boolean putUserPass(String userName,String password)
    {

        editor = sharedPreference.edit();
        editor.putString(REMEMBER_USER,userName);
        editor.putString(REMEMBER_PASS,password);
        savedPreferenceFlag=editor.commit();
        return savedPreferenceFlag;
    }
    public String  getUser()
    {
        return sharedPreference.getString(REMEMBER_USER,DEFAULT);
    }

    public String  getPass()
    {
        return sharedPreference.getString(REMEMBER_PASS,DEFAULT);
    }

}
