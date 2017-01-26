package finalproject.rahman.tourbook.Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;

import finalproject.rahman.tourbook.Adapters.GridViewAdapter;
import finalproject.rahman.tourbook.CameraActivity;
import finalproject.rahman.tourbook.PreMainActivity;
import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/14/2017.
 */

public class GalleryFragment extends Fragment {
    private View view;
    private GridView gridView;

    private GridViewAdapter gridViewAdapter;

    private ArrayList<String> list;
    private File[] fileList;

    String appName;
    String userName;
    String eventName;
    String combined;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gallery,container,false);
        appName=getArguments().getString("appName");
        userName=getArguments().getString("userName");
        eventName=getArguments().getString("eventName");
        combined=appName+userName+eventName;
        PreMainActivity.collapsingToolbarLayout.setTitle("Gallery");

        gridView=(GridView)view.findViewById(R.id.grid_view);
        list=new ArrayList<String>();
        getFromSdcard();
        gridViewAdapter=new GridViewAdapter(getActivity(),list);
        gridView.setAdapter(gridViewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new ViewPagerHolderFragment();
                Bundle bundle=new Bundle();
                bundle.putStringArrayList("list",list);
                fragment.setArguments(bundle);
                getFragmentManager().beginTransaction()
                        .replace(R.id.memorygallery_fragment_container,fragment).addToBackStack(null).commit();
            }
        });
        return view;
    }

    public void getFromSdcard()
    {
        File file = new File(Environment.getExternalStorageDirectory(),combined);
        if(file.isDirectory())
        {
            fileList=file.listFiles();
            for(int i =0 ; i<fileList.length;i++)
            {
                list.add(fileList[i].getAbsolutePath());
            }
        }else
        {
            CameraActivity.createDirectory(appName,userName,eventName);
        }

    }




}
