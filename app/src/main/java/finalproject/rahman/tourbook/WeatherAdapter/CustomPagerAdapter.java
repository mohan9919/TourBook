package finalproject.rahman.tourbook.WeatherAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by USER on 15-Dec-16.
 */
public class CustomPagerAdapter extends FragmentPagerAdapter {

    private  String[] fragments={"Current","Forecast"};
    private Context context;

    public CustomPagerAdapter(Context context, FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new FragmentCurrent();
            case 1:
                return new FragmentForecast();
            default:
                return  null;
        }
    }

    @Override
    public int getCount() {
        return fragments.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragments[position];
    }
}
