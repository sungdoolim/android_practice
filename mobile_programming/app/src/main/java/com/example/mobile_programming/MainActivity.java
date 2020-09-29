package com.example.mobile_programming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setContentView(R.layout.activity_main);// 액티비티 화면을 성정하는 메서드

MyView w=new MyView(this);
setContentView(w);
//final RatingBar ratingbar=(RatingBar) findViewById(R.id.ratingbar);

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


    class MyView extends View{
        int key;
        String str;
        int x,y;
        public MyView(Context context){
            super(context);
            setBackgroundColor(Color.YELLOW);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            x=(int)event.getX(0);
            y=(int)event.getY(0);
            if(event.getAction()==MotionEvent.ACTION_UP){
                str="up";
            }
            if(event.getAction()==MotionEvent.ACTION_DOWN){
                str="down";
            }

            if(event.getAction()==MotionEvent.ACTION_MOVE){
                str="move";
            }


            invalidate();
            return true;
        }

        @Override
        protected void onDraw(Canvas canvas){
            Paint paint=new Paint();
            paint.setTextSize(50);
            canvas.drawCircle(x,y,30,paint);
            canvas.drawText("이벤트 발생"+str+" : "+x+","+y,x,y+100,paint);
        }

    }

}