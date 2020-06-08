package com.example.applicationloginusephp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

public class FullScreenActivity extends AppCompatActivity {
       VideoView videoView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen);
        String u = "android.resource://com.example.applicationloginusephp/"+R.raw.quran  ;
         String link = getIntent().getStringExtra("link") ;
         Uri uri = Uri.parse(u)   ;
          videoView = findViewById(R.id.videoViewFullscreen) ;
        MediaController mediaController = new MediaController(this) ;
        videoView.setVideoURI(uri);
        videoView.setMediaController(new MediaController(this));
         mediaController.setAnchorView(videoView);
         videoView.requestFocus() ;
         videoView.start();

    }
}
