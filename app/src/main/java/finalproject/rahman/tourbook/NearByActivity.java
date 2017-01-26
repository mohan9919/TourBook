package finalproject.rahman.tourbook;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.List;

import NearBy.NearbyListAdapter;
import NearBy.NearbyPlaceServiceApi;
import NearBy.NearbyPlaces;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NearByActivity extends AppCompatActivity {

    private double latitude;
    private double longitude;
    private String city_name;

    private ListView nearby_list;
    private TextView city_tv;
    private Spinner type_sp;
    private Spinner radious_sp;
    private Button findBTN;
    String category;
    int radius;
    NearbyPlaceServiceApi nearbyPlaceServiceApi;

    public static final String BASE_URL_NEARBY_PLACE = "https://maps.googleapis.com/maps/api/place/nearbysearch/";
    public static final String places_api_key = "AIzaSyD7zSS7qQHMjX-Y3hSj6i98RcG-Y40df7s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by);

        city_name="Dhaka";
        latitude = 23.7508;
        longitude = 90.3933;

        category = "atm";
        radius = 500;
        nearby_list = (ListView) findViewById(R.id.nearby_list);
        city_tv = (TextView) findViewById(R.id.nearby_city_name);
        type_sp = (Spinner) findViewById(R.id.spinner_type);
        radious_sp = (Spinner) findViewById(R.id.spinner_radious);
        findBTN = (Button) findViewById(R.id.find_nearby);

        city_tv.setText(city_name);
        ArrayAdapter<CharSequence> type_adapter = ArrayAdapter.createFromResource(this,
                R.array.nearby_type, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> radious_adapter = ArrayAdapter.createFromResource(this,
                R.array.nearby_radious, android.R.layout.simple_spinner_item);
        type_sp.setAdapter(type_adapter);
        radious_sp.setAdapter(radious_adapter);
        networkLibraryInitialize();


        type_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = "atm";
            }
        });

        radious_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                double x = Double.parseDouble(parent.getItemAtPosition(position).toString());
                radius =(int) (x*1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                radius = 500;
            }
        });

        findBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNearbyPlace();
            }
        });
    }



    private void networkLibraryInitialize() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL_NEARBY_PLACE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        nearbyPlaceServiceApi = retrofit.create(NearbyPlaceServiceApi.class);
    }

    public void getNearbyPlace() {

        String apiKey = places_api_key;
        String urlString = String.format("json?location=%f,%f&radius=%d&type=%s&key=%s",latitude,longitude,radius,category,apiKey);
        Call<NearbyPlaces> nearbyPlacesResponseCall = nearbyPlaceServiceApi.getAllResponse(urlString);
        nearbyPlacesResponseCall.enqueue(new Callback<NearbyPlaces>() {
            @Override
            public void onResponse(Call<NearbyPlaces> call, Response<NearbyPlaces> response) {
                if (response != null && response.errorBody() == null) {
                    NearbyPlaces nearbyPlaces = response.body();
                    List<NearbyPlaces.Result> results = nearbyPlaces.getResults();
                    setListView(results);
                }
                else {
                    Toast.makeText(NearByActivity.this,"Failed, pleased try again", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<NearbyPlaces> call, Throwable t) {
                Toast.makeText(NearByActivity.this,"Please check your connection", Toast.LENGTH_LONG).show();
            }
        });
    }


    public void setListView(final List<NearbyPlaces.Result> results){
        NearbyListAdapter adapter = new NearbyListAdapter(this, results);
        nearby_list.setAdapter(adapter);
        nearby_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {

                AlertDialog.Builder alert = new AlertDialog.Builder(NearByActivity.this);
                alert.setTitle("Show Direction on Map");
                alert.setMessage("Do you want to see direction in map");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //show direction on map

                        LatLng startLatLng = new LatLng(latitude, longitude);
                        LatLng destLatLng = new LatLng(results.get(i).getGeometry().getLocation().getLat(), results.get(i).getGeometry().getLocation().getLng());

//                        Intent direction = new Intent(getActivity(), MapFragmentDemo.class);
//                        direction.putExtra("s", startLatLng);
//                        direction.putExtra("d", destLatLng);
//                        startActivity(direction);
                        dialog.dismiss();
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                alert.show();
            }
        });
    }
}
