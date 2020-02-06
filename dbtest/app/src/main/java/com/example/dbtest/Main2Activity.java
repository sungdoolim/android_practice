package com.example.dbtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
DrawerLayout drawerLayout;
View drawer;
ListView lv;
Spinner spin;
FrameLayout frame;
    TextView tv;TextView tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        drawerLayout=findViewById(R.id.drawerback);
        drawer=findViewById(R.id.drawer);
        Button draweropen=findViewById(R.id.drawerbt);
        Button drawerclose=findViewById(R.id.drawerbtclose);
        lv=findViewById(R.id.lv);
        spin=findViewById(R.id.spinner);
        frame=findViewById(R.id.frame);
        Button frag1=findViewById(R.id.frag1);
        Button frag2=findViewById(R.id.frag2);
        tv=findViewById(R.id.tv);
        tv2=findViewById(R.id.tvlv);

        List<String> a=new ArrayList<>();
        a.add("hi");a.add("hello");a.add("안녕");
        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,a);
        lv.setAdapter(adapter);
        draweropen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(drawer);
                Toast.makeText(getApplicationContext(),"toast test 1",Toast.LENGTH_SHORT).show();


                AlertDialog.Builder ad=new AlertDialog.Builder(Main2Activity.this);
                ad.setTitle("hi");
ad.setMessage("hello");
final EditText et=new EditText(Main2Activity.this);
ad.setView(et);
ad.setPositiveButton("확인",new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        String e=et.getText().toString();
        tv.setText(e);
        dialogInterface.dismiss();
    }
});
ad.setNegativeButton("취소", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.dismiss();
    }
});
                ad.show();


            }
        });
        drawerclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            drawerLayout.closeDrawers();
            }
        });


      spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {// select라는거 주의
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              tv2.setText("spin 테스트"+i);
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {

          }
      });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            tv.setText("lv 테스트"+i);
            }
        });

        frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                frag1 f=new frag1();
                transaction.replace(R.id.frame,f);
                transaction.commit();
            }
        });
        frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                frag2 f2=new frag2();
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame,f2);
            transaction.commit();
            }
        });
        //-------------------------------









    }
}
