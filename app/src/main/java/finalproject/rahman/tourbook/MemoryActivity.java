/*
package finalproject.rahman.tourbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import finalproject.rahman.tourbook.Fragments.GalleryFragment;
import finalproject.rahman.tourbook.Fragments.PagerFragment;

public class MemoryActivity extends AppCompatActivity {
    private Button openCameraBtn;
    private String appName="/TourBook";
    private String userName="/User2";
    private String eventName="/Event1";
    private Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory);
        bundle=new Bundle();
        bundle.putString("appName",appName);
        bundle.putString("userName",userName);
        bundle.putString("eventName",eventName);

        openCameraBtn =(Button)findViewById(R.id.memorygallery_open_camera_btn);
        openCameraBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MemoryActivity.this,CameraActivity.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });


        //showClickedImageIv=(ImageView)findViewById(R.id.clicked_image_iv);
*/
/*        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // Fragment fragment=getclickedImage(list,position);
                adapter=new ViewPagerAdapter(getSupportFragmentManager(),list,position);
                imagePager.setAdapter(adapter);
                getSupportFragmentManager().beginTransaction()

                        .replace(R.id.fragment_holder,getclickedImage(list,position)).commit();
                Toast.makeText(GalleryActivity.this, "clicked "+position, Toast.LENGTH_SHORT).show();
                Bitmap bitmap=BitmapFactory.decodeFile(list.get(position));
                showClickedImageIv.setImageBitmap(bitmap);

            }
        });*//*

        Fragment fragment=new GalleryFragment();
        fragment.setArguments(bundle);
        Toast.makeText(this, bundle.getString("appName")+"", Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.memorygallery_fragment_container,fragment).commit();
    }


    PagerFragment getclickedImage(ArrayList<String> list, int position)
    {
        PagerFragment fragment=new PagerFragment();
        Bundle bundle=new Bundle();
        bundle.putStringArrayList("list",list);
        bundle.putInt("pos",position);
        fragment.setArguments(bundle);
        return fragment;

    }


}
*/
