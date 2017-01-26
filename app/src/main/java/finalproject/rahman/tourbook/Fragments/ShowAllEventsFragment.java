package finalproject.rahman.tourbook.Fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

import finalproject.rahman.tourbook.Adapters.RecyclerAdapter;
import finalproject.rahman.tourbook.Database.Constants;
import finalproject.rahman.tourbook.Database.DatabaseManager;
import finalproject.rahman.tourbook.Database.Variables;
import finalproject.rahman.tourbook.Dialogs.DatePickerCustomDialog;
import finalproject.rahman.tourbook.PreMainActivity;
import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/9/2017.
 */

public class ShowAllEventsFragment extends Fragment {
    private View view;

    private RecyclerView recyclerView;
    private LinearLayoutManager  manager;
    private RecyclerAdapter adapter;

    private FloatingActionButton addEventFab;

    private DatabaseManager databaseManager;

    private ArrayList<String> eventList;
    private ArrayList<String> startLocationList;
    private ArrayList<String> destinationList;
    private ArrayList<String> createdOnList;
    private ArrayList<Integer> startDayList;
    private ArrayList<Integer> startMonthList;
    private ArrayList<Integer> startYearList;
    private ArrayList<Integer> startHourList;
    private ArrayList<Integer> startMinList;
    private ArrayList<Integer> budgetList;
    private ArrayList<String> daysLeftList;


    public ShowAllEventsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_show_all_event,container,false);
       // Toast.makeText(getActivity(), "ShowAll created", Toast.LENGTH_SHORT).show();

        FragmentManager fm = getActivity().getSupportFragmentManager();
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }

        eventList=new ArrayList<String>();
        startLocationList=new ArrayList<String>();
        destinationList=new ArrayList<String>();
        createdOnList=new ArrayList<String>();
        startDayList=new ArrayList<Integer>();
        startMonthList=new ArrayList<Integer>();
        startYearList=new ArrayList<Integer>();
        startHourList=new ArrayList<Integer>();
        startMinList=new ArrayList<Integer>();
        budgetList=new ArrayList<Integer>();
        daysLeftList=new ArrayList<String>();

        Variables.SHOW_MENU=true;

        databaseManager=new DatabaseManager(getActivity());
        PreMainActivity.collapsingToolbarLayout.setTitle("Events Created");
        addEventFab = (FloatingActionButton)view.findViewById(R.id.showallevent_fab);
        addEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.pre_main_fragment_container,new AddEventFragment())
                        .addToBackStack(null).commit();
            }
        });
        getAllEvents(Variables.CURRENT_USER_NAME);
        recyclerView = (RecyclerView)view.findViewById(R.id.showallevent_recycler_view);
        manager = new LinearLayoutManager(getActivity());
        adapter = new RecyclerAdapter(eventList,startLocationList,destinationList
                ,startDayList,startMonthList,startYearList,startHourList,startMinList
                ,budgetList,createdOnList,daysLeftList);

        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        return view;
    }
    private void getAllEvents(String currentUserName)
    {
        Cursor cursor = databaseManager.getEventInfos(currentUserName);
        cursor.moveToFirst();
        //Toast.makeText(getActivity(), ""+cursor.getCount(), Toast.LENGTH_SHORT).show();
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
            String createdOn=cursor.getString(cursor.getColumnIndex(Constants.CREATED_ON));

            String startInMillis = cursor.getString(cursor.getColumnIndex(Constants.STRAT_TIME_MILLIS));
            //Toast.makeText(getActivity(), "fromdb:"+startInMillis+"", Toast.LENGTH_SHORT).show();
            Calendar calendar =Calendar.getInstance();
            //Toast.makeText(getActivity(), "after long:"+Long.valueOf(startInMillis)+"", Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), "now:"+calendar.getTimeInMillis()+"", Toast.LENGTH_SHORT).show();


            startInMillis=getDaysLeft(Long.valueOf(startInMillis),calendar.getTimeInMillis());
            //Toast.makeText(getActivity(), startInMillis+"", Toast.LENGTH_SHORT).show();


            eventList.add(eventName);
            startLocationList.add(startLocation);
            destinationList.add(destination);
            startDayList.add(day);
            startMonthList.add(month);
            startYearList.add(year);
            startHourList.add(hour);
            startMinList.add(min);
            budgetList.add(budget);
            createdOnList.add(createdOn);
            daysLeftList.add(startInMillis);

            cursor.moveToNext();

        }
        databaseManager.closeDatabase();
    }

    private int getDaysLeft(DatePickerCustomDialog datePicker,ArrayList<Integer> startDayList
            ,ArrayList<Integer> startMonthList,ArrayList<Integer> startYearList)
    {
        for(int object:startDayList)
        {
            //object
        }
        datePicker.getCurrentDay();
        datePicker.getCurrentMonth();
        datePicker.getCurrentYear();
        return 0;
    }
    public String getDaysLeft(long startMilliseconds,long currentMilliseconds)
    {
        if(startMilliseconds>currentMilliseconds)
        {
            long temp = (startMilliseconds-currentMilliseconds);
            //Toast.makeText(getActivity(),"left:"+ (startMilliseconds-currentMilliseconds)+"", Toast.LENGTH_SHORT).show();
            //int daysLeft= (int)(temp/(24*60*60*1000));
            int daysLeft= (int)(temp/1000/60/60/24);
            //Toast.makeText(getActivity(), (temp/(24*60*60*1000))+"", Toast.LENGTH_SHORT).show();
            temp = temp%(24*60*60*1000);
            int hoursLeft=(int)(temp/(60*60*1000));
            temp=temp%(60*60*1000);
            int minLeft=(int)(temp/(60*1000));
            String timeLeft="Time Left:\n"+daysLeft+" days "+hoursLeft+" Hrs "+minLeft+" Min ";
            return timeLeft;
        }
        else
        {
            String timeleft="Journey Started";
            return timeleft;
        }

    }


}
