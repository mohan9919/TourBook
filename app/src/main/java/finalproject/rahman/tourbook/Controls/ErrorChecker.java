package finalproject.rahman.tourbook.Controls;

import android.content.Context;
import android.util.Patterns;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/6/2017.
 */

public class ErrorChecker {
    Context context;
    public ErrorChecker(Context context) {
        this.context=context;
    }

    private final static String ERROR_MSG = "Must be filled";
    private final static String ERROR_MSG_PASS="Password Didn't match";
    private final static String ERROR_MSG_WHITESPACE="White Spaces are not allowed";
    private final static String ERROR_MSG_EMAIL_FORMAT="Invalid Email Address";
    private final static String ERROR_MSG_LENGTH="Too much characters";
    private final static String ERROR_MSG_START_DATE_TIME="Time travel is only available for Premium Users. Upgrade Today";
    private String blank = "";
    private String whiteSpace=" ";
    private Animation anim;

    public boolean checkEqualPass(EditText pass, EditText retypePass)
    {
        String password = pass.getText().toString();
        String retypePassword = retypePass.getText().toString();
        if(password.equals(retypePassword))
            return true;
        else
            return false;
    }

    private boolean checkBlankInput(EditText editText)
    {
        if(editText.getText().toString().equals(blank))
            return true;
        else
            return false;
    }
    public boolean setPassError(EditText editText,String errorMsg)
    {
        editText.setError(errorMsg);
        return true;
    }

    public boolean setBlankError(EditText editText)
    {
        if(checkBlankInput(editText))
        {
            editText.setError(ERROR_MSG);
            return true;
        }
        else
            return false;
    }
    public void setShake(EditText edittext)
    {
        anim= AnimationUtils.loadAnimation(context, R.anim.shake);
        edittext.startAnimation(anim);
    }
    private boolean whiteSpaceChecker(EditText editText)
    {
        boolean status=false;
        String string=editText.getText().toString();
        int length=string.length();

        for(int i=0; i<length; i++)
        {
            if(string.substring(i,i+1).equals(whiteSpace))
            {
                status=true;
                break;
            }


        }
        return status;
    }
    public boolean setWhiteSpaceError(EditText editText)
    {
        if(whiteSpaceChecker(editText))
        {
            editText.setError(ERROR_MSG_WHITESPACE);
            return true;
        }
        else
            return false;


    }
    private boolean emailFormatChecker(EditText editText)
    {
        String email=editText.getText().toString();
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public boolean setEmailFormatError(EditText editText)
    {
        if(!emailFormatChecker(editText))
        {
            editText.setError(ERROR_MSG_EMAIL_FORMAT);
            return true;
        }
        else
            return false;

    }
    private boolean checkLength(EditText editText,int lengthLimit)
    {
        if(editText.getText().toString().length()>lengthLimit)
            return true;
        else
            return false;

    }
    public boolean setLengthError(EditText editText,int lengthLimit)
    {
        if(checkLength(editText,lengthLimit))
        {
            editText.setError(ERROR_MSG_LENGTH);
            return true;
        }

        else
            return false;
    }
    private boolean checkStartDate(int pickedDay,int pickedMonth,int pickedYear
                                  ,int currentDay,int currentMonth,int currentYear)
    {
        boolean status=false;
        if(checkYear(pickedYear,currentYear))
        {
            status=true;
        }

        else
        {
            if(checkMOnth(pickedMonth,currentMonth))
            {
                status=true;
            }
            else
            {
                if(checkDay(pickedDay,currentDay))
                {
                    status=true;
                }
            }

        }
            return status;
    }
    public boolean setDateError(EditText editText,int pickedDay,int pickedMonth,int pickedYear
            ,int currentDay,int currentMonth,int currentYear)
    {
        if(checkStartDate(pickedDay,pickedMonth,pickedYear,currentDay,currentMonth,currentYear))
        {
            editText.setError(ERROR_MSG_START_DATE_TIME);
            return true;
        }
        else
            return false;
    }
    private boolean checkYear(int pickedYear,int currentYear)
    {
        if(pickedYear<currentYear)
            return true;
        else
            return false;
    }
    private boolean checkMOnth(int pickedMonth,int currentMonth)
    {
        if(pickedMonth<currentMonth)
            return true;
        else
            return false;
    }
    private boolean checkDay(int pickedDay,int currentDay)
    {
        if(pickedDay<currentDay)
            return true;
        else
            return false;
    }
    private boolean checkHour(int pickedHour,int currentHour)
    {

        if(pickedHour<currentHour)
            return true;
        else
            return false;
    }
    private boolean checkMinute(int pickedMin,int currentMin)
    {
        if(pickedMin<currentMin)
            return true;
        else
            return false;
    }
    private boolean checkTimeToday(int pickedHour,int pickedMin,int curretntHour,int currentMin)
    {   boolean status=false;
        if (checkHour(pickedHour,curretntHour))
        {
            status = true;
        }
        else
        {
            if(checkMinute(pickedMin,currentMin))
            {
                status=true;
            }
        }

        return status;
    }
    public boolean setTimeError(EditText editText,int pickedHour,int pickedMin,int currentHour,int currentMin)
    {
        if(checkTimeToday(pickedHour,pickedMin,currentHour,currentMin))
        {
            editText.setError(ERROR_MSG_START_DATE_TIME);
            return true;
        }
        else
            return false;
    }

}

