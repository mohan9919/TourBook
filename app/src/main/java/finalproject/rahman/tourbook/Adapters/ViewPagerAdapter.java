package finalproject.rahman.tourbook.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import finalproject.rahman.tourbook.Fragments.PagerFragment;

/**
 * Created by rahma on 1/14/2017.
 */

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> list;
    int clickedPosition;
    boolean clicked;

    public ViewPagerAdapter(FragmentManager fm, ArrayList<String>list) {

        super(fm);
        this.list=list;

    }
    public ViewPagerAdapter(FragmentManager fm, ArrayList<String>list, int clickePosition) {

        super(fm);
        this.list=list;
        this.clickedPosition=clickePosition;
        clicked=true;

    }

    @Override
    public Fragment getItem(int position) {
        if(clicked)
        {
            clicked=false;
            return PagerFragment.getImages(list,clickedPosition);
        }
        else
        {
            return PagerFragment.getImages(list,position);

        }
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
