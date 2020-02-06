package com.example.re;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.fragment.app.Fragment;
public class Main4Activity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        Button bt1;
        Button bt2;
        Button bt3;

        bt1=findViewById(R.id.bt1);
        bt2=findViewById(R.id.bt2);
        bt3=findViewById(R.id.bt3);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                frag1 fragment1 = new frag1();
                transaction.replace(R.id.frag, fragment1);
                transaction.commit();


            }
        });   bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                frag2 fragment1 = new frag2();
                transaction.replace(R.id.frag, fragment1);
                transaction.commit();


            }
        });   bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                frag3 fragment1 = new frag3();
                transaction.replace(R.id.frag, fragment1);
                transaction.commit();


            }
        });



    }




}
