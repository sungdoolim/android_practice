package com.example.mysqltest;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class thread1 extends Thread{
    int num;
    String id,name,address,pw;


    public thread1(int num,String id,String name,String address) { // 초기화 작업 this.threadNum = threadNum;
            this.num=num;
            this.id=id;
            this.name=name;
            this.address=address;


         }

    public void run(){
switch (num){
    case 1://getPassword()
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
            postData.add(new BasicNameValuePair("id",id));
           // postData.add(new BasicNameValuePair("pw","pw 입니다"));   // pw라는 스트링에 패스워드 값을 넣어서 포스트로 전달!!!!!!!!!
// url encoding이 필요한 값들(한글, 특수문자) : 한글은 인코딩안해주면 깨짐으로 인코딩을 한다.
            UrlEncodedFormEntity request = new UrlEncodedFormEntity(postData,"utf-8");

            // post 방식으로 전송하는 객체
            HttpPost httpPost = new HttpPost("http://192.168.56.1:8052/controller/AgetPassword.json");

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
             pw=jsonObj.getString("pw");
            System.out.println("스트링 변환 : "+pw);

// 스프링 컨트롤러에서 리턴해줄 때 저장했던 값을 꺼냄
            //  String message = jsonObj.getString("message");
            // json객체.get("변수명")

        }catch (Exception e){
            e.printStackTrace();
        }



        break;
    case 2:
        break;
    default:
        break;
}



    }
    String getP(){

        return pw;
    }


}
