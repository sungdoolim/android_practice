package com.example.mysqltest;

        import android.content.Intent;
        import android.net.Uri;
        import android.os.Bundle;
//package com.jy.dbconnection;
        import android.app.Activity;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.SimpleAdapter;
        import android.widget.TextView;

        import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.toolbox.StringRequest;
        import com.android.volley.toolbox.Volley;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;
        import org.w3c.dom.Text;

        import java.io.BufferedReader;
        import java.io.DataOutputStream;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;
        import java.util.ArrayList;
        import java.util.HashMap;

        import okhttp3.Call;
        import okhttp3.Callback;


public class MainActivity extends Activity {

    private static final String TAG = "TestActivity";
    private HttpConnection httpConn = HttpConnection.getInstance();


    String myJSON;

    private static final String TAG_RESULTS = "result";
    private static final String TAG_ID = "id";
    private static final String TAG_NAME = "name";
    private static final String TAG_ADD = "address";
    private static final String TAGC = "content";

    JSONArray peoples = null;

    ArrayList<HashMap<String, String>> personList;

    ListView list;
    ArrayAdapter<HashMap<String,String>> ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = (ListView) findViewById(R.id.listView);
        personList = new ArrayList<HashMap<String, String>>();
        TextView tv=findViewById(R.id.tv);
        Intent intent=getIntent();
        tv.setText(intent.getStringExtra("str"));

Button register=findViewById(R.id.register);
register.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent=new Intent(MainActivity.this,register.class);
        startActivity(intent);

    }
});

Button okh=findViewById(R.id.okh);
okh.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        sendData();
    }
});



Button selectbt=findViewById(R.id.selectbt);
selectbt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getData("http://192.168.56.1/PHP_connection.php","s"); //수정 필요
    }
});
Button deletebt=findViewById(R.id.deletebt);
deletebt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getData("http://192.168.56.1/delete.php","d"); //수정 필요
    }
});
Button updatebt=findViewById(R.id.updatebt);
updatebt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getData("http://192.168.56.1/register.php","u"); //수정 필요
            }
        });
Button resetbt=findViewById(R.id.resetbt);
resetbt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
System.out.println(personList.isEmpty());//false
        personList.clear();
        System.out.println(personList.isEmpty());//true
// 리스트 뷰 비우기 찾아보기!!!!
//얼마나 많은 데이터가 저장될까?

    }
});





    }




    /** 웹 서버로 데이터 전송 */
    private void sendData() {
// 네트워크 통신하는 작업은 무조건 작업스레드를 생성해서 호출 해줄 것!!
        new Thread() {
            public void run() {
// 파라미터 2개와 미리정의해논 콜백함수를 매개변수로 전달하여 호출
                httpConn.requestWebServer("데이터1","데이터2", callback);
            }
        }.start();;
    }

    private final Callback callback;

    {
        callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "콜백오류:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {
                String body = response.body().string();
                Log.d(TAG, "서버에서 응답한 Body:" + body);
            }
        };
    }


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

          /*  ListAdapter adapter = new SimpleAdapter(
                    MainActivity.this, personList, R.layout.list_item,
                    new String[]{TAG_ID, TAG_NAME, TAG_ADD,TAGC},
                    new int[]{R.id.id, R.id.name, R.id.address,R.id.content}
            );
*/
            ad = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, personList) ;
            list.setAdapter(ad);
          //  list.setAdapter(adapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void getData(String url,final String c) {
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
                        String name = "name=" + Uri.encode("2") + "&id=" + Uri.encode("2") + "&address=" + Uri.encode("2")+ "&content=" + Uri.encode("2");
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