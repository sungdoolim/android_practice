package com.example.mysqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
        Button smt=findViewById(R.id.submit);
        final String[]ar=new String[4];
        smt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ar[0]=id.getText().toString();
                ar[1]=pw.getText().toString();
                ar[2]=addr.getText().toString();
                ar[3]=name.getText().toString();

                dbc.getData("http://192.168.56.1/register.php","u",ar); //수정 필요x
                Intent intent=new Intent(register.this,MainActivity.class);
                intent.putExtra("str","register success");
                startActivity(intent);
            }
        });
    }
}
