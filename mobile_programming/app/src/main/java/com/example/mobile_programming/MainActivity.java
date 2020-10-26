package com.example.mobile_programming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mobile_programming.frag.frag1;
import com.example.mobile_programming.frag.frag2;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button bttime;
    Button btcal;
    Button ani;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);// 액티비티 화면을 성정하는 메서드

         bttime =findViewById(R.id.bttime);
         btcal=findViewById(R.id.btcal);
         ani=findViewById(R.id.ani);

        registerForContextMenu(btcal);
        Button variousView=findViewById(R.id.variousView);
        Button variousIntent=findViewById(R.id.variousIntent);




        variousView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainActivity.class);    }
        });
        variousIntent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,MainActivity.class);

            }
        });
        ani.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

//        MyView w = new MyView(this);
//        setContentView(w);
//        TextView ctvtest = (TextView) findViewById(R.id.ctvtest);
//        registcontextview(ctvtest);

//final RatingBar ratingbar=(RatingBar) findViewById(R.id.ratingbar);






    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
          super.onCreateContextMenu(menu, v, menuInfo);
          MenuInflater inflater=getMenuInflater();
          inflater.inflate(R.menu.bottomnavi_x,menu);
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.bottom1:Toast.makeText(getApplicationContext(),"뉴",Toast.LENGTH_SHORT).show();
            break;
            default : Toast.makeText(getApplicationContext(),"si",Toast.LENGTH_SHORT).show();
        }

        return super.onContextItemSelected(item);
    }

    public void onclic(View v){
        if(v==btcal){
            final Calendar c=Calendar.getInstance();
            int y=c.get(Calendar.YEAR);
            int m=c.get(Calendar.MONTH);
            int d=c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd=new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    btcal.setText(month+"/"+(month+1)+"/"+year);
                }
            },y,m,d);
        dpd.show();
        }
        if(v==bttime){
            final Calendar c=Calendar.getInstance();
            int mHour=c.get(Calendar.HOUR_OF_DAY);
            int mMinute=c.get(Calendar.MINUTE);
            TimePickerDialog tpd=new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    bttime.setText(hour + " : "+minute);
                }
            },mHour,mMinute,false);
            tpd.show();
        }
    }







//    public void onChanged(double angle){
//        float rating=ratingbar.getRating();
//        if(angle>0&&rating<7){
//            ratingbar.setRating(rating+1.0f);
//        }
//        else if(angle<0){
//            ratingbar.setRating(rating-1.0f);
//        }
//    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.bottomnavi_x,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.bottom1: Toast.makeText(getApplicationContext(),"1111",Toast.LENGTH_SHORT).show();break;
            default: Toast.makeText(getApplicationContext(),"defa",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }


    public void onClicked(View v) {


    }

    public void ischeck(View v) {
        switch (v.getId()) {
            case (R.id.cb1):
                Toast.makeText(getBaseContext(), "cb버튼1 테스트!!!", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.cb2):
                Toast.makeText(getBaseContext(), "cb버튼2 테스트!!!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void isradio(View v) {
        switch (v.getId()) {
            case R.id.rb1:
                Toast.makeText(getBaseContext(), "rb버튼1 테스트!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb2:
                Toast.makeText(getBaseContext(), "rb버튼2 테스트!!!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void istoggle(View v) {
        ToggleButton tg = findViewById(R.id.tg1);
        boolean ist = tg.isChecked();
        if (ist) {
            Toast.makeText(getBaseContext(), "tg 테스트!!!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getBaseContext(), "tg 꺼짐!!!", Toast.LENGTH_SHORT).show();

        }
    }


    class MyView extends View {
        int key;
        String str;
        int x, y;

        public MyView(Context context) {
            super(context);
            setBackgroundColor(Color.YELLOW);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            x = (int) event.getX(0);
            y = (int) event.getY(0);
            if (event.getAction() == MotionEvent.ACTION_UP) {
                str = "up";
            }
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                str = "down";
            }

            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                str = "move";
            }


            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas) {
            Paint paint = new Paint();
            paint.setTextSize(50);
            canvas.drawCircle(x, y, 30, paint);
            canvas.drawText("이벤트 발생" + str + " : " + x + "," + y, x, y + 100, paint);
        }

    }

}