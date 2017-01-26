package finalproject.rahman.tourbook.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import finalproject.rahman.tourbook.Controls.ErrorChecker;
//import finalproject.rahman.tourbook.Controls.SetMenu;
import finalproject.rahman.tourbook.Database.Constants;
import finalproject.rahman.tourbook.Database.DatabaseManager;
import finalproject.rahman.tourbook.Database.Variables;
import finalproject.rahman.tourbook.PreMainActivity;
import finalproject.rahman.tourbook.R;
import finalproject.rahman.tourbook.SharedPreferences.RememberMe;

/**
 * Created by rahma on 12/31/2016.
 */

public class LogInFragment extends Fragment {
    View view;
    private EditText userNameEt;
    private EditText passEt;
    private CheckBox rememberMeCb;
    private TextView submitTv;
    private FloatingActionButton fab;

    private ErrorChecker errorChecker;
    private RememberMe rememberMe;

    private DatabaseManager databaseManager;
    private SharedPreferences checkPreference;
    private SharedPreferences.Editor checkEditor;

    private Boolean checked=false;
    private Boolean autoLogFlag=false;

    private final static String ERROR_MSG_PASS="Password Didn't match";
    private final static String ERROR_MSG="Wrong User Name";
    private final static String CHECK="check";
    private final static String TRUE="true";
    private final static String DEFAULT="default";
    private final static String AUTO_LOG_TAG="auto_log";
    private final static String AUTO_LOG_PERMISSION_TRUE="true";
    private final static String AUTO_LOG_PERMISSION_FALSE="false";

    private Fragment fragment;




    public LogInFragment() {

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_log_in,container,false);
        Variables.SHOW_MENU=false;
//        setMenu.setMenuVisibility(PreMainActivity.setMenu);
        errorChecker = new ErrorChecker(getActivity());
        rememberMe=new RememberMe(getActivity());
        PreMainActivity.collapsingToolbarLayout.setTitle("Log In");

        userNameEt = (EditText)view.findViewById(R.id.login_user_name_et);
        passEt = (EditText)view.findViewById(R.id.login_password_et);
        rememberMeCb=(CheckBox)view.findViewById(R.id.login_remember_me_cb);
        submitTv = (TextView)view.findViewById(R.id.login_submit_tv);
        fab = (FloatingActionButton)view.findViewById(R.id.login_register_fab);

        //ImageView imageView = (ImageView)view.findViewById(R.id.tour_book_logo_iv);
        //GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(imageView);
        //Glide.with(getActivity()).load(R.raw.tourbooklogogif).into(imageViewTarget);

        databaseManager=new DatabaseManager(getActivity());
        checkPreference=getActivity().getSharedPreferences(CHECK,Context.MODE_PRIVATE);
        checkEditor=checkPreference.edit();

        rememberMeCb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((CheckBox)v).isChecked())
                    checked=true;
                else
                    checked=false;
            }
        });

        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(errorChecker.setBlankError(userNameEt)|errorChecker.setBlankError(passEt))
                {
                    Snackbar.make(v,"\t\t\t\t\t\t\t\t\t\t"+"Please complete the required fields",Snackbar.LENGTH_SHORT).show();

                }
                else
                {
                    logIn(userNameEt.getText().toString(),passEt.getText().toString());
                }
            }
        });
        fab.setBackgroundColor(Color.RED);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.pre_main_fragment_container,new SignUpFragment()).addToBackStack(null).commit();
            }
        });
        if(getArguments().getString(AUTO_LOG_TAG).equals(AUTO_LOG_PERMISSION_TRUE))
        {
            if(getCheckPreference().equals(TRUE))
            {
                autoLogIn();
            }
        }
        else if(getArguments().getString(AUTO_LOG_TAG).equals(AUTO_LOG_PERMISSION_FALSE))
        {
            putCheckPreference(false);
        }


        return view;
    }

    private void putCheckPreference(Boolean checkFlag)
    {
        checkEditor.putString(CHECK,checkFlag.toString());
        checkEditor.commit();
    }
    private String getCheckPreference()
    {
       return checkPreference.getString(CHECK,DEFAULT);
    }

    private void logIn(String userName,String pass)
    {

        Cursor cursor=databaseManager.getAllUserInfos();
        cursor.moveToFirst();
        for (int i=0;i<cursor.getCount();i++)
        {
            String userNameCursor=cursor.getString(cursor.getColumnIndex(Constants.USER_NAME));
            String passCursor=cursor.getString(cursor.getColumnIndex(Constants.PASSWORD));
            if(matchPass(userName,pass,userNameCursor,passCursor))
            {
                if(!autoLogFlag)
                {
                    putCheckPreference(checked);
                    if(checked)
                    {

                        rememberMe.putUserPass(userName,pass);
                    }
                    Variables.CURRENT_USER_NAME=userName;
                }
                else
                {
                    Variables.CURRENT_USER_NAME=userName;
                }
                databaseManager.closeDatabase();
                fragment=new ShowAllEventsFragment();
                final ProgressDialog logInDialog=new ProgressDialog(getActivity());
                logInDialog.setTitle(userName);
                logInDialog.setMessage("Logging In");
                logInDialog.show();
                //final ProgressDialog progress = new ProgressDialog(this);
               // progress.setTitle("Connecting");
                //progress.setMessage("Please wait while we connect to devices...");
                //progress.show();

               /* Runnable progressRunnable = new Runnable() {

                    @Override
                    public void run() {
                        logInDialog.cancel();
                    }
                };*/


                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        logInDialog.dismiss();



                    }

                }, 1500);
                getFragmentManager().beginTransaction()
                        .replace(R.id.pre_main_fragment_container,fragment)
                        .commit();


                //Handler pdCanceller = new Handler();
                //pdCanceller.postDelayed(progressRunnable, 1500);


                /*getFragmentManager().beginTransaction()
                .replace(R.id.pre_main_fragment_container,new ShowAllEventsFragment())
                .commit();*/


            }


            else
            cursor.moveToNext();
        }
    }

    private boolean matchPass(String userName, String pass, String userNameCursor, String passCursor)
    {
        Boolean status=false;
        if(userName.equals(userNameCursor))
        {
            if(pass.equals(passCursor))
            {
                status=true;
            }
            else
            {
                status=false;
                errorChecker.setShake(passEt);
                errorChecker.setPassError(passEt,ERROR_MSG_PASS);
            }
        }
        else
        {
            status=false;
            errorChecker.setShake(userNameEt);
            errorChecker.setShake(passEt);
            errorChecker.setPassError(userNameEt,ERROR_MSG);
            errorChecker.setPassError(passEt,ERROR_MSG_PASS);
        }
        return status;
    }

    private void autoLogIn()
    {
        autoLogFlag=true;
        String userName=rememberMe.getUser();
        String password=rememberMe.getPass();
        logIn(userName,password);

    }


}

