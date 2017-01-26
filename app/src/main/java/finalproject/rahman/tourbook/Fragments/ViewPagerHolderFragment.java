package finalproject.rahman.tourbook.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import finalproject.rahman.tourbook.Adapters.ViewPagerAdapter;
import finalproject.rahman.tourbook.R;

/**
 * Created by rahma on 1/14/2017.
 */

public class ViewPagerHolderFragment extends Fragment {
    private View view;
    private ViewPagerAdapter adapter;
    private ViewPager viewPagerVp;
    private ArrayList<String> list;

    public ViewPagerHolderFragment() {
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_view_pager_holder,container,false);

        viewPagerVp=(ViewPager)view.findViewById(R.id.fragmentviewpagerholder_viewpager_vp);
        this.list=getArguments().getStringArrayList("list");
        adapter=new ViewPagerAdapter(getFragmentManager(),list);
        viewPagerVp.setAdapter(adapter);

        return view;
    }


}
