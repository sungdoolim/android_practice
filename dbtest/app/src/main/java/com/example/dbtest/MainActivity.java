package com.example.dbtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText et;
    EditText et2;
    EditText et3;
    EditText et4;
    EditText et5;


TextView tv;
SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=findViewById(R.id.et1);   et2=findViewById(R.id.et2);    et3=findViewById(R.id.et3);   et4=findViewById(R.id.et4);    et5=findViewById(R.id.et5);
        tv=findViewById(R.id.tv1);
        Button button=findViewById(R.id.bt1);
        Button intent=findViewById(R.id.intent);
        intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte=new Intent(MainActivity.this,Main2Activity.class);
                startActivity(inte);

            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dbname=et.getText().toString();
                openDatabase(dbname);
            }
        });
        Button button2=findViewById(R.id.bt2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tabname=et2.getText().toString();
                createTable(tabname);

            }
        });
        Button bt3=findViewById(R.id.bt3);
        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=et3.getText().toString().trim();
                String ageStr=et4.getText().toString().trim();
                String mobile=et5.getText().toString().trim();
                int age=-1;
                age=Integer.parseInt(ageStr);
                try{
                    Integer.parseInt(ageStr);

                }catch(Exception e){
                    println("error");
                }
                insertData(name,age,mobile);
            }
        });
        Button bt4=findViewById(R.id.bt4);
        bt4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
selectDate();
            }
        });


    }
    public void openDatabase(String dbname){

        /*
        println("openDatabase() 호출 됨.");
     db=openOrCreateDatabase(dbname,MODE_PRIVATE,null);
    if(db!=null){
        println("db오픈됨");
    }*/
        DatabaseHelper helper=new DatabaseHelper(this,dbname,null,3);
        db=helper.getWritableDatabase();

    }
    public void createTable(String tabname){
        println("createTable호출됨");
        if (db != null) {
            String sql="create table if not exists "+ tabname + "(_id integer PRIMARY KEY autoincrement, name text,age int, mobile text)";
            db.execSQL(sql);
        println("tab생성 완료");
        }else{
            println("db open먼저 해야됨");
        }
    }
    public void println(String data){
        tv.append(data+"\n");
    }
    public void selectDate(){
        println("select 호출됨");
        if(db!=null){
            String tabname=et2.getText().toString();
            String sql="select name,age,mobile from "+tabname;
            Cursor rs=db.rawQuery(sql,null);
            println("조회된 데이터 개수 : "+rs.getCount());
            while(rs.moveToNext()){
                String name=rs.getString(0);// 첫번째 칼럼
                int age=rs.getInt(1);
                String mobile=rs.getString(2);
                println("#->"+name+","+age+","+mobile);
            }
            rs.close();
        }

    }
    public void insertData(String name,int age,String mobile){
        println("insert 호출");
        if(db!=null){
            String sql="insert into customer(name,age,mobile) values(?,?,?)";
            Object[] params={name,age,mobile};

            db.execSQL(sql,params);
            println("데이터 추가 성공");

        }else{
            println("먼저 데이터를 추가하세요");
        }
    }

    class DatabaseHelper extends SQLiteOpenHelper{
        public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            println("onCreate() 허츨");

                String tabname="customer";
                String sql="create table if not exists "+ tabname + "(_id integer PRIMARY KEY autoincrement, name text,age int, mobile text)";
                sqLiteDatabase.execSQL(sql);
                println("tab생성 완료");


        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//db가 원래 있는 경우
            println("on Upgrade 호출됨 :"+i+","+i1);
            if(i1>1){
                String tabname="customer";
                sqLiteDatabase.execSQL("drop table if exists "+tabname);
              println("tab삭제");
                String sql="create table if not exists "+ tabname + "(_id integer PRIMARY KEY autoincrement, name text,age int, mobile text)";
          sqLiteDatabase.execSQL(sql);
          println("tab 새로 생성됨");

            }


        }
    }


}
