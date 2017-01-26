package finalproject.rahman.tourbook;

import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    GoogleMap mGoogleMap;
    GoogleApiClient mGoogleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (googleServiceAvilable()) {
            //Toast.makeText(this, "Connected", Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_map);
            initMap();
        } else {

        }
    }

    private void initMap() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    public boolean googleServiceAvilable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int isAvailable = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (isAvailable == ConnectionResult.SUCCESS) {
            return true;
        } else if (googleApiAvailability.isUserResolvableError(isAvailable)) {
            Dialog dialog = googleApiAvailability.getErrorDialog(this, isAvailable, 0);
            dialog.show();
        } else {
           Toast.makeText(this, "Con't connect to play service", Toast.LENGTH_LONG).show();
        }
        return false;
    }
    Marker marker;

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        LatLng ll=new LatLng(23.7181,90.4007);
        mGoogleMap.addMarker(new MarkerOptions().position(ll).snippet("I am here").icon(BitmapDescriptorFactory.
                fromResource(R.mipmap.found_place)));
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ll,15));

        mGoogleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                Geocoder gc=new Geocoder(MapActivity.this);
                LatLng ll=marker.getPosition();
                List<Address> list=null;
                try {
                     list=gc.getFromLocation(ll.latitude,ll.longitude,1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Address add=list.get(0);
                marker.setTitle(add.getLocality());
                marker.showInfoWindow();

            }
        });


        if(mGoogleMap!=null){
            mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                @Override
                public View getInfoWindow(Marker marker) {
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker) {
                    View v=getLayoutInflater().inflate(R.layout.map_place_info,null);

                    TextView localityTv=(TextView)v.findViewById(R.id.locality_tv);
                    TextView latTv=(TextView)v.findViewById(R.id.lat_tv);
                    TextView lonTv=(TextView)v.findViewById(R.id.lon_tv);
                    TextView snippetTv=(TextView)v.findViewById(R.id.snippet_tv);

                    LatLng ll=marker.getPosition();

                    localityTv.setText(marker.getTitle());
                    latTv.setText("Lantitude"+ll.latitude);
                    lonTv.setText("Longitude"+ll.longitude);
                    snippetTv.setText(marker.getSnippet());

                    return v;
                }
            });
        }


        //double goToLocation;
        goToLocationZoom(23.7181,90.4007,15);



//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
//            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                return;
//            }
            //   mGoogleMap.setMyLocationEnabled(true);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
        mGoogleApiClient.connect();

    }

//    private void goToLocation(double lat, double lon) {
//        LatLng ll = new LatLng(lat, lon);
//        CameraUpdate update = CameraUpdateFactory.newLatLng(ll);
//        mGoogleMap.moveCamera(update);
//
//    }

    private void goToLocationZoom(double lat, double lon, float zoom) {
        LatLng ll = new LatLng(lat, lon);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mGoogleMap.moveCamera(update);
    }
//    private void goToLocationOut(double lat, double lon, float out) {
//        LatLng ll = new LatLng(lat, lon);
//        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, out);
//        mGoogleMap.moveCamera(update);
//    }

    public void clickGoBtn(View view) throws IOException { //geoLocate
        try
        {
            EditText locationEt = (EditText) findViewById(R.id.location_et);
            String location = locationEt.getText().toString();
            locationEt.setText("");

            Geocoder gc = new Geocoder(this);
            List<Address> list = gc.getFromLocationName(location, 1);
            Address address = list.get(0);
            String locality = address.getLocality();

           // Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

            double lat = address.getLatitude();
            double lon = address.getLongitude();

            goToLocationZoom(lat, lon, 15);

            setMarker(locality, lat, lon);

        }catch (Exception e)
        {
            Toast.makeText(this, "দয়া করে পূরণ করুন", Toast.LENGTH_LONG).show();
        }

    }

    Marker markerOne;
    Marker markerTwo;
    Marker markerThree;
    Marker markerFour;

    //Circle circle;
    private void setMarker(String locality, double lat, double lon) {
//        if(markerOne!=null){
//            removeEveryThing();
//        }

        MarkerOptions markerOptions=new MarkerOptions()
                .title(locality)
                .draggable(true)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.found_place))
                .position(new LatLng(lat,lon))
                .snippet("I am here");

        if(markerOne==null){
            markerOne=mGoogleMap.addMarker(markerOptions);
        }else if (markerTwo==null){
            markerTwo=mGoogleMap.addMarker(markerOptions);
            drawLine();
        }else {
            removeEveryThing();
            markerOne=mGoogleMap.addMarker(markerOptions);

        }
        //circle=drawCircle(new LatLng(lat,lon));
    }
    Polygon line;
    private void drawLine() {
//        LatLng ll=new LatLng(23.7529527,90.3924129);
//        LatLng llend=new LatLng(23.807107, 90.368750);
        PolygonOptions options=new PolygonOptions()

                .add(markerOne.getPosition())
                .add(markerTwo.getPosition())
                .strokeColor(Color.BLUE)
                .strokeWidth(6);
                line=mGoogleMap.addPolygon(options);

    }

//    private Circle drawCircle(LatLng latLng) {
//        CircleOptions option=new CircleOptions()
//                            .center(latLng)
//                            .radius(1000)
//                            .fillColor(0x33FF0000)
//                            .strokeColor(Color.MAGENTA)
//                            .strokeWidth(3);
//        return mGoogleMap.addCircle(option);
//
//    }

    private void removeEveryThing(){
        markerOne.remove();
        markerOne=null;
        markerTwo.remove();
        markerTwo=null;
        line.remove();
//        circle.remove();
//        circle=null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.map_location_layout, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case (R.id.mapTypeNone):
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            case (R.id.mapTypeNormal):
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case (R.id.mapTypeTerrain):
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case (R.id.mapTypeSatelite):
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case (R.id.mapTypeHybrid):
                mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    LocationRequest mLocationRequest;

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(1000);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location==null){
            Toast.makeText(this,"Can't get current location",Toast.LENGTH_LONG).show();
        }else {
            LatLng ll= new LatLng(location.getLatitude(),location.getLongitude());
            CameraUpdate update= CameraUpdateFactory.newLatLngZoom(ll,15);
            //mGoogleMap.animateCamera(update);
        }

    }

}
