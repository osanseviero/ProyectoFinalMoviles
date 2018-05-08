package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ImageSplashActivity extends AppCompatActivity {
    private long waitTime = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_splash);

        ArrayList<String> urls = new ArrayList<String>();
        urls.add("http://ubiquitous.csf.itesm.mx/~raulms/content/TC2024/images/accord.jpg");
        urls.add("http://ubiquitous.csf.itesm.mx/~raulms/content/TC2024/images/astra.jpg");
        urls.add("http://ubiquitous.csf.itesm.mx/~raulms/content/TC2024/images/bora.jpg");
        urls.add("http://ubiquitous.csf.itesm.mx/~raulms/content/TC2024/images/camry.jpg");

        String url = urls.get((int)(Math.random() * 2));

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        ImageRequest requestImage = new ImageRequest(
                url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        Log.v("DBG", "Got image");
                        ImageView iv =  findViewById(R.id.imageSplash);
                        iv.setImageBitmap(response);
                    }
                },
                0,
                0,
                ImageView.ScaleType.CENTER_CROP,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.v("ERROR", "Volley error image:\n" + error.toString());
                        error.printStackTrace();
                    }
                }

        );
        requestQueue.add(requestImage);

        TimerTask task = new TimerTask() {
            public void run() {
                finish();
                Intent mainIntent = new Intent().setClass(ImageSplashActivity.this, HomeScreenActivity.class);
                startActivity(mainIntent);
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, waitTime);
    }
}
