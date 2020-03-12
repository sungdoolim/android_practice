package com.example.mysqltest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultBackoffStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements Runnable {
    ListView listView1;
    List<SampleVO> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        listView1 = (ListView)findViewById(R.id.listView1);
        items = new ArrayList<SampleVO>();
        Thread th = new Thread(this);
       // th.start();// 스프링과 통신
        thread1 thre=new thread1(1,"a","","");
        thre.start();
        System.out.println("thread에서 return 받기 : "+thre.getP());

        try {
            thre.join();
        }catch(Exception e){System.out.println("오 마이 쓰레드 오류....");}System.out.println("thread에서 return 받기2 : "+thre.getP());

    }

    @Override
    public void run() {
        try{
            // httpclient-4.2.2.jar와 httpcore-4.2.2.jar를 mvnrepository에서 찾아 Files에 Download jars를 해 받음
            // app/libs에 추가
            // 이때, android상태에서는 libs가 안보임으로 project로 변경해서
            // lib에 받은 jar파일 2개를 넣어준다.

            // http client 객체/
             HttpClient http = new DefaultHttpClient();
            //HttpClient http = HttpClientBuilder.create().build();

            // NameValuePair : 변수명과 값을 함께 저장하는 객체로 제공되는 객체이다.
            ArrayList<NameValuePair> postData = new ArrayList<>();
// post 방식으로 전달할 값들을 postData 객체에 집어 넣는다.
            postData.add(new BasicNameValuePair("id","id 입니다"));
            postData.add(new BasicNameValuePair("pw","pw 입니다"));   // pw라는 스트링에 패스워드 값을 넣어서 포스트로 전달!!!!!!!!!
// url encoding이 필요한 값들(한글, 특수문자) : 한글은 인코딩안해주면 깨짐으로 인코딩을 한다.
            UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData,"utf-8");

            // post 방식으로 전송하는 객체
            HttpPost httpPost = new HttpPost("http://192.168.56.1:8052/controller/androidtest.json");

            // http클라이언트.execute(httppost객체) : 웬서버에 데이터를 전달
            httpPost.setEntity(request);
// post 방식으로 전송, 응답결과는 response로 넘어옴
            // 결과(json)가 response로 넘어옴
            HttpResponse response = http.execute(httpPost);
            // body에 json 스트링이 넘어옴
            String body = EntityUtils.toString(response.getEntity());
            System.out.println("#################################################"+body);
            // string을 JSONObject로 변환
            JSONObject jsonObj = new JSONObject(body);
// 스프링 컨트롤러에서 리턴해줄 때 저장했던 값을 꺼냄
          //  String message = jsonObj.getString("message");
            // json객체.get("변수명")
            JSONArray jArray = (JSONArray)jsonObj.get("sendData");
            for(int i=0; i<jArray.length();i++){
                // json배열.getJSONObject(인덱스)
                JSONObject row = jArray.getJSONObject(i);
                SampleVO dto = new SampleVO();
                dto.setMno(row.getInt("mno"));
                dto.setFirstName(row.getString("f"));
                dto.setLastName(row.getString("l"));

                // ArrayList에 add
                items.add(dto);
            }
            // 핸들러에게 메시지를 요청
            handler.sendEmptyMessage(0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            // 어댑터 생성
            String[] str = new String[items.size()];
            for (int i = 0; i < str.length; i++) {
                SampleVO dto = items.get(i);
                str[i] = dto.getMno() + "(" + dto.getFirstName() + ")";
                System.out.println(str[i]);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main2Activity.this, android.R.layout.simple_list_item_1, str);
            // adapter와 data 바인딩
            listView1.setAdapter(adapter);
        }

    };

}
