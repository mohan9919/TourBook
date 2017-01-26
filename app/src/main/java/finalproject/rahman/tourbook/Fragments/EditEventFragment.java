package finalproject.rahman.tourbook.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import finalproject.rahman.tourbook.Controls.ErrorChecker;
import finalproject.rahman.tourbook.Database.Constants;
import finalproject.rahman.tourbook.Database.DatabaseManager;
import finalproject.rahman.tourbook.Database.Variables;
import finalproject.rahman.tourbook.Dialogs.DatePickerCustomDialog;
import finalproject.rahman.tourbook.Dialogs.TimePickerCustomDialog;
import finalproject.rahman.tourbook.Model.Expense;
import finalproject.rahman.tourbook.PreMainActivity;
import finalproject.rahman.tourbook.R;





public class EditEventFragment extends Fragment {
    private View view;

    private ErrorChecker errorChecker;

    private EditText eventNameEt;
    private EditText startLocationEt;
    private EditText destinationEt;
    private EditText startDateEt;
    private EditText startTimeEt;
    private EditText budgetEt;
    private TextView editEventTv;


    private String eventName;
    private String startLocation;
    private String destination;
    private String startDate;
    private String startTime;
    private int totalBudget;
    private int budgetLeft;
    private int budgetSpent;
    private String startTimeMillis;

    private int day;
    private int month;
    private int year;
    private int hour;
    private int min;



    private DatePickerCustomDialog startDatePicker;
    private TimePickerCustomDialog startTimePicker;

    private Calendar calendar;

    private DatabaseManager databaseManager;
    private Bundle bundle;
    private Cursor cursor;

    private int pickedDate;
    private int pickedMonth;
    private int pickedYear;
    private int currentDate;
    private int currentMonth;
    private int currentYear;

    int pickedHour;
    int pickedMin;
    int currentHour;
    int currentMin;
    private String pickedStartTimeInMillis;

    private boolean updateFlag=false;



    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_edit_event,null,false);
       // Toast.makeText(getActivity(), "EditEvent created", Toast.LENGTH_SHORT).show();

        PreMainActivity.collapsingToolbarLayout.setTitle("Edit Event");
        connectAllViewComponents(view);

        errorChecker = new ErrorChecker(getActivity());

        eventName=getArguments().getString(Constants.EVENT_NAME);

        //Toast.makeText(getActivity(), eventName+"", Toast.LENGTH_SHORT).show();

        databaseManager=new DatabaseManager(getActivity());
        getEventInfo(eventName);
        setTextToAllEditTexts();
        setEditTextEditability(day,month,year,hour,min);

        startDatePicker=new DatePickerCustomDialog(getActivity());
        startTimePicker=new TimePickerCustomDialog(getActivity());

        bundle=new Bundle();
        //Done upway//

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

//Done Above//

        editEventTv.setOnClickListener(new View.OnClickListener() {
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

                        if(Variables.EDIT_DATE_PICKED)
                        {
                            //Variables.EDIT_DATE_PICKED=false;
                            Variables.EDIT_DATE_PICKED=false;
                            pickedDate=startDatePicker.getDay();
                            pickedMonth=startDatePicker.getMonth();
                            pickedYear=startDatePicker.getYear();
                            Calendar calendar=Calendar.getInstance();

                            currentDate=calendar.get(Calendar.DATE);
                            currentMonth=calendar.get(Calendar.MONTH);
                            currentYear=calendar.get(Calendar.YEAR);
                            /*Calendar currentDateCal=Calendar.getInstance();
                            currentDateCal.getTimeInMillis();*/
                        }
                        else
                        {
                            pickedDate=day;
                            pickedMonth=month;
                            pickedYear=year;
                            pickedStartTimeInMillis=startTimeMillis;
                            Calendar currentCal=Calendar.getInstance();
                            currentDate=currentCal.get(Calendar.DATE);
                            currentMonth=currentCal.get(Calendar.MONTH)+1;
                            currentYear=currentCal.get(Calendar.YEAR);
                        }



                        if(errorChecker.setDateError(startDateEt,pickedDate,pickedMonth,pickedYear,currentDate,currentMonth,currentYear))
                        {
                           // Toast.makeText(getActivity(), "Time Travel", Toast.LENGTH_SHORT).show();

                        }
                        else
                        {
                            if(pickedDate==currentDate&&pickedMonth==currentMonth&&pickedYear==currentYear)
                            {
                                if(Variables.EDIT_TIME_PICKED)
                                {

                                    pickedHour=startTimePicker.getHour();
                                    pickedMin=startTimePicker.getMinute();
                                    Calendar calendar=Calendar.getInstance();

                                    currentHour=calendar.get(Calendar.HOUR_OF_DAY);;
                                    currentMin=calendar.get(Calendar.MINUTE);
                                }
                                else
                                {
                                    pickedHour=hour;
                                    pickedMin=min;
                                    Calendar currentTime=Calendar.getInstance();
                                    currentHour=currentTime.get(Calendar.HOUR_OF_DAY);
                                    currentMin=currentTime.get(Calendar.MINUTE);
                                }

                                if(startTimeEt.isEnabled())
                                {
                                    if(errorChecker.setTimeError(startTimeEt,pickedHour,pickedMin,currentHour,currentMin))
                                    {

                                       // Toast.makeText(getActivity(), "Time Travel is not allowed", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        if(Integer.valueOf(budgetEt.getText().toString())<budgetSpent)
                                        {
                                            budgetEt.setError("Budget Must be More than what you already have spent");
                                        }
                                        else
                                        {
                                            budgetLeft=Integer.valueOf(budgetEt.getText().toString())-budgetSpent;

                                            //Snackbar.make(view,"SS",Snackbar.LENGTH_LONG).show();
                                            setPickedStartTimeMillis();

                                            updateFlag=databaseManager.updateEventInfo(eventName,makeBundle());
                                            //Register to DataBase
                                            //Toast.makeText(getActivity(), "update: "+updateFlag, Toast.LENGTH_SHORT).show();
                                            getFragmentManager().beginTransaction().replace(R.id.pre_main_fragment_container,new ShowAllEventsFragment()).commit();
                                            //getFragmentManager().beginTransaction().remove()
                                            Variables.EDIT_DATE_PICKED=false;
                                            Variables.EDIT_TIME_PICKED=false;
                                            //showAllEvents();
                                        }
                                    }
                                }
                                else
                                {
                                    if(Integer.valueOf(budgetEt.getText().toString())<budgetSpent)
                                    {
                                        budgetEt.setError("Budget Must be More than what you already have spent");
                                    }
                                    else
                                    {
                                        budgetLeft=Integer.valueOf(budgetEt.getText().toString())-budgetSpent;

                                        Snackbar.make(view,"SS",Snackbar.LENGTH_LONG).show();
                                        setPickedStartTimeMillis();

                                        updateFlag=databaseManager.updateEventInfo(eventName,makeBundle());
                                        //Register to DataBase
                                       // Toast.makeText(getActivity(), "update: "+updateFlag, Toast.LENGTH_SHORT).show();
                                        getFragmentManager().beginTransaction().replace(R.id.pre_main_fragment_container,new ShowAllEventsFragment()).commit();
                                        Variables.EDIT_DATE_PICKED=false;
                                        Variables.EDIT_TIME_PICKED=false;
                                        //showAllEvents();
                                    }
                                }


                            }
                            else if(pickedDate>currentDate&&pickedMonth>=currentMonth&&pickedYear>=currentYear)
                            {
                                if(Variables.EDIT_TIME_PICKED)
                                {
                                    pickedHour=startTimePicker.getHour();
                                    pickedMin=startTimePicker.getMinute();
                                    currentHour=startTimePicker.getCurrentHour();
                                    currentMin=startTimePicker.getCurrentMinute();
                                }
                                else
                                {
                                    pickedHour=hour;
                                    pickedMin=min;
                                    Calendar currentTime=Calendar.getInstance();
                                    currentHour=currentTime.get(Calendar.HOUR_OF_DAY);
                                    currentMin=currentTime.get(Calendar.MINUTE);
                                }


                                    if(Integer.valueOf(budgetEt.getText().toString())<budgetSpent)
                                    {
                                        budgetEt.setError("Budget Must be More than what you already have spent");
                                    }
                                    else
                                    {
                                        budgetLeft=Integer.valueOf(budgetEt.getText().toString())-budgetSpent;

                                        setPickedStartTimeMillis();

                                        updateFlag=databaseManager.updateEventInfo(eventName,makeBundle());
                                        //Register to DataBase
                                       // Toast.makeText(getActivity(), ""+updateFlag, Toast.LENGTH_SHORT).show();
                                        Variables.EDIT_DATE_PICKED=false;
                                        Variables.EDIT_TIME_PICKED=false;
                                        getFragmentManager().beginTransaction().replace(R.id.pre_main_fragment_container,new ShowAllEventsFragment()).commit();
                                        //showAllEvents();
                                    }


                            }



                        }
                    }
                }
            }
        });

        return view;
    }

    private void setPickedStartTimeMillis()
    {
            calendar.set(pickedYear,pickedMonth-1,pickedDate,pickedHour,pickedMin);
            pickedStartTimeInMillis=String.valueOf(calendar.getTimeInMillis());
    }

    private Bundle makeBundle()
    {
       // String userName= Variables.CURRENT_USER_NAME;
        String eventName=eventNameEt.getText().toString();
        String startLocation=startLocationEt.getText().toString();
        String destination=destinationEt.getText().toString();
        int day=pickedDate;
        int month=pickedMonth;
        int year=pickedYear;
        int hour=pickedHour;
        int min=pickedMin;
        int budget=Integer.valueOf(budgetEt.getText().toString());
        String startTimeMillis=pickedStartTimeInMillis;

        //bundle.putString(Constants.USER_NAME,userName);
        bundle.putString(Constants.EVENT_NAME,eventName);
        bundle.putString(Constants.START_LOCATION,startLocation);
        bundle.putString(Constants.DESTINATION,destination);
        bundle.putInt(Constants.START_DATE_DAY,day);
        bundle.putInt(Constants.START_DATE_MONTH,month);
        bundle.putInt(Constants.START_DATE_YEAR,year);
        bundle.putInt(Constants.START_TIME_HOUR,hour);
        bundle.putInt(Constants.START_TIME_MIN,min);
        bundle.putInt(Constants.BUDGET,budget);
        bundle.putInt(Constants.BUDGET_LEFT,budgetLeft);
        bundle.putInt(Constants.BUDGET_SPENT,budgetSpent);
        bundle.putString(Constants.STRAT_TIME_MILLIS,startTimeMillis);


        return bundle;
    }
   //DONE BELOW//
    private void getEventInfo(String eventName)
    {  // DatabaseManager data=new DatabaseManager(getActivity());
        try
        {
            cursor=databaseManager.getEventDeatailInfos(eventName);
            cursor.moveToFirst();
            this.eventName=cursor.getString(cursor.getColumnIndex(Constants.EVENT_NAME));
            startLocation=cursor.getString(cursor.getColumnIndex(Constants.START_LOCATION));
            destination=cursor.getString(cursor.getColumnIndex(Constants.DESTINATION));
            day=cursor.getInt(cursor.getColumnIndex(Constants.START_DATE_DAY));
            month=cursor.getInt(cursor.getColumnIndex(Constants.START_DATE_MONTH));
            year=cursor.getInt(cursor.getColumnIndex(Constants.START_DATE_YEAR));
            hour=cursor.getInt(cursor.getColumnIndex(Constants.START_TIME_HOUR));
            min=cursor.getInt(cursor.getColumnIndex(Constants.START_TIME_MIN));
            startDate=day+"/"+month+"/"+year;
            startTime=hour+":"+min;
            totalBudget=cursor.getInt(cursor.getColumnIndex(Constants.BUDGET));
            budgetLeft=cursor.getInt(cursor.getColumnIndex(Constants.BUDGET_LEFT));
            budgetSpent=cursor.getInt(cursor.getColumnIndex(Constants.BUDGET_SPENT));
            startTimeMillis=cursor.getString(cursor.getColumnIndex(Constants.STRAT_TIME_MILLIS));
            cursor.close();
            databaseManager.closeDatabase();

        }catch (Exception e)
        {
           // Toast.makeText(getActivity(), e.getMessage()+"", Toast.LENGTH_SHORT).show();

        }
        //cursor.moveToFirst();
        //Toast.makeText(getActivity(), cursor.getCount()+"", Toast.LENGTH_SHORT).show();

    }

    public void connectAllViewComponents(View view)
    {
        eventNameEt = (EditText)view.findViewById(R.id.editevent_event_name_et);
        startLocationEt = (EditText)view.findViewById(R.id.editevent_starting_location_et);
        destinationEt = (EditText)view.findViewById(R.id.editevent_destination_et);
        startDateEt = (EditText)view.findViewById(R.id.editevent_departure_date_et);
        startTimeEt = (EditText)view.findViewById(R.id.editevent_departure_time_et);
        budgetEt = (EditText)view.findViewById(R.id.editevent_budget_et);
        editEventTv=(TextView)view.findViewById(R.id.editevent_add_tv);
    }
    /*public void setEditability()
    {

    }*/

   /* public void setEventNameEditability(int startDate,int startMonth,int startYear
                                    ,int startHour,int startMin
                                    , int currentDate,int currentMonth,int currentYear
                                    ,int currentHour,int currentMin)
    {

        calendar = Calendar.getInstance();
        int seconds = calendar.get(Calendar.MINUTE);
    }*/
   public void setEditTextEditability(int startDate,int startMonth,int startYear
           ,int startHour,int startMin) {
       calendar = Calendar.getInstance();
       int currentyear = calendar.get(Calendar.YEAR);
       int currentMonth = calendar.get(Calendar.MONTH) + 1;
       int currentDate = calendar.get(Calendar.DATE);
       int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
       int currentMin = calendar.get(Calendar.MINUTE);
       if (startYear > currentyear) {
           eventNameEt.setEnabled(true);
       } else if (startYear < currentyear) {
           setAllFalse();
       } else if (startYear == currentyear) {

           if (startMonth > currentMonth) {
               eventNameEt.setEnabled(true);
           } else if (startMonth < currentMonth) {
               setAllFalse();
           } else if (startMonth == currentMonth) {
               if (startDate > currentDate) {
                   eventNameEt.setEnabled(true);
               } else if (startDate < currentDate) {
                   setAllFalse();
               } else if (startDate == currentDate) {
                   if (startHour > currentHour) {
                       eventNameEt.setEnabled(true);
                   } else if (startHour < currentHour) {
                       setAllFalse();
                   } else if (startHour == currentHour) {
                       if (startMin > currentMin) {

                       } else if (startMin <= currentMin) {
                           setAllFalse();
                       }

                       eventNameEt.setEnabled(false);
                   }

               }
           }
       }

   }
    public void setTextToAllEditTexts()
    {
        eventNameEt.setText(eventName);
        startLocationEt.setText(startLocation);
        startLocationEt.setText(startLocation);
        destinationEt.setText(destination);
        startDateEt.setText(startDate);
        startTimeEt.setText(startTime);
        budgetEt.setText(String.valueOf(totalBudget));
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

    public void setAllFalse()
    {
        eventNameEt.setEnabled(false);
        startLocationEt.setEnabled(false);
        destinationEt.setEnabled(false);
        startDateEt.setEnabled(false);
        startTimeEt.setEnabled(false);
    }





}
