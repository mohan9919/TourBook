package finalproject.rahman.tourbook.Fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import finalproject.rahman.tourbook.Controls.ErrorChecker;
import finalproject.rahman.tourbook.Database.Constants;
import finalproject.rahman.tourbook.Database.DatabaseManager;
import finalproject.rahman.tourbook.Database.Variables;
import finalproject.rahman.tourbook.MainActivity;
import finalproject.rahman.tourbook.PreMainActivity;
import finalproject.rahman.tourbook.R;

import static android.R.attr.fragment;

/**
 * Created by rahma on 12/31/2016.
 */

public class SignUpFragment extends android.support.v4.app.Fragment {
    private View view;

    private Boolean insertionFlag=false;

    private ErrorChecker errorChecker;

    private EditText userNameEt;
    private EditText emailEt;
    private EditText passEt;
    private EditText retypePassEt;
    private EditText phoneEt;
    private TextView registerTv;

    Bundle bundle=new Bundle();

    private String userName;
    private String password;
    private String email;
    private String phone;

    private DatabaseManager databaseManager;

    private final static String ERROR_MSG = "Must be filled";
    private final static String ERROR_MSG_PASS="Password Didn't match";

    public SignUpFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_sign_up,container,false);

        PreMainActivity.collapsingToolbarLayout.setTitle("Sign Up");
        Variables.SHOW_MENU=false;

        databaseManager=new DatabaseManager(getActivity());
        errorChecker=new ErrorChecker(getActivity());

        userNameEt = (EditText)view.findViewById(R.id.signup_user_name_et);
        emailEt = (EditText)view.findViewById(R.id.signup_email_et);
        passEt = (EditText)view.findViewById(R.id.signup_password_et);
        retypePassEt = (EditText)view.findViewById(R.id.signup_retype_pass_et);
        phoneEt = (EditText)view.findViewById(R.id.signup_phone_et);

        registerTv =(TextView)view.findViewById(R.id.signup_register_tv);

        registerTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(errorChecker.setBlankError(userNameEt)|errorChecker.setBlankError(emailEt)
                        |errorChecker.setBlankError(passEt)|errorChecker.setBlankError(retypePassEt)
                        |errorChecker.setBlankError(phoneEt))
                {
                    Snackbar.make(view,"\t\t\t\t\t\t\t\t\t\t\t\t\tPlease Fill All The Fields Above",Snackbar.LENGTH_LONG).show();
                }
                else
                {
                    if(errorChecker.setWhiteSpaceError(userNameEt)|errorChecker.setWhiteSpaceError(emailEt)
                            |errorChecker.setWhiteSpaceError(phoneEt))
                    {
                        Toast.makeText(getActivity(),"Whitespaces are not allowed",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if(errorChecker.setEmailFormatError(emailEt))
                        {
                            Toast.makeText(getActivity(),"Invalid Email Address",Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            //Register to DataBase
                            if(errorChecker.checkEqualPass(passEt,retypePassEt))
                            {
                                try
                                {
                                    insertionFlag=databaseManager.insertUserInfo(makeBundle());

                                    //Toast.makeText(getActivity(), insertionFlag+"", Toast.LENGTH_SHORT).show();

                                }catch (SQLiteConstraintException e)
                                {
                                    String failedToInsert="";
                                    e.printStackTrace();
                                    switch (e.getMessage().substring(7,8))
                                    {
                                        case "u" :
                                            failedToInsert="User Name";
                                            break;
                                        case "e" :
                                            failedToInsert="Email Address";
                                            break;
                                        case "p" :
                                            failedToInsert="Phone Number";
                                            break;

                                    }
                                   // Toast.makeText(getActivity(), e.getMessage()+"", Toast.LENGTH_SHORT).show();

                                    Toast.makeText(getActivity(), "The "+failedToInsert+" is already registered", Toast.LENGTH_SHORT).show();
                                }
                                if(insertionFlag)
                                {
                                    bundle.putString(PreMainActivity.AUTO_LOG_TAG,PreMainActivity.AUTO_LOG_PERMISSION_FALSE);
                                    android.support.v4.app.Fragment fragment=new LogInFragment();
                                    fragment.setArguments(bundle);
                                    Snackbar.make(view,"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t"+userName.toUpperCase()+" is a now Registered",Snackbar.LENGTH_LONG).show();
                                    getFragmentManager().beginTransaction().replace(R.id.pre_main_fragment_container,fragment).commit();
                                }


                        /*getFragmentManager().beginTransaction()
                        .replace(R.id.pre_main_fragment_container,new LogInFragment()).commit();*/
                            }

                            else
                            {
                                errorChecker.setShake(passEt);
                                errorChecker.setShake(retypePassEt);
                                errorChecker.setPassError(passEt,ERROR_MSG_PASS);
                                errorChecker.setPassError(retypePassEt,ERROR_MSG_PASS);
                            }
                        }

                    }




                }
            }
        });

        return view;
    }

    private Bundle makeBundle()
    {
        userName=userNameEt.getText().toString();
        password=passEt.getText().toString();
        email=emailEt.getText().toString();
        phone=phoneEt.getText().toString();

        bundle.putString(Constants.USER_NAME,userName);
        bundle.putString(Constants.PASSWORD,password);
        bundle.putString(Constants.EMAIL,email);
        bundle.putString(Constants.PHONE,phone);

        return bundle;
    }

}
