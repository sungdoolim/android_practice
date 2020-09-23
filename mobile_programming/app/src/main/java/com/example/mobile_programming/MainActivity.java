package com.example.mobile_programming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// 액티비티 화면을 성정하는 메서드





    }


    public void onClicked(View v){


    }
    public void ischeck(View v){
        switch(v.getId()){
            case(R.id.cb1):Toast.makeText(getBaseContext(),"cb버튼1 테스트!!!",Toast.LENGTH_SHORT).show();
            break;
            case(R.id.cb2):Toast.makeText(getBaseContext(),"cb버튼2 테스트!!!",Toast.LENGTH_SHORT).show();
            break;
        }
    }
    public void isradio(View v){
        switch(v.getId()){
            case R.id.rb1:Toast.makeText(getBaseContext(),"rb버튼1 테스트!!!",Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb2:Toast.makeText(getBaseContext(),"rb버튼2 테스트!!!",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void istoggle(View v){
        ToggleButton tg=findViewById(R.id.tg1);
        boolean ist=tg.isChecked();
       if(ist){
           Toast.makeText(getBaseContext(),"tg 테스트!!!",Toast.LENGTH_SHORT).show();
       }else{
           Toast.makeText(getBaseContext(),"tg 꺼짐!!!",Toast.LENGTH_SHORT).show();
           
       }
    }


}