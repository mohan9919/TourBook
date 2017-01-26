package finalproject.rahman.tourbook.WeatherAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import finalproject.rahman.tourbook.R;
import finalproject.rahman.tourbook.WeatherModel.Weather;

//import Model.Weather;
//import location.rahman.weathercurrent.R;


public class FragmentCurrent extends Fragment {
    View view;
    TextView currentCityTv;
    TextView mainCityTv;
    ImageView currentThumbnailIconIv;
    ImageView mainThumbnailIconIv;
    TextView currentTempTextTv;
    TextView mainTempTextTv;
    TextView currentWindTextTv;
    TextView mainWindTextTv;
    TextView currentCloudTextTv;
    TextView mainCloudTextTv;
    TextView currentPressureTextTv;
    TextView mainPressureTextTv;
    TextView currentHumidTextTv;
    TextView mainHumidTextTv;
    TextView currentRiseTextTv;
    TextView mainRiseTextTv;
    TextView currentSetTextTv;
    TextView mainSetTextTv;
    TextView currentUpdateTextTv;
    TextView mainUpdateTextTv;
    public final static String ICON_BASE="http://openweathermap.org/img/w/";
    Weather weather;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_current_layout,container,false);
        currentCityTv=(TextView)view.findViewById(R.id.current_cityTv);
        mainCityTv=(TextView)getActivity().findViewById(R.id.cityTv);
        currentThumbnailIconIv=(ImageView)view.findViewById(R.id.current_thumbnailIcon);
        //currentThumbnailIconIv.setScaleType(ImageView.ScaleType.CENTER_CROP);

        mainThumbnailIconIv=(ImageView)getActivity().findViewById(R.id.thumbnailIcon);
        currentTempTextTv=(TextView) view.findViewById(R.id.current_temp_text);
        mainTempTextTv=(TextView)getActivity().findViewById(R.id.tempText);
        currentWindTextTv=(TextView) view.findViewById(R.id.current_windText);
        mainWindTextTv=(TextView)getActivity().findViewById(R.id.windText);
        currentCloudTextTv=(TextView)view.findViewById(R.id.current_cloudText);
        mainCloudTextTv=(TextView)getActivity().findViewById(R.id.cloudText);
        currentPressureTextTv=(TextView)view.findViewById(R.id.current_pressureText);
        mainPressureTextTv=(TextView)getActivity().findViewById(R.id.pressureText);
        currentHumidTextTv=(TextView)view.findViewById(R.id.current_humidText);
        mainHumidTextTv=(TextView)getActivity().findViewById(R.id.humidText);
        currentRiseTextTv=(TextView)view.findViewById(R.id.current_riseText);
        mainRiseTextTv=(TextView)getActivity().findViewById(R.id.riseText);
        currentSetTextTv=(TextView)view.findViewById(R.id.current_setText);
        mainSetTextTv=(TextView)getActivity().findViewById(R.id.setText);
        currentUpdateTextTv=(TextView)view.findViewById(R.id.current_updateText);
        mainUpdateTextTv=(TextView)getActivity().findViewById(R.id.updateText);


        currentCityTv.setText(mainCityTv.getText().toString());
        //currentThumbnailIconIv.setImageBitmap(mainThumbnailIconIv.);// watch out
       // currentThumbnailIconIv.setImageDrawable(mainThumbnailIconIv.getDrawable());
       //currentThumbnailIconIv.setImageResource(R.drawable.01d);
        //currentThumbnailIconIv.setImageDrawable(R.drawable.01d);
       // Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.);
        //currentThumbnailIconIv.setImageResource();
        currentTempTextTv.setText(mainTempTextTv.getText().toString());
        currentWindTextTv.setText(mainWindTextTv.getText().toString());
        currentCloudTextTv.setText(mainCloudTextTv.getText().toString());
        currentPressureTextTv.setText(mainPressureTextTv.getText().toString());
        currentHumidTextTv.setText(mainHumidTextTv.getText().toString());
        currentRiseTextTv.setText(mainRiseTextTv.getText().toString());
        currentSetTextTv.setText(mainSetTextTv.getText().toString());
        currentUpdateTextTv.setText(mainUpdateTextTv.getText().toString());

        //Picasso.with(getActivity()).load(ICON_BASE+weather.currentCondition.getIcon().toString()+".png").into(icon);
        //makeFragment();

        //Toast.makeText(getActivity(), ""+mainTempTextTv.getText().toString(), Toast.LENGTH_SHORT).show();









        return view;


    }
}
