package finalproject.rahman.tourbook.WeatherActivity;

import android.app.Dialog;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

//import Adapter.CustomPagerAdapter;
//import Adapter.WeatherAdapter;
//import Data.JSONWetherParser;
//import Data.WeatherHttpParse;
//import Model.Weather;
import finalproject.rahman.tourbook.R;
import finalproject.rahman.tourbook.WeatherAdapter.CustomPagerAdapter;
import finalproject.rahman.tourbook.WeatherAdapter.WeatherAdapter;
import finalproject.rahman.tourbook.WeatherData.JSONWetherParser;
import finalproject.rahman.tourbook.WeatherData.WeatherHttpParse;
import finalproject.rahman.tourbook.WeatherModel.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {
    String cityNameStr;
    TextView textView;
    ViewPager viewPager;
    TabLayout tabLayout;
    Context context;
    int celActive=0;
    int fahrActive=0;
    int changetoCel=0;
    int changeToFahr=0;

    public static List<WebResponse.List> listeds=null;

    TextView citySearchTv;

    WeatherAdapter adapter;
    ListView listViewLv;
    private TextView cityName;
    private TextView temp;
    private ImageView icon;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;
    Weather weather = new Weather();
    Retrofit retrofit;
    WebServiceApi webServiceApi;
    private Call<WebResponse> webResponseCall;
    private final static String BASE="http://api.openweathermap.org/";
    public final static String ICON_BASE="http://openweathermap.org/img/w/";

    public static String getEndUrlForecast(String place)
    {
        String endHalf="data/2.5/forecast/daily?q=" ;
        String endFull="&mode=json&units=metric&cnt=7&APPID=3f947e621a30b74771b6f7cf5bbe44a7";
        return endHalf+place+endFull;

    }
    //DecimalFormat deci=new DecimalFormat()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        celActive=1;
        changeToFahr=1;
        cityName=(TextView)findViewById(R.id.cityTv);
        temp=(TextView)findViewById(R.id.tempText);
        icon=(ImageView)findViewById(R.id.thumbnailIcon);
        description=(TextView)findViewById(R.id.cloudText);
        humidity=(TextView)findViewById(R.id.humidText);
        pressure=(TextView)findViewById(R.id.pressureText);
        wind=(TextView)findViewById(R.id.windText);
        sunrise=(TextView)findViewById(R.id.riseText);
        sunset=(TextView)findViewById(R.id.setText);
        updated=(TextView)findViewById(R.id.updateText);
        citySearchTv=(TextView)findViewById(R.id.citySearchTv);
        listViewLv=(ListView)findViewById(R.id.list_view_lv);
       // renderWeatherData("Spokane,US");
        renderWeatherData("Dhaka,BD","c");

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        webServiceApi=retrofit.create(WebServiceApi.class);
        if(celActive==1)
        {
            webResponseCall=webServiceApi.getAllSevenDays("data/2.5/forecast/daily?q=Dhaka&mode=json&units=metric&cnt=7&APPID=3f947e621a30b74771b6f7cf5bbe44a7");

        }
       // makeFragment();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.weather,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.change_city_id:
                //do something
                final Dialog dialog=new Dialog(WeatherActivity.this);
                dialog.setContentView(R.layout.search_city_layout);
                dialog.setTitle("Search City");
                final EditText searchEt=(EditText)dialog.findViewById(R.id.search_city_et);
                //final EditText passwordET=(EditText)dialog.findViewById(R.id.log_in_password_et);

                Button searchBtn=(Button)dialog.findViewById(R.id.search_btn);


                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                         cityNameStr=searchEt.getText().toString();

                        //citySearchTv.setText(cityName);

                        //String password=passwordET.getText().toString();
                        renderWeatherData(cityNameStr,"c");
                        //FragmentManager().
                        //makeFragment();
                        webServiceApi=retrofit.create(WebServiceApi.class);
                        String url=getEndUrlForecast(cityNameStr);
                        webResponseCall=webServiceApi.getAllSevenDays(url);
                        dialog.dismiss();

                    }
                });
                dialog.show();
                break;
            case R.id.celcius:

                webResponseCall=webServiceApi.getAllSevenDays("data/2.5/forecast/daily?q="+cityNameStr+"&mode=json&units=metric&cnt=7&APPID=3f947e621a30b74771b6f7cf5bbe44a7");
                webResponseCall.enqueue(new Callback<WebResponse>() {
                    @Override
                    public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
                        WebResponse webResponse=response.body();

                        listeds = webResponse.getList();
                        //Toast.makeText(MainActivity.this, ""+listeds.get(2).getWeather().get(0).getIcon(), Toast.LENGTH_SHORT).show();

                        //  Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMax()), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMin()), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMorn()), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getNight()), Toast.LENGTH_SHORT).show();
                        adapter=new WeatherAdapter(WeatherActivity.this,listeds);
                        listViewLv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        //renderWeatherData(cityNameStr,"c");
                        //makeFragment();
                        //Toast.makeText(MainActivity.this,""+ listeds.get(1).getTemp(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,""+ listeds.get(2).getTemp(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,""+ listeds.get(3).getTemp(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, webResponse, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<WebResponse> call, Throwable t) {

                    }
                });

                    //String c="c";
               //renderWeatherData(cityNameStr,c);

                //do something
                break;
            case R.id.fahrenheit:
                    //renderWeatherData(cityNameStr,"f");

                    webResponseCall=webServiceApi.getAllSevenDays("data/2.5/forecast/daily?q="+cityNameStr+"&mode=json&units=imperial&cnt=7&APPID=3f947e621a30b74771b6f7cf5bbe44a7");
                webResponseCall.enqueue(new Callback<WebResponse>() {
                    @Override
                    public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
                        WebResponse webResponse=response.body();

                        listeds = webResponse.getList();
                        //Toast.makeText(MainActivity.this, ""+listeds.get(2).getWeather().get(0).getIcon(), Toast.LENGTH_SHORT).show();
                        //  Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMax()), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMin()), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMorn()), Toast.LENGTH_SHORT).show();
                        // Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getNight()), Toast.LENGTH_SHORT).show();
                        adapter=new WeatherAdapter(WeatherActivity.this,listeds);
                        listViewLv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        makeFragment();
                        //Toast.makeText(MainActivity.this,""+ listeds.get(1).getTemp(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,""+ listeds.get(2).getTemp(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this,""+ listeds.get(3).getTemp(), Toast.LENGTH_SHORT).show();
                        //Toast.makeText(MainActivity.this, webResponse, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<WebResponse> call, Throwable t) {

                    }
                });

                //do something
                break;
        }
        //switch (item.getItemId())
        return super.onOptionsItemSelected(item);

    }

    private void renderWeatherData(String city,String unit)
    {

        WeatherTask weatherTask = new WeatherTask();
        if(unit=="f")
        {
            weatherTask.execute(new String []{city +"&units=imperial&APPID=3f947e621a30b74771b6f7cf5bbe44a7"});
        }else

        {
            weatherTask.execute(new String []{city +"&units=metric&APPID=3f947e621a30b74771b6f7cf5bbe44a7"});
        }

    }

    private class WeatherTask extends AsyncTask<String,Void, Weather>
    {

        @Override
        protected Weather doInBackground(String... params) {
            String data = ((new WeatherHttpParse()).getWeatherData(params[0]));
            weather = JSONWetherParser.getWeather(data);
           // Toast.makeText(MainActivity.this, weather.place.getCity(), Toast.LENGTH_SHORT).show();
            Log.v("Data:",String.valueOf(weather.place.getLat()));
            Log.v("Data:",String.valueOf(weather.place.getLon()));
            Log.v("Data:",String.valueOf(weather.place.getCity()));
            Log.v("Data:",String.valueOf(weather.place.getCountry()));
            Log.v("Data:",String.valueOf(weather.place.getSunrise()));
            Log.v("Data:",String.valueOf(weather.place.getSunset()));
            //Log.v("Data:",String.valueOf(weather.place.getLon()));
            Log.v("Data:",String.valueOf(weather.currentCondition.getTemperature()));
            publishProgress();
            return weather;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            //Toast.makeText(MainActivity.this, weather.place.getCity(), Toast.LENGTH_SHORT).show();
            super.onProgressUpdate(values);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        protected void onPostExecute(Weather weather) {
            /*String sunriseTime=null;
            DateFormat df=DateFormat.getTimeInstance();
            sunriseTime=df.format(new Date(weather.place.getSunrise()));*/

           /* long unixSec=weather.place.getSunrise();
            Date date = new Date(unixSec*1000);
            //Date date=new Date();*/


            /*SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyy-MM-dd HH:mm:ss z");
            simpleDateFormat.setTimeZoneFormat(TimeZone.getTimeZone(""));
            String time=simpleDateFormat.format(date);*/

            //Toast.makeText(MainActivity.this,""+ time, Toast.LENGTH_SHORT).show();

            long unixSunrise=weather.place.getSunrise();
            Date date = new Date(unixSunrise*1000L);
            //SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss z");
           // sdf.setTimeZone(android.icu.util.TimeZone.getTimeZone("GTM-6"));
            //String sunriseTime= sdf.format(date);

            Date rise = new Date(unixSunrise*1000L);
//            SimpleDateFormat f = new SimpleDateFormat("HH:mm ( z )");
           // f.setTimeZone(TimeZone.getTimeZone("GMT");
            //String s = f.format(rise);

            Date set = new Date(weather.place.getSunset()*1000L);
            //SimpleDateFormat ft = new SimpleDateFormat("HH:mm ( z )");
            // f.setTimeZone(TimeZone.getTimeZone("GMT");
            //String sunsetTime = ft.format(set);

            Date update = new Date(weather.place.getLastUpdates()*1000L);
           // SimpleDateFormat ftt = new SimpleDateFormat("HH:mm ( z )");
            // f.setTimeZone(TimeZone.getTimeZone("GMT");
            //String upDateTime = ftt.format(update);

           // Toast.makeText(MainActivity.this, weather.place.getCity(), Toast.LENGTH_SHORT).show();
            cityName.setText(weather.place.getCity()+","+weather.place.getCountry());
            temp.setText(String.format("%.2f",weather.currentCondition.getTemperature())+" °C");
            humidity.setText("Humidity : "+weather.currentCondition.getHumidity()+" %");
            pressure.setText("Pressure : "+weather.currentCondition.getPresure()+" hPa");
            wind.setText("Wind : "+ weather.wind.getSpeed()+" mps");
            //sunrise.setText("Sunrise : "+ weather.place.getSunrise());
            //sunrise.setText("Sunrise : "+ s);
            //sunset.setText("Sunset : "+ sunsetTime);
            //updated.setText("Last Updated : "+ upDateTime);
            description.setText("Condition : "+ weather.currentCondition.getCondition() + ", "+weather.currentCondition.getDescription());
            //icon.setI
            //String iconString="http://openweathermap.org/img/w/\" + iconCode + \".png\"
            Picasso.with(getApplicationContext()).load(ICON_BASE+weather.currentCondition.getIcon().toString()+".png").into(icon);
            makeFragment();

            //Toast.makeText(MainActivity.this, weather.currentCondition.getIcon().toString(), Toast.LENGTH_SHORT).show();
            webResponseCall.enqueue(new Callback<WebResponse>() {
                @Override
                public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
                    WebResponse webResponse=response.body();

                    listeds = webResponse.getList();
                    //Toast.makeText(MainActivity.this, ""+listeds.get(2).getWeather().get(0).getIcon(), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMax()), Toast.LENGTH_SHORT).show();
                  //Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMin()), Toast.LENGTH_SHORT).show();
                  //Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMorn()), Toast.LENGTH_SHORT).show();
                 // Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getNight()), Toast.LENGTH_SHORT).show();
                  adapter=new WeatherAdapter(WeatherActivity.this,listeds);
                    listViewLv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    makeFragment();
                    //Toast.makeText(MainActivity.this,""+ listeds.get(1).getTemp(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this,""+ listeds.get(2).getTemp(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this,""+ listeds.get(3).getTemp(), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(MainActivity.this, webResponse, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<WebResponse> call, Throwable t) {

                }
            });

        }
    }

    public void makeFragment(){
        viewPager = (ViewPager) findViewById(R.id.vpPager);
        viewPager.setAdapter(new CustomPagerAdapter(this, getSupportFragmentManager()));

        tabLayout= (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
        });
    }
}
