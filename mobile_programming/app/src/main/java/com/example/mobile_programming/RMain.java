package com.example.mobile_programming;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.mobile_programming.frag.frag1;
import com.example.mobile_programming.frag.frag2;

import java.util.Calendar;

public class RMain extends AppCompatActivity {
    TextView ctext;//플로팅
    Button bttime;//시간
    Button btcal;//달력
    String NOTIFICATION_CHANNEL_ID;
    private ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_r_main);
        TextView tvc=findViewById(R.id.tvc);
       
        Button call=findViewById(R.id.call);// 전화로 넘기기
        progress = new ProgressDialog(this);// 프로그레스 바 // 진행율
        NOTIFICATION_CHANNEL_ID= "my_channel_id_01";
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel: 010-3152-1372"));
                startActivity(intent);
            }
        });

         ctext=findViewById(R.id.floatevent);
        registerForContextMenu(ctext);// 텍스트 뷰를 컨텍스트로 등록하는 이미 있는 메서드


        // 시간 달력
        bttime =findViewById(R.id.bttime);
        btcal=findViewById(R.id.btcal);

        createNotificationChannel();// 푸시 알림

        RatingBar rtb=findViewById(R.id.rtb);
        rtb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(getBaseContext(),""+v,Toast.LENGTH_SHORT).show();
            }
        });

        Button openfrag1=findViewById(R.id.openfrag1);
        Button openfrag2=findViewById(R.id.openfrag2);
        openfrag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.framecontainer, new frag1(), "one");
                ft.commitAllowingStateLoss();

            }
        });
        openfrag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.framecontainer, new frag2(), "two");
                ft.commitAllowingStateLoss();

            }
        });
    }

    public void ischeck(View v) {// 체크 박스
        switch (v.getId()) {
            case (R.id.cb1):
                Toast.makeText(getBaseContext(), "cb버튼1 테스트!!!", Toast.LENGTH_SHORT).show();
                break;
            case (R.id.cb2):
                Toast.makeText(getBaseContext(), "cb버튼2 테스트!!!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void isradio(View v) {// 라디오 버튼
        switch (v.getId()) {
            case R.id.rb1:
                Toast.makeText(getBaseContext(), "rb버튼1 테스트!!!", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rb2:
                Toast.makeText(getBaseContext(), "rb버튼2 테스트!!!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    public void togglevent(View v){
        boolean on=((ToggleButton)v).isChecked();
        if(on){
            Toast.makeText(getBaseContext(), "토글 온", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getBaseContext(), "토글 오프", Toast.LENGTH_SHORT).show();
        }
    }



    //두개 메서드가 한 세트고 이거 하면 자동으로 오른쪽 상단에 메뉴 탭을 가지게 됨
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




    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("컨텍스트 메뉴");
        menu.add(0, 1, 0, "배경색: RED");
        menu.add(0, 2, 0, "배경색: GREEN");
        menu.add(0, 3, 0, "배경색: BLUE");
    }
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                ctext.setBackgroundColor(Color.RED);
                return true;
            case 2:
                ctext.setBackgroundColor(Color.GREEN);
                return true;
            case 3:
                ctext.setBackgroundColor(Color.BLUE);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    // 팝업 버튼
    public void onpopup(View v){
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.action_bar, popup.getMenu());
        popup.setOnMenuItemClickListener(
                new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getApplicationContext(), "클릭된 팝업 메뉴: "
                                        + item.getTitle(),
                                Toast.LENGTH_SHORT).show();
                        return true;
                    }
                });
        popup.show();
    }


// 시계 달력
    public void callendar(View v){
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

    public void dialog(View v) {
        final Dialog loginDialog = new Dialog(this);
        loginDialog.setContentView(R.layout.custom_dialog);
        loginDialog.setTitle("로그인 화면");
        Button login = (Button) loginDialog.findViewById(R.id.login);
        Button cancel = (Button) loginDialog.findViewById(R.id.cancel);
// 이어서 계속
        final EditText username = (EditText) loginDialog
                .findViewById(R.id.username);
        final EditText password = (EditText) loginDialog
                .findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().trim().length() > 0
                        && password.getText().toString().trim().length() > 0) {
                    Toast.makeText(getApplicationContext(), "로그인 성공",
                            Toast.LENGTH_LONG).show();
                    loginDialog.dismiss();
                } else {
                    Toast.makeText(getApplicationContext(), "다시 입력하시오",
                            Toast.LENGTH_LONG).show();
                }
            }
        });cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDialog.dismiss();
            }
        });
        loginDialog.show();
    }




    //핸드폰 푸시알림!! 메인에서 createnotifica~~예를 부르고... 무슨 notification 아이디도 main에서 설정했엉
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new
                    NotificationChannel(NOTIFICATION_CHANNEL_ID, "My Notifications",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription("Channel description");
            NotificationManager notificationManager = (NotificationManager)
                    getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }
    public void sendNotification(View view) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,
                NOTIFICATION_CHANNEL_ID);
// 알림이 클릭되면, 아래의 인텐트가 전송된다.
        Intent intent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.google.com/"));
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, 0);
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("메일 알림")
                .setContentText("새로운 메일이 도착하였습니다.")
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(/*notification id*/1, notificationBuilder.build());
    }





    //프로그레스 바
    public void progress(View view){
        progress.setCancelable(true);
        progress.setMessage("네트워크에서 다운로드 중입니다. ");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setProgress(0);
        progress.setMax(100);
        progress.show();

        final Thread t = new Thread(){
            @Override
            public void run(){
                int time = 0;
                while(time < 100){
                    try {
                        sleep(200);
                        time += 5;
                        progress.setProgress(time);
                    } catch (InterruptedException e) {
// TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }


}