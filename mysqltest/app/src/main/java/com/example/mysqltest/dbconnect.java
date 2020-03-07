package com.example.mysqltest;

import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.json.JSONArray;
public class dbconnect {
    String myJSON;
    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";
    private static final String TAGC = "content";
    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;
    ArrayAdapter<HashMap<String,String>> ad;

    public String getData(String url,final String c,final String[] ar) {
        String resultString=new String();

        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();


                    if(c=="u") {
                        con.setDoOutput(true); // POST 로 데이터를 넘겨주겠다는 옵션
                        con.setRequestMethod("POST");
                        String name = "name=" + ar[0] + "&id=" + ar[1] + "&address=" + ar[2]+ "&pw=" + ar[3];

                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

                        wr.writeBytes(name);

                        wr.flush();
                        wr.close();
                    }else if(c=="d"){

                        con.setDoOutput(true); // POST 로 데이터를 넘겨주겠다는 옵션
                        con.setRequestMethod("POST");
                        String name = "id=" + Uri.encode("2");

                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

                        wr.writeBytes(name);

                        wr.flush();
                        wr.close();
                    }else if(c.equals("selid")){

                        con.setDoOutput(true); // POST 로 데이터를 넘겨주겠다는 옵션
                        con.setRequestMethod("POST");
                        String name="id="+ar[0];
                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        wr.writeBytes(name);
                       System.out.println("#########################################################select id test.....");
                        wr.flush();
                        wr.close();

                    }


                    System.out.println("############################################################### select 부분 이동.?");
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    } System.out.println("결과값 #########################################:"+sb.toString().trim());
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
            }
        }
        GetDataJSON g = new GetDataJSON();
try {
    return g.execute(url).get();
}catch(Exception e){
    return e.getMessage();
        }
}



}
