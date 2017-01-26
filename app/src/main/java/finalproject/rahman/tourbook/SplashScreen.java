package finalproject.rahman.tourbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class SplashScreen extends AppCompatActivity {
    ImageView logoIM;
    ImageView glideLogoIM;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        glideLogoIM = (ImageView)findViewById(R.id.glideLogo_IV_id) ;
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(glideLogoIM);
        Glide.with(SplashScreen.this).load(R.raw.tourbooklogogif).into(imageViewTarget);

        Thread myThread= new Thread(){

            @Override
            public void run() {
                try {
                    sleep(3000);
                    Intent intent= new Intent(getApplicationContext(),PreMainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        myThread.start();
    }
}
