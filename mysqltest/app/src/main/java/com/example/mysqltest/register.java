package com.example.mysqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class register extends AppCompatActivity {


    //EditText 여기서 전역으로 받으니까 에러뜨네....
    dbconnect dbc=new dbconnect();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        final EditText id=findViewById(R.id.id);
        final EditText pw=findViewById(R.id.pw);
        final EditText addr=findViewById(R.id.address);
        final EditText name=findViewById(R.id.name);
        TextView tv=findViewById(R.id.tv);
        SharedPreferences sp=getSharedPreferences("testsp", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.remove("key");
        editor.commit();
        editor.putString("key","재전");
        editor.commit();
        String sessiont=sp.getString("key","전달 값 없음");
        tv.setText(sessiont);
        Button smt=findViewById(R.id.submit);
        final String[]ar=new String[4];
        Intent intent=getIntent();
        String id1=intent.getStringExtra("id");
        String pw1=intent.getStringExtra("pw");
        System.out.println("#################################################################################################33");
        System.out.println("id = "+id1+"pw = "+pw1);
        smt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ar[0]=id.getText().toString();
                ar[1]=pw.getText().toString();
                ar[2]=addr.getText().toString();
                ar[3]=name.getText().toString();


                dbc.getData("http://192.168.56.1/register.php","u",ar); //수정 필요x

                //경고창 띄우기
                AlertDialog.Builder ad=new AlertDialog.Builder(register.this);
                ad.setTitle("축하합니다");
                ad.setMessage("정상적으로 가입되었습니다");
                ad.show();// 이렇게 하니까 뜬다음에 바로 사라지네.....

                Intent intent=new Intent(register.this,MainActivity.class);
                intent.putExtra("str","register success");
                startActivity(intent);
            }
        });
    }
}
