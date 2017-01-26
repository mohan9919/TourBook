package finalproject.rahman.tourbook.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import finalproject.rahman.tourbook.Adapters.ExpandableListAdapter;
import finalproject.rahman.tourbook.Database.Constants;
import finalproject.rahman.tourbook.Database.DatabaseManager;
import finalproject.rahman.tourbook.Database.Variables;
import finalproject.rahman.tourbook.Dialogs.AddMoreBudgetDialog;
import finalproject.rahman.tourbook.Dialogs.AddNewExpenseDialog;
import finalproject.rahman.tourbook.Dialogs.ShowAllExpenseHistoryDialog;
//import finalproject.rahman.tourbook.MemoryActivity;
import finalproject.rahman.tourbook.Model.Expense;
import finalproject.rahman.tourbook.PreMainActivity;
import finalproject.rahman.tourbook.R;

import static finalproject.rahman.tourbook.R.string.budget;

/**
 * Created by rahma on 1/10/2017.
 */

public class EventDetailFragment extends Fragment {
    private View view;

    private TextView eventnameTv;
    private static TextView budgetStatusTv;
    private static TextView percentTv;
    private static ProgressBar progressBar;

    private Button addExpenseBtn;
    private Button showHistoryBtn;
    private Button addMoreBudgetBtn;
    private Button deleteExpenseHistoryBtn;
    private Button deleteEventBtn;
    private Button editEventBtn;
    private Button momentBtn;

    private AddNewExpenseDialog addNewExpDialog;
    private ShowAllExpenseHistoryDialog expenseHistoryDialog;
    private AddMoreBudgetDialog addBudgetDialog;

    public static ArrayList<Expense> expenseList;
    private static Expense expense;

    private String eventName;
    private static int totalBudget;
    private static int budgetLeft;
    private static int budgetSpent;
    private int progress;

    private ExpandableListAdapter expListAdapter;
    private ExpandableListView expListLv;
    private List<String> headerList;
    private HashMap<String,List<String>> childList;


    //int budgetInt;


    private DatabaseManager databasemanager;
    private Cursor historyCursor;

    public EventDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_event_detail,container,false);
       // Toast.makeText(getActivity(), "EventDetails created", Toast.LENGTH_SHORT).show();

        databasemanager = new DatabaseManager(getActivity());

        eventnameTv=(TextView)view.findViewById(R.id.eventdetail_event_name_tv);
        budgetStatusTv=(TextView)view.findViewById(R.id.eventdetail_budget_status_tv);
        percentTv=(TextView)view.findViewById(R.id.eventdetail_percentage_tv);
        progressBar=(ProgressBar)view.findViewById(R.id.eventdetail_expendature_percentage_pb);

        expenseList = new ArrayList<>();
        /*if(Variables.EVENT_NAME_UPDATED)
        {
            eventName=getArguments().get(Constants.SHOW_EVENT_UPDATED_DETAIL_BUNDLE).toString();


        }else
        {

        }*/
        eventName=getArguments().get(Constants.SHOW_EVENT_DETAIL_BUNDLE).toString();

        getEventDetails(eventName);
        PreMainActivity.collapsingToolbarLayout.setTitle("Event Details : "+eventName.toUpperCase());

        setBudgetStatus(budgetLeft,totalBudget);
        progress=setProgressbar(budgetSpent,totalBudget);
        percentTv.setText(progress+"%");
        eventnameTv.setText(eventName.toUpperCase());

        expListLv=(ExpandableListView)view.findViewById(R.id.eventdetailfragment_expandable_lists_el);
        makeLists();
        expListAdapter=new ExpandableListAdapter(getActivity(),headerList,childList);
        expListLv.setAdapter(expListAdapter);
        expListLv.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if(groupPosition==0 && childPosition==0)
                {
                    addNewExpDialog=new AddNewExpenseDialog(getActivity());
                    addNewExpDialog.addNewExpenseDialog(eventName,budgetLeft,totalBudget,budgetSpent);
                }
                else if(groupPosition==0 && childPosition==1)
                {
                    historyCursor=databasemanager.getBudgetHistoryInfo(eventName);
                    setExpenseList(historyCursor);
                    expenseHistoryDialog=new ShowAllExpenseHistoryDialog(getActivity());
                    expenseHistoryDialog.showAllExpenseHistoryDialog(expenseList);
                }
                else if(groupPosition==0 && childPosition==2)
                {
                    addBudgetDialog=new AddMoreBudgetDialog(getActivity());
                    addBudgetDialog.addMoreBudgetDialog(eventName,totalBudget,budgetLeft,budgetSpent);
                }
                else if(groupPosition==1 && childPosition==0)
                {
                    Bundle bundle=new Bundle();
                    bundle.putString("eventName","/"+eventName);
                    bundle.putString("userName", "/"+Variables.CURRENT_USER_NAME);
                    Fragment fragment = new MemoryFragment();
                    fragment.setArguments(bundle);

                    getFragmentManager().beginTransaction()
                            .replace(R.id.pre_main_fragment_container,fragment)
                            .addToBackStack(null).commit();
                }
                else if(groupPosition==1 && childPosition==1)
                {

                }
                else if(groupPosition==2 && childPosition==0)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.EVENT_NAME,eventName);
                    Fragment fragment = new EditEventFragment();
                    fragment.setArguments(bundle);
                    getFragmentManager().beginTransaction()
                            .replace(R.id.pre_main_fragment_container,fragment)
                            .addToBackStack(null).commit();
                }
                else if(groupPosition==2 && childPosition==1)
                {
                    databasemanager.deleteExpenseHistory(eventName);
                }
                else if(groupPosition==2 && childPosition==2)
                {
                    boolean deleteHistoryFlag=databasemanager.deleteExpenseHistory(eventName);
                    boolean deleteEventflag= databasemanager.deleteEvent(eventName);
                    // Toast.makeText(getActivity(), "Deleted History: "+deleteHistoryFlag, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(getActivity(), "Deleted Event: "+deleteEventflag, Toast.LENGTH_SHORT).show();
                    if((deleteEventflag) || (deleteHistoryFlag))
                    {
                        getFragmentManager().beginTransaction()
                                .replace(R.id.pre_main_fragment_container,new ShowAllEventsFragment()).commit();
                    }
                }
                //Toast.makeText(getActivity(), groupPosition +" "+ childPosition, Toast.LENGTH_SHORT).show();
                return true;
            }
        });



        /*deleteExpenseHistoryBtn=(Button)view.findViewById(R.id.eventdetail_delete_expense_history_btn);
        deleteExpenseHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databasemanager.deleteExpenseHistory(eventName);
            }
        });*/

       /* deleteEventBtn=(Button)view.findViewById(R.id.eventdetail_delete_event_btn);
        deleteEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean deleteHistoryFlag=databasemanager.deleteExpenseHistory(eventName);
               boolean deleteEventflag= databasemanager.deleteEvent(eventName);
               // Toast.makeText(getActivity(), "Deleted History: "+deleteHistoryFlag, Toast.LENGTH_SHORT).show();
               // Toast.makeText(getActivity(), "Deleted Event: "+deleteEventflag, Toast.LENGTH_SHORT).show();
                if((deleteEventflag) || (deleteHistoryFlag))
                {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.pre_main_fragment_container,new ShowAllEventsFragment()).commit();
                }

            }
        });*/

        /*editEventBtn=(Button)view.findViewById(R.id.eventdetail_edit_event_btn);
        editEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.EVENT_NAME,eventName);
                Fragment fragment = new EditEventFragment();
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.pre_main_fragment_container,fragment)
                        .addToBackStack(null).commit();


            }
        });*/

       /* momentBtn=(Button)view.findViewById(R.id.eventdetail_moments_btn);
        momentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putString("eventName","/"+eventName);
                bundle.putString("userName", "/"+Variables.CURRENT_USER_NAME);
                Fragment fragment = new MemoryFragment();
                fragment.setArguments(bundle);

                getFragmentManager().beginTransaction()
                        .replace(R.id.pre_main_fragment_container,fragment)
                        .addToBackStack(null).commit();
            }
        });*/

        return view;

    }
    private void getEventDetails(String eventName)
    {
        Cursor cursor=databasemanager.getEventDeatailInfos(eventName);
        cursor.moveToFirst();
        //Toast.makeText(getActivity(), cursor.getCount()+"", Toast.LENGTH_SHORT).show();
        try
        {
            totalBudget=cursor.getInt(cursor.getColumnIndex(Constants.BUDGET));
            budgetLeft=cursor.getInt(cursor.getColumnIndex(Constants.BUDGET_LEFT));
            budgetSpent=cursor.getInt(cursor.getColumnIndex(Constants.BUDGET_SPENT));
            cursor.close();
        }catch (Exception e)
        {
            getFragmentManager().beginTransaction()
                    .replace(R.id.pre_main_fragment_container,new ShowAllEventsFragment()).commit();
        }


    }
    private static int setProgressbar(int budgetSpent,int totalBudget)
    {
        int prgoress=(budgetSpent*100)/totalBudget;
        progressBar.setProgress(prgoress);
        percentTv.setText(String.valueOf(prgoress)+"%");
        return prgoress;
    }

    public static void setBudgetUpdates(int newTotalBudget,int newBudgetLeft,int newBudgetSpent)
    {
        totalBudget=newTotalBudget;
        budgetLeft=newBudgetLeft;
        budgetSpent=newBudgetSpent;
        setBudgetStatus(budgetLeft,totalBudget);
        setProgressbar(budgetSpent,totalBudget);
    }


    private static void setBudgetStatus(int budgetLeft,int totalBudget)
    {
        budgetStatusTv.setText("("+budgetLeft+"/"+totalBudget+")");
    }

    private void setExpenseList(Cursor expenseHistoryCursor)
    {
        expenseList.clear();
        historyCursor.moveToFirst();
        for(int i=0;i<historyCursor.getCount();i++)
        {

            String expenseName=historyCursor.getString(historyCursor.getColumnIndex(Constants.EXPENSE_NAME));
           // Toast.makeText(getActivity(), expenseName+"", Toast.LENGTH_SHORT).show();
            int expenseAmount=historyCursor.getInt(historyCursor.getColumnIndex(Constants.EXPENSE_AMOUNT));
            expense=new Expense(expenseName,expenseAmount);
            expenseList.add(expense);
            historyCursor.moveToNext();
        }
    }

    private void makeLists()
    {
        headerList=new ArrayList<String>();
        childList=new HashMap<String, List<String>>();

        headerList.add("Expense");
        headerList.add("Moment");
        headerList.add("More On Event");

        List<String> childsHeaderOne=new ArrayList<>();
        childsHeaderOne.add("Add Expense");
        childsHeaderOne.add("Expense History");
        childsHeaderOne.add("Add More Budget");

        List<String> childsHeaderTwo=new ArrayList<>();
        childsHeaderTwo.add("Moment Gallery");
        childsHeaderTwo.add("Capture Moments");

        List<String> childsHeaderThree=new ArrayList<>();
        childsHeaderThree.add("Edit This Event");
        childsHeaderThree.add("Clean Expense History");
        childsHeaderThree.add("Delete This Event");


        childList.put(headerList.get(0),childsHeaderOne);
        childList.put(headerList.get(1),childsHeaderTwo);
        childList.put(headerList.get(2),childsHeaderThree);

    }
}
