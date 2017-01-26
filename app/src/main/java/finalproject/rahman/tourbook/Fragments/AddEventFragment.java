package finalproject.rahman.tourbook.Fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Locale;

import finalproject.rahman.tourbook.Controls.ErrorChecker;
import finalproject.rahman.tourbook.Database.Constants;
import finalproject.rahman.tourbook.Database.CurrentUserName;
import finalproject.rahman.tourbook.Database.DatabaseManager;
import finalproject.rahman.tourbook.Database.Variables;
import finalproject.rahman.tourbook.Dialogs.DatePickerCustomDialog;
import finalproject.rahman.tourbook.Dialogs.TimePickerCustomDialog;
import finalproject.rahman.tourbook.PreMainActivity;
import finalproject.rahman.tourbook.R;

/**
 * Created by Bdjobs on 1/3/2017.
 */

public class AddEventFragment extends Fragment {
    private View view;

    private ErrorChecker errorChecker;

    private EditText eventNameEt;
    private EditText startLocationEt;
    private EditText destinationEt;
    private EditText startDateEt;
    private EditText startTimeEt;
    private EditText budgetEt;
    private TextView addEventTv;


    private DatePickerCustomDialog startDatePicker;
    private TimePickerCustomDialog startTimePicker;

    private DatabaseManager databaseManager;
    private Bundle bundle;

    private boolean insertionFlag=false;

    private java.util.Calendar pickedMillisCal;



    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_add_event,container,false);
        PreMainActivity.collapsingToolbarLayout.setTitle("Add Event");
        PreMainActivity.collapsingToolbarLayout.setTitle("Add Event");
        errorChecker = new ErrorChecker(getActivity());

        eventNameEt = (EditText)view.findViewById(R.id.addevent_event_name_et);
        startLocationEt = (EditText)view.findViewById(R.id.addevent_starting_location_et);
        destinationEt = (EditText)view.findViewById(R.id.addevent_destination_et);
        startDateEt = (EditText)view.findViewById(R.id.addevent_departure_date_et);
        startTimeEt = (EditText)view.findViewById(R.id.addevent_departure_time_et);
        budgetEt = (EditText)view.findViewById(R.id.addevent_budget_et);
        addEventTv=(TextView)view.findViewById(R.id.addEvent_add_tv);

       // Toast.makeText(getActivity(), , Toast.LENGTH_SHORT).show();

        startDatePicker=new DatePickerCustomDialog(getActivity());
        startTimePicker=new TimePickerCustomDialog(getActivity());

        pickedMillisCal= java.util.Calendar.getInstance();

        databaseManager=new DatabaseManager(getActivity());
        bundle=new Bundle();


        startDateEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    startDatePicker.datePickerCustomDialog(startDateEt);
                }

            }
        });

        startDateEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDatePicker.datePickerCustomDialog(startDateEt);
            }
        });
        startTimeEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus)
                {
                    startTimePicker.timePickerCustomDialog(startTimeEt);
                }

            }
        });

        startTimeEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimePicker.timePickerCustomDialog(startTimeEt);
            }
        });



        addEventTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //eventNameEt.setText(Constants.CREATE_USER_INFO_TABLE);
               // budgetEt.setText(Constants.CREATE_EVENT_INFO_TABLE);
                if(errorChecker.setBlankError(eventNameEt)|errorChecker.setBlankError(startLocationEt)
                        |errorChecker.setBlankError(destinationEt)|errorChecker.setBlankError(startDateEt)
                        |errorChecker.setBlankError(startTimeEt)|errorChecker.setBlankError(budgetEt))
                {
                    Toast.makeText(getActivity(), "Each Field is required", Toast.LENGTH_SHORT).show();
                }
                else

                {
                    if(errorChecker.setLengthError(eventNameEt,20))
                    {

                    }
                    else
                    {

                        int pickedDate=startDatePicker.getDay();
                        int pickedMonth=startDatePicker.getMonth();
                        int pickedYear=startDatePicker.getYear();
                        int currentDate=startDatePicker.getCurrentDay();
                        int currentMonth=startDatePicker.getCurrentMonth();
                        int currentYear =startDatePicker.getCurrentYear();
                        if(errorChecker.setDateError(startDateEt,pickedDate,pickedMonth,pickedYear,currentDate,currentMonth,currentYear))
                        {
                           // Toast.makeText(getActivity(), "Time Travel", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            if(pickedDate==currentDate&&pickedMonth==currentMonth&&pickedYear==currentYear)
                            {
                                int pickedHour=startTimePicker.getHour();
                                int pickedMin=startTimePicker.getMinute();
                                int currentHour=startTimePicker.getCurrentHour();
                                int currentMin=startTimePicker.getCurrentMinute();
                                if(errorChecker.setTimeError(startTimeEt,pickedHour,pickedMin,currentHour,currentMin))
                                {
                                    //Toast.makeText(getActivity(), "Time Travel is not allowed", Toast.LENGTH_SHORT).show();
                                }
                                else if(pickedHour==0 && pickedMin==0)
                                {
                                    startTimeEt.setError("Pick a valid time");
                                }
                                else
                                {

                                    //Snackbar.make(view,"SS",Snackbar.LENGTH_LONG).show();
                                    try
                                    {
                                        insertionFlag=databaseManager.insertEventInfo(makeBundle());
                                        Variables.EDIT_DATE_PICKED=false;
                                        Variables.EDIT_TIME_PICKED=false;

                                    }catch (Exception e)
                                    {
                                        Snackbar.make(view,"Event "+eventNameEt.getText().toString()+" has already been created",Snackbar.LENGTH_LONG).show();
                                    }
                                    //Register to DataBase
                                    //Toast.makeText(getActivity(), ""+insertionFlag, Toast.LENGTH_SHORT).show();
                                    getFragmentManager().beginTransaction().replace(R.id.pre_main_fragment_container,new ShowAllEventsFragment()).commit();

                                    //showAllEvents();
                                }
                            }
                            else
                            {



                                try
                                {
                                    insertionFlag=databaseManager.insertEventInfo(makeBundle());
                                    Variables.EDIT_DATE_PICKED=false;
                                    Variables.EDIT_TIME_PICKED=false;

                                }catch (Exception e)
                                {
                                    Snackbar.make(view,"Event "+eventNameEt.getText().toString()+" has already been created",Snackbar.LENGTH_LONG).show();
                                }
                                //Register to DataBase
                                //Toast.makeText(getActivity(), ""+insertionFlag, Toast.LENGTH_SHORT).show();
                                Variables.EDIT_DATE_PICKED=false;
                                Variables.EDIT_TIME_PICKED=false;
                                getFragmentManager().beginTransaction().replace(R.id.pre_main_fragment_container,new ShowAllEventsFragment()).commit();
                                //showAllEvents();
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
        String userName= Variables.CURRENT_USER_NAME;
        String eventName=eventNameEt.getText().toString();
        String startLocation=startLocationEt.getText().toString();
        String destination=destinationEt.getText().toString();
        int day=startDatePicker.getDay();
        int month=startDatePicker.getMonth();
        int year=startDatePicker.getYear();
        int hour=startTimePicker.getHour();
        int min=startTimePicker.getMinute();
        int budget=Integer.valueOf(budgetEt.getText().toString());
        pickedMillisCal.set(year,month-1,day,hour,min);
        String pickedTimeMillis= String.valueOf(pickedMillisCal.getTimeInMillis());
        String createdOn=startDatePicker.getCurrentDay()+"/"+startDatePicker.getCurrentMonth()
                +"/"+startDatePicker.getCurrentYear()+" "+startTimePicker.getCurrentHour()
                +":"+startTimePicker.getCurrentMinute();

        bundle.putString(Constants.USER_NAME,userName);
        bundle.putString(Constants.EVENT_NAME,eventName);
        bundle.putString(Constants.START_LOCATION,startLocation);
        bundle.putString(Constants.DESTINATION,destination);
        bundle.putInt(Constants.START_DATE_DAY,day);
        bundle.putInt(Constants.START_DATE_MONTH,month);
        bundle.putInt(Constants.START_DATE_YEAR,year);
        bundle.putInt(Constants.START_TIME_HOUR,hour);
        bundle.putInt(Constants.START_TIME_MIN,min);
        bundle.putInt(Constants.BUDGET,budget);
        bundle.putString(Constants.STRAT_TIME_MILLIS,pickedTimeMillis);
        bundle.putString(Constants.CREATED_ON,createdOn);

        return bundle;
    }

/*
    private void showAllEvents()
    {
        Cursor cursor = databaseManager.getEventInfos(Variables.CURRENT_USER_NAME);
        cursor.moveToFirst();
        Toast.makeText(getActivity(), ""+cursor.getCount(), Toast.LENGTH_SHORT).show();
        for (int i=0;i<cursor.getCount();i++)
        {
            String eventName=cursor.getString(cursor.getColumnIndex(Constants.EVENT_NAME));
            String startLocation=cursor.getString(cursor.getColumnIndex(Constants.START_LOCATION));
            String destination=cursor.getString(cursor.getColumnIndex(Constants.DESTINATION));
            int day=cursor.getInt(cursor.getColumnIndex(Constants.START_DATE_DAY));
            int month=cursor.getInt(cursor.getColumnIndex(Constants.START_DATE_MONTH));
            int year=cursor.getInt(cursor.getColumnIndex(Constants.START_DATE_YEAR));
            int hour=cursor.getInt(cursor.getColumnIndex(Constants.START_TIME_HOUR));
            int min=cursor.getInt(cursor.getColumnIndex(Constants.START_TIME_MIN));
            int budget=cursor.getInt(cursor.getColumnIndex(Constants.BUDGET));
            destinationEt.setText(destinationEt.getText().toString()+" "+eventName+startLocation+destination+
                                    day+month+year+hour+min+budget);
            cursor.moveToNext();

        }
        databaseManager.closeDatabase();
    }
*/



}
