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
    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {//select 부분이구나

                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);
                String content = c.getString(TAGC);

                HashMap<String, String> persons = new HashMap<String, String>();

                persons.put(TAG_ID, id);System.out.println(id);
                persons.put(TAG_NAME, name);System.out.println(name);
                persons.put(TAG_ADD, address);System.out.println(address);
                persons.put(TAGC, content);System.out.println(content);

                personList.add(persons);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getData(String url,final String c,final String[] ar) {
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
                        System.out.println("u로 들어옴");
                        con.setDoOutput(true); // POST 로 데이터를 넘겨주겠다는 옵션
                        con.setRequestMethod("POST");
                        String name = "name=" + ar[0] + "&id=" + ar[1] + "&address=" + ar[2]+ "&pw=" + ar[3];
                        System.out.println(name);
                        System.out.println("post");
                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        System.out.println("1");
                        wr.writeBytes(name);
                        System.out.println("2");
                        wr.flush();   System.out.println("3");
                        wr.close();   System.out.println("4");
                    }else if(c=="d"){
                        System.out.println("d로 들어옴");
                        con.setDoOutput(true); // POST 로 데이터를 넘겨주겠다는 옵션
                        con.setRequestMethod("POST");
                        String name = "id=" + Uri.encode("2");
                        System.out.println("post");
                        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
                        System.out.println("1");
                        wr.writeBytes(name);
                        System.out.println("2");
                        wr.flush();   System.out.println("3");
                        wr.close();   System.out.println("4");
                    }
                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();
                } catch (Exception e) {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }



}
