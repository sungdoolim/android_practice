package com.example.re;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText et;
    String str="hi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       final TextView tv=findViewById(R.id.tv);
        Button bt=findViewById(R.id.bt);
        Button bt2=findViewById(R.id.bt2);
        Button bt3=(Button)findViewById(R.id.bt3);
        Button bt4=(Button)findViewById(R.id.bt4);
        Button bt5=findViewById(R.id.bt5);


        Spinner spin=findViewById(R.id.spinner);

        et=(EditText)findViewById(R.id.et);
      final EditText et2=(EditText)findViewById(R.id.et2);
       final EditText et1=(EditText)findViewById(R.id.et1);
        ListView lv=findViewById(R.id.lv);





        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {/*여기 주의 select!!!!*/
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tv.setText("a"+i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            tv.setText("bb");
            }
        });


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tv.getText().equals("바꾸자")){
                    tv.setText("바꼈군");
                }
                else {
                    tv.setText("바꾸자");
                }
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Main3Activity.class);
                startActivity(intent);
            }
        });
bt4.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,Main4Activity.class);
        startActivity(intent);
    }
});
        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intest=new Intent(MainActivity.this,Main2.class);
                intest.putExtra("a","전송!!!");
                startActivity(intest);

            }
        });
        bt5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this);
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setTitle("is this true?");
                ad.setMessage("really???!");
                final EditText etet=new EditText(MainActivity.this);
                ad.setView(etet);
        ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             String e=etet.getText().toString();
             et.setText(e);
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
List<String> a=new ArrayList<String>();
a.add("staris");
a.add("love");
a.add("hama");
ArrayAdapter adap=new ArrayAdapter(this,android.R.layout.simple_list_item_1,a);


lv.setAdapter(adap);
//adap.notifyDataSetChanged();
lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        tv.setText("list test "+i);
    }
});

        et.setText(str);

        SharedPreferences sh=getSharedPreferences("일치시켜야함",0);
        String str2=sh.getString("sta","");
        et.setText(str2);

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sh=getSharedPreferences("일치시켜야함",0);
        SharedPreferences.Editor ed=sh.edit();
        String str2=et.getText().toString();
        ed.putString("sta",str2);
ed.commit();


    }
}
