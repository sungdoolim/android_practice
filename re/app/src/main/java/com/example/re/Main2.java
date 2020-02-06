package com.example.re;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.file.Files;

public class Main2 extends AppCompatActivity {
    MediaPlayer mediaPlayer;
     Button mpp;
     Button mps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
       final TextView tv=findViewById(R.id.tv);
        final Button bt= findViewById(R.id.bt);
        mpp= findViewById(R.id.mpp);
        mps= findViewById(R.id.mps);

        Intent inte=getIntent();
      final ImageView iv=findViewById(R.id.iv);
        final String b=inte.getStringExtra("a");
iv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(tv.getText().equals("tkdlek")){
            tv.setText(b);
            Toast.makeText(getApplicationContext(),"toasttest1",Toast.LENGTH_SHORT).show();
        }else{
            tv.setText("tkdlek");
            Toast.makeText(getApplicationContext(),"toasttest2",Toast.LENGTH_SHORT).show();

        }
    }
});
    mpp.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
    mediaPlayer=MediaPlayer.create(Main2.this,R.raw.dudududu);
    mediaPlayer.start();
        }
    });
mps.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
if(mediaPlayer.isPlaying()){
    mediaPlayer.stop();
    mediaPlayer.reset();
}
    }
});



    }

    protected void onDestroy(){
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;
        }
    }



}
