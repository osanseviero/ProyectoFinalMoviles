package com.example.osanseviero.proyectofinalmoviles;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class VideoSplashActivity extends AppCompatActivity {
    private long waitTime = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_splash);

        ArrayList<String> urls = new ArrayList<String>();
        urls.add("http://ubiquitous.csf.itesm.mx/~raulms/ads/ad9.mp4");
        urls.add("http://ubiquitous.csf.itesm.mx/~raulms/ads/ad18.mp4");
        urls.add("http://ubiquitous.csf.itesm.mx/~raulms/ads/ad2.mp4");

        String url = urls.get((int)(Math.random() * 2));

        final VideoView videoView;
        videoView = (VideoView)findViewById(R.id.videoSplash);
        videoView.setVideoPath(url);
        videoView.start();

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Intent mainIntent = new Intent().setClass(VideoSplashActivity.this, HomeScreenActivity.class);
                startActivity(mainIntent);
            }
        });
    }
}
