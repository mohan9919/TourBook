package finalproject.rahman.tourbook.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import finalproject.rahman.tourbook.CameraActivity;
//import finalproject.rahman.tourbook.MemoryActivity;
import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/15/2017.
 */

public class MemoryFragment extends Fragment
{
        private FloatingActionButton openCameraBtn;
        private String appName="/TourBook";
        private String userName;
                //="/User2";
        private String eventName;
                //="/Event1";
        private Bundle bundle;
        private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_memory,container,false);
        userName=getArguments().getString("userName");
        eventName=getArguments().getString("eventName");
        bundle=new Bundle();
        bundle.putString("appName",appName);
        bundle.putString("userName",userName);
        bundle.putString("eventName",eventName);

        Fragment fragment=new GalleryFragment();
        fragment.setArguments(bundle);
        //Toast.makeText(getActivity(), bundle.getString("appName")+"", Toast.LENGTH_SHORT).show();
        getFragmentManager().beginTransaction()
                .replace(R.id.memorygallery_fragment_container,fragment).commit();

        openCameraBtn =(FloatingActionButton)view.findViewById(R.id.memorygallery_open_camera_fab);
        openCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getActivity(),CameraActivity.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });


        return view;
    }












    }








