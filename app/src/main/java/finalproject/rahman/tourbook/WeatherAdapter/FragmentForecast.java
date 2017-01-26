package finalproject.rahman.tourbook.WeatherAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//import Model.Weather;
//import location.rahman.weathercurrent.R;
//import location.rahman.weathercurrent.WebResponse;
//import location.rahman.weathercurrent.WebServiceApi;
import finalproject.rahman.tourbook.R;
import finalproject.rahman.tourbook.WeatherActivity.WebResponse;
import finalproject.rahman.tourbook.WeatherActivity.WebServiceApi;
import finalproject.rahman.tourbook.WeatherModel.Weather;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static finalproject.rahman.tourbook.WeatherActivity.WeatherActivity.getEndUrlForecast;
import static finalproject.rahman.tourbook.WeatherActivity.WeatherActivity.listeds;
//import static location.rahman.weathercurrent.MainActivity.getEndUrlForecast;
//import static location.rahman.weathercurrent.MainActivity.listeds;

/**
 * Created by USER on 15-Dec-16.
 */
public  class FragmentForecast extends Fragment {
    WeatherAdapter adapter;
    ListView listViewLv;
    Weather weather = new Weather();
    Retrofit retrofit;
    WebServiceApi webServiceApi;
    private Call<WebResponse> webResponseCall;
    private final static String BASE="http://api.openweathermap.org/";
    public final static String ICON_BASE="http://openweathermap.org/img/w/";
    String city;
    EditText editText;

    View view;
    ListView listFore;
    WeatherAdapter adapter2;
    //WeatherAdapter adapter=new WeatherAdapter(getActivity(),getActivity().);
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_forecast_layout, container, false);
        editText=(EditText)getActivity().findViewById(R.id.search_city_et);

try{
    city=editText.getText().toString();
}catch (Exception e)
{
    city="Dhaka";
}



        //List<WebResponse.List>listeds;
       // adapter2=new WeatherAdapter(getActivity(),listeds);
      //  Toast.makeText(getActivity(), "From Frag:"+listeds.get(0).getTemp().getMax().toString(), Toast.LENGTH_SHORT).show();
        listFore=(ListView)view.findViewById(R.id.forecast_list_view_lv);
//        listFore.setAdapter(adapter2);

          /*  list=(ListView) getActivity().findViewById(R.id.list_view_lv);

            */
        retrofit=new Retrofit.Builder()
                .baseUrl(BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //getEndUrlForecast("helsinki,FI");

        webServiceApi=retrofit.create(WebServiceApi.class);
       // webResponseCall=webServiceApi.getAllSevenDays("data/2.5/forecast/daily?q=Dhaka&mode=json&units=metric&cnt=7&APPID=3f947e621a30b74771b6f7cf5bbe44a7");
        webResponseCall=webServiceApi.getAllSevenDays(getEndUrlForecast(city));
        webResponseCall.enqueue(new Callback<WebResponse>() {
            @Override
            public void onResponse(Call<WebResponse> call, Response<WebResponse> response) {
                WebResponse webResponse=response.body();

                listeds = webResponse.getList();
                //Toast.makeText(MainActivity.this, ""+listeds.get(2).getWeather().get(0).getIcon(), Toast.LENGTH_SHORT).show();



                /*Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMax()), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMin()), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getMorn()), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, String.valueOf(listeds.get(0).getTemp().getNight()), Toast.LENGTH_SHORT).show();*/
                adapter=new WeatherAdapter(getActivity(),listeds);
                listFore.setAdapter(adapter);
                adapter.notifyDataSetChanged();







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

        return view;

    }

    @Override
    public void onResume() {
        Toast.makeText(getActivity(), "Resume", Toast.LENGTH_SHORT).show();

        super.onResume();
    }
}
