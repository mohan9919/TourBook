package finalproject.rahman.tourbook;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.File;
import java.util.Calendar;

import finalproject.rahman.tourbook.Fragments.MemoryFragment;


public class CameraActivity extends AppCompatActivity {

    File imagesFolder;
    Calendar calendar;
    String appName;
    String userName;
    String eventName;
    //String combined;
    //Intent imageIntent;
    public static final int IMAGE_CAPTURE_REQUEST=1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_camera);
        Bundle bundle=new Bundle();
        bundle=getIntent().getBundleExtra("bundle");
        appName=bundle.getString("appName");
       // Toast.makeText(this, appName+userName+eventName+"", Toast.LENGTH_SHORT).show();
        userName=bundle.getString("userName");
        eventName=bundle.getString("eventName");
        //Toast.makeText(this, appName+userName+eventName+"", Toast.LENGTH_SHORT).show();

        startCamera(appName,userName,eventName);
       // startCamera(appName,userName,eventName);
    }

    private static String getImageFolderEndDirectory(String appName , String userName, String eventName )
    {
        String combined=appName+"/"+userName+"/"+eventName;
        return combined;
    }

    private static File getImageFolderDirectory(String endDirectoryFolder)
    {
        File imageFile=new File(Environment.getExternalStorageDirectory(),endDirectoryFolder);
        return imageFile;
    }
    private static boolean makeImageFolder(File file)
    {
        boolean isCreated=file.exists();
        if(isCreated)
        {
            //Toast.makeText(this, "if ;" + file.exists(), Toast.LENGTH_SHORT).show();
        }
        else
        {
            isCreated =file.mkdirs();
           // Toast.makeText(this, "else ;" + file.exists(), Toast.LENGTH_SHORT).show();
        }
        return isCreated;
    }

    private String getImageEndDirectory(String eventName)
    {
        Calendar calendar=Calendar.getInstance();
        String endPath=eventName+calendar.getTimeInMillis()+".jpg";
        return endPath;
    }
    private Uri getImageUri(File parentDirectory,String endDirectory)
    {
        File imageDirectory = new File(parentDirectory,endDirectory);
        Uri imageUri = Uri.fromFile(imageDirectory);
        return imageUri;
    }
    private Intent getIntent(Uri imageUri){
        Intent imageIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        return imageIntent;
    }


    private void startCamera(String appName, String userName, String eventName)
    {
        String endDirFolder=getImageFolderEndDirectory(appName,userName,eventName);
        File imageFolder=getImageFolderDirectory(endDirFolder);
        makeImageFolder(imageFolder);
        String endDirImg=getImageEndDirectory(eventName);
        Uri imageUri=getImageUri(imageFolder,endDirImg);
        Intent imageIntent=getIntent(imageUri);
        startActivityForResult(imageIntent,IMAGE_CAPTURE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == IMAGE_CAPTURE_REQUEST)
        {
            if (resultCode == RESULT_OK)
            {
                startCamera(appName,userName,eventName);
                //startActivityForResult(getIntent(getImageDirectoryUri(getImageFolderDirectory(getImageFolderEndDirectory(userName,eventName)),getImageFolderEndDirectory(userName,eventName))),IMAGE_CAPTURE_REQUEST);
            }

            else if (resultCode == RESULT_CANCELED)
            {
                /*Intent intent = new Intent(CameraActivity.this,PreMainActivity.class);
                startActivity(intent);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.pre_main_fragment_container,new MemoryFragment())
                        .commit();*/
            }
        }
    }
    public static void createDirectory(String appName,String userName,String eventName)
    {
        String endDirFolder=CameraActivity.getImageFolderEndDirectory(appName,userName,eventName);
        File imageFolder=getImageFolderDirectory(endDirFolder);
        makeImageFolder(imageFolder);
    }

}
