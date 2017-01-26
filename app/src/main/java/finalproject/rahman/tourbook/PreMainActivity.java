package finalproject.rahman.tourbook;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import finalproject.rahman.tourbook.Database.Variables;
import finalproject.rahman.tourbook.Fragments.LogInFragment;
import finalproject.rahman.tourbook.WeatherActivity.WeatherActivity;

public class PreMainActivity extends AppCompatActivity {
    public static CollapsingToolbarLayout collapsingToolbarLayout;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar toolbar;
    private Fragment fragment;
    private Bundle bundle;
    private NavigationView navigationView;

    public Boolean logoutFlag=false;

    public final static String AUTO_LOG_TAG="auto_log";
    public final static String AUTO_LOG_PERMISSION_TRUE="true";
    public final static String AUTO_LOG_PERMISSION_FALSE="false";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_main);
        toolbar=(Toolbar)findViewById(R.id.primary_toolbar);
        setSupportActionBar(toolbar);
        navigationView=(NavigationView)findViewById(R.id.design_navigation_view);
        drawerLayout=(DrawerLayout)findViewById(R.id.pre_main_drawer_layout);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.drawer_nearby_id)
                {
                    Intent intent=new Intent(PreMainActivity.this, NearByActivity.class);
                    startActivity(intent);
                    //Toast.makeText(PreMainActivity.this, "hey+", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId()==R.id.drawer_map_id)
                {
                    Intent intent=new Intent(PreMainActivity.this, MapActivity.class);
                    startActivity(intent);
                    //Toast.makeText(PreMainActivity.this, "hey+", Toast.LENGTH_SHORT).show();
                }
                if(item.getItemId()==R.id.drawer_weather)
                {
                    Intent intent=new Intent(PreMainActivity.this, WeatherActivity.class);
                    startActivity(intent);
                    //Toast.makeText(PreMainActivity.this, "hey+", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
        //navigationView.getMenu().getItem(3).setActionView(R.layout.drawer_menu)

        actionBarDrawerToggle.syncState();
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        collapsingToolbarLayout=
                (CollapsingToolbarLayout)findViewById(R.id.primary_collapsing_toolbar);
        collapsingToolbarLayout.setTitle("This is a collapsing Toolbar");
        toolbar.setTitle("Toolbar");
        toolbarTextAppearence();
        fragment=new LogInFragment();

        bundle=new Bundle();
        bundle.putString(AUTO_LOG_TAG,AUTO_LOG_PERMISSION_TRUE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pre_main_fragment_container,fragment).commit();
        fragment.setArguments(bundle);
        //setMenuVisibility(setMenu);

    }
    public static Menu setMenu;

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //getMenuInflater(null,menu);
        //Toast.makeText(this, collapsingToolbarLayout.isFocusable()+"", Toast.LENGTH_SHORT).show();


        if(!Variables.SHOW_MENU)
        {
            menu.clear();
            getMenuInflater().inflate(R.menu.pre_main_about_ony_menu,menu);
        }
        else
        {
            menu.clear();
            getMenuInflater().inflate(R.menu.pre_main_toolbar_menu,menu);
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater(null,menu);
       // getMenuInflater().inflate(R.menu.pre_main_toolbar_menu,menu);
        //setMenu=menu;
        /*if(!Variables.SHOW_MENU)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }
        else
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(true);
        }*/
        return true;
    }

    public  void setMenuVisibility(Menu menu)
    {
        getMenuInflater().inflate(R.menu.pre_main_toolbar_menu,menu);
        if(!Variables.SHOW_MENU)
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(false);
        }
        else
        {
            for (int i = 0; i < menu.size(); i++)
                menu.getItem(i).setVisible(true);
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;
        if(item.getItemId()==R.id.pre_main_menu_logout)
        {
            bundle.putString(AUTO_LOG_TAG,AUTO_LOG_PERMISSION_FALSE);
            fragment=new LogInFragment();
            fragment.setArguments(bundle);

           // collapsingToolbarLayout.setTitle("Log in");

            getSupportFragmentManager().beginTransaction()
            .replace(R.id.pre_main_fragment_container,fragment).commit();

        }
        /*if(item.getItemId()==R.id.drawer_nearby_id)
        {

        }*/

        //switch ()

        return super.onOptionsItemSelected(item);
    }

    private void toolbarTextAppearence()
    {
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CardView_Dark);
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.RIGHT);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.Base_CardView);
        int color=Color.RED;
        //collapsingToolbarLayout.setExpandedTitleTextColor(new ColorStateList());

    }

}
