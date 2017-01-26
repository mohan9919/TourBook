package finalproject.rahman.tourbook.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
//import android.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import finalproject.rahman.tourbook.Database.Constants;
import finalproject.rahman.tourbook.Fragments.EventDetailFragment;
import finalproject.rahman.tourbook.Fragments.SignUpFragment;
import finalproject.rahman.tourbook.MainActivity;
import finalproject.rahman.tourbook.PreMainActivity;
import finalproject.rahman.tourbook.R;

import static java.security.AccessController.getContext;

/**
 * Created by rahma on 1/9/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>{
    /*String [] s1={"Event Name","Event Name","Event Name","Event Name","Event Name","Event Name"};
    String [] s2={"From Sylhet to Chittagong","From Sylhet to Chittagong","From Sylhet to Chittagong","From Sylhet to Chittagong","From Sylhet to Chittagong","From Sylhet to Chittagong"};
    String [] s3={"Created On:","Created On:","Created On:","Created On:","Created On:","Created On:"};
    String [] s4={"Journy Starts on:","Journy Starts on:","Journy Starts on:","Journy Starts on:","Journy Starts on:","Journy Starts on:"};
    String [] s5={"Budget :","Budget :","Budget :","Budget :","Budget :","Budget :"};
    String [] s6={"X days to go :","X days to go :","X days to go :","X days to go :","X days to go :","X days to go :"};*/
    private static ArrayList<String> eventList;
    private ArrayList<String> startLocationList;
    private ArrayList<String> destinationList;
    private ArrayList<Integer> startDayList;
    private ArrayList<Integer> startMonthList;
    private ArrayList<Integer> startYearList;
    private ArrayList<Integer> startHourList;
    private ArrayList<Integer> startMinList;
    private ArrayList<Integer> budgetList;
    private ArrayList<String> createdOnList;
    private ArrayList<String> daysLeftList;

    private int pos;


    private static Context context;





    public RecyclerAdapter(ArrayList<String> eventList, ArrayList<String> startLocationList, ArrayList<String> destinationList, ArrayList<Integer> startDayList, ArrayList<Integer> startMonthList, ArrayList<Integer> startYearList, ArrayList<Integer> startHourList, ArrayList<Integer> startMinList, ArrayList<Integer> budgetList, ArrayList<String> createdOnList, ArrayList<String> daysLeftList) {
        this.eventList = eventList;
        this.startLocationList = startLocationList;
        this.destinationList = destinationList;
        this.startDayList = startDayList;
        this.startMonthList = startMonthList;
        this.startYearList = startYearList;
        this.startHourList = startHourList;
        this.startMinList = startMinList;
        this.budgetList = budgetList;
        this.createdOnList=createdOnList;
        this.daysLeftList=daysLeftList;
        //this.context=context;



    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        context=parent.getContext();
        View view = inflater.inflate(R.layout.material_design_card_view,parent,false);
        RecyclerViewHolder recyclerViewHolder=new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.eventNameTv.setText(eventList.get(position));
        holder.fromStartToDestTv.setText("From "+startLocationList.get(position)+" to "+destinationList.get(position));
        holder.createdOnTv.setText("Created On:"+createdOnList.get(position));
        holder.startDayTv.setText("Departure Date: "+startDayList.get(position)+"/"+startMonthList.get(position)+"/"+startYearList.get(position));
        holder.startHourTv.setText("Departure Time: "+startHourList.get(position)+":"+startMinList.get(position));
        holder.budgetTv.setText("Budget:\n"+budgetList.get(position)+" BDT");
        holder.daysLeftTv.setText(daysLeftList.get(position));


    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView eventNameTv;
        TextView fromStartToDestTv;
        TextView createdOnTv;
        TextView startDayTv;
        TextView startHourTv;
        TextView budgetTv;
        TextView daysLeftTv;
        FloatingActionButton deleteHistoryFab;
       // Context context;

        public RecyclerViewHolder(final View itemView)
        {
            super(itemView);
            final int position=getAdapterPosition();
            eventNameTv = (TextView)itemView.findViewById(R.id.card_view_event_name);
            fromStartToDestTv = (TextView)itemView.findViewById(R.id.card_view_from_start_to_destination);
            createdOnTv = (TextView)itemView.findViewById(R.id.card_view_created_on);
            startDayTv = (TextView)itemView.findViewById(R.id.card_view_start_date);
            startHourTv = (TextView)itemView.findViewById(R.id.card_view_start_time);
            budgetTv = (TextView)itemView.findViewById(R.id.card_view_budget);
            daysLeftTv = (TextView)itemView.findViewById(R.id.card_view_days_left);
            //deleteHistoryFab = (FloatingActionButton)itemView.findViewById(R.id.card_view_delete_history_fab);

//            deleteHistoryFab.setOnClickListener(this);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Fragment fragment=new EventDetailFragment();
                    Bundle bundle=new Bundle();
                    //bundle.putInt(Constants.SHOW_EVENT_DETAIL_BUNDLE,getAdapterPosition());
                    bundle.putString(Constants.SHOW_EVENT_DETAIL_BUNDLE,eventList.get(getAdapterPosition()));
                    fragment.setArguments(bundle);
                    try
                    {

                        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.pre_main_fragment_container,fragment)
                                .addToBackStack(null).commit();
                    }catch (Exception e)
                    {
                        Snackbar.make(itemView,e+"",Snackbar.LENGTH_LONG).show();
                    }

                    //Snackbar.make(itemView,"snackbar"+getAdapterPosition(),Snackbar.LENGTH_SHORT).setAction(null,null).show();

                }
            });


        }


       /* @Override
        public void onClick(View v) {
            //Toast.makeText(context, "hey", Toast.LENGTH_SHORT).show();

            //Delete History and Event

            Toast.makeText(context, "clicked "+eventList.get(getAdapterPosition()), Toast.LENGTH_SHORT).show();



        }*/
    }
}
