package finalproject.rahman.tourbook.WeatherAdapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import finalproject.rahman.tourbook.R;
import finalproject.rahman.tourbook.WeatherActivity.WeatherActivity;
import finalproject.rahman.tourbook.WeatherActivity.WebResponse;

//import location.rahman.weathercurrent.MainActivity;
//import location.rahman.weathercurrent.R;
//import location.rahman.weathercurrent.WebResponse;


public class WeatherAdapter extends ArrayAdapter {
    private Context context;

    List<WebResponse.List> weatherList;
    TextView dayTv;
    ImageView iconIv;
    TextView dateTv;
    TextView maxTempTv;
    TextView minTempTv;
    TextView dayTempTv;
    TextView nightTempTv;
    Picasso picasso;
    Calendar calendar=Calendar.getInstance();
    int year=calendar.get(calendar.YEAR);
    int month=calendar.get(calendar.MONTH);
    int date=calendar.get(calendar.DATE);
    /*private String dayName(int date)
    {
        Date date1=calendar.getTime();

    }*/




    public WeatherAdapter(Context context, List<WebResponse.List> weatherList) {
        super(context, R.layout.weather_adapter_layout,weatherList);
        this.context=context;
        this.weatherList=weatherList;
        //this.contactList=contactList;
    }

    @NonNull
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=inflater.inflate(R.layout.weather_adapter_layout,parent,false);

//        nameTV=(TextView)view.findViewById(R.id.list_database_name_tv);
//        rollTV=(TextView)view.findViewById(R.id.list_database_roll_tv);
//        emailTV=(TextView)view.findViewById(R.id.list_database_email_tv);
//        phoneNoTV=(TextView)view.findViewById(R.id.list_database_phone_no_tv);
//        batchNoTV=(TextView)view.findViewById(R.id.list_database_batch_no_tv);
//        userNameTV=(TextView)view.findViewById(R.id.list_database_user_name_tv);
//        passwordTV=(TextView)view.findViewById(R.id.list_database_password_tv);
//        macAddressTV=(TextView)view.findViewById(R.id.list_database_mac_address_tv);


        dayTv=(TextView)view.findViewById(R.id.weather_day_tv);
        iconIv=(ImageView) view.findViewById(R.id.weather_icon_iv);
        dateTv=(TextView)view.findViewById(R.id.weather_date_tv);
        maxTempTv=(TextView)view.findViewById(R.id.weather_max_tv);
        minTempTv=(TextView)view.findViewById(R.id.weather_min_tv);
        dayTempTv=(TextView)view.findViewById(R.id.weather_dayTemp_tv);
        nightTempTv=(TextView)view.findViewById(R.id.weather_nightTemp_tv);

        /*dateTv.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(date).append(" ").append("-").append(month).append("-")
                .append(year));
*/
        //if(position.)

        dateTv.setText("Date: "+String.valueOf((int)date+position)+"-"+month+"-"+year);
        int days=calendar.get(calendar.DAY_OF_WEEK)+position;
        if(days>7)
            days=days%7;
        String dayName=null;
        switch (days)
        {
            case 1:dayName="Sunday";
                break;
            case 2:dayName="Monday";
                break;
            case 3:dayName="Tuesday";
                break;
            case 4:dayName="Wednesday";
                break;
            case 5:dayName="Thursday";
                break;
            case 6:dayName="Friday";
                break;
            case 7:dayName="Saturday";
                break;
            default:break;
        }
        switch (position)
        {
            case 0 : dayName="Today";
                break;
            case 1 : dayName="Tomorrow";
                    break;
            default:break;
        }



            dayTv.setText(dayName);


        maxTempTv.setText("Max: "+weatherList.get(position).getTemp().getMax().toString());
        minTempTv.setText("Min: "+weatherList.get(position).getTemp().getMin().toString());
        dayTempTv.setText("Day: "+weatherList.get(position).getTemp().getDay().toString());
        nightTempTv.setText("Night: "+weatherList.get(position).getTemp().getNight().toString());
        picasso.with(context).load(WeatherActivity.ICON_BASE
                +weatherList.get(position).getWeather().get(0).getIcon()+".png").into(iconIv);



        weatherList.get(position).getTemp().getNight().toString();

        /*contact=contactList.get(position);

        w
        nameTV.setText(contact.getName());
        rollTV.setText(contact.getRoll());
        emailTV.setText(contact.getEmail());
        phoneNoTV.setText(contact.getPhoneNo());
        batchNoTV.setText(contact.getBatchNo());
        macAddressTV.setText(contact.getMacAddress());*/


        return view;
    }
}
