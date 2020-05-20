# android_practice
안드로이드의 기본 사용법을 연습합니다.
android

  toast :
Toast.makeText(getApplicationContext(),"toasttest2",Toast.LENGTH_SHORT).show();

mp3:
MediaPlayer mediaPlayer;
버튼클릭: mediaPlayer=MediaPlayer.create(Main2.this,R.raw.dudududu);
mediaPlayer.start(); //.stop();.reset();

서랍:
<include layout="@layout/main3drawer" />
DrawerLayout drawerLayout;
View drawerView;//얘가 linear
버튼 : drawerLayout.openDrawer(drawerView);

인텐트
Intent a=new Intent(MainActivity.this, MAinActivity2.class);
a.putExtra("str","hihello");
startActivity(a);

프레그먼트 버튼 :
xml에 
FrameLayout생성 - frag
FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
 frag1 fragment1 = new frag1();
 transaction.replace(R.id.frag, fragment1);
transaction.commit();

frag1 extends Fragment
return inflater.inflate(R.layout.frag1, container, false);

경고창 버튼 :
AlertDialog.Builder ad=new AlertDialog.Builder(MainActivity.this)
                ad.setIcon(R.mipmap.ic_launcher);
                ad.setTitle("is this true?");
                ad.setMessage("really???!");
                final EditText etet=new EditText(MainActivity.this);
                ad.setView(etet);

 ad.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
             String e=etet.getText().toString();
             et.setText(e);
                dialogInterface.dismiss();
            }
        });
negativebutton에는  dialogInterface.dismiss();
마지막에 ad.show();

리스트:
ArrayAdapter adap=new ArrayAdapter(this,android.R.layout.simple_list_item_1,a);
여기서 a는 리스트
lv.setAdapter(adap);
lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
오버라이드



스피너:
스피너 xml에         android:entries="@array/test" />
얘는 values밑에 array.xml이 있고 array.xml안에 string array name=test임
<string-array name="test">
    <item>1</item>......
spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()



database 구동 : 
class DatabaseHelper extends SQLiteOpenHelper{
onCreate(SQLiteDatabase sqLiteDatabase){
String tabname="customer";
                String sql="create table if not exists "+ tabname + "(_id integer PRIMARY KEY autoincrement, name text,age int, mobile text)";
                sqLiteDatabase.execSQL(sql);
}
onUpgrade(SQLiteDatabase sqLiteDatabase){
sqLiteDatabase.execSQL("drop table if exists "+tabname);
String sql="create table if not exists "+ tabname + "(_id integer PRIMARY KEY autoincrement, name text,age int, mobile text)";
          sqLiteDatabase.execSQL(sql);
}}

클래스 내에 SQLiteDatabase db;

버튼 안에{
String dbname="dfsdfsef";
openDatabase(dbname);
}
openDatabase(String dbname){//내가 정의 하는 것임
 DatabaseHelper helper=new DatabaseHelper(this,dbname,null,3);
        db=helper.getWritableDatabase();
}
버튼2{createTable(tabname)}
createTable(String tabname){
  String sql="create table if not exists "+ tabname + "(_id integer PRIMARY KEY autoincrement, name text,age int, mobile text)";
 db.execSQL(sql);
}
버튼3{
String a=et.getText().toStrng().trim();....> a,b,c;
insertData(a,b,c);
}
insertData(String a,String b,String c){
String sql="insert into customer(name,age,mobile) values(?,?,?)";
Object[] params={a,b,c};
  db.execSQL(sql,params);
}
버튼4{selectDate();}
selectDate(){
 String sql="select name,age,mobile from "+tabname;
 Cursor rs=db.rawQuery(sql,null);
while(rs.moveToNext()){
                String name=rs.getString(0);// 첫번째 칼럼
                int age=rs.getInt(1);
                String mobile=rs.getString(2);
                println("#->"+name+","+age+","+mobile);
            }
}

session 대신 사용 가능 : = sharedpreferencs
   SharedPreferences sp=getSharedPreferences("testsp",Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("key","세션 전달!!!!");
        editor.commit();


받는 곳에서는
TextView tv=findViewById(R.id.tv);
        SharedPreferences sp=getSharedPreferences("testsp", Activity.MODE_PRIVATE);
        String sessiont=sp.getString("key","");   
                                          // get int float.....     두번째 인자는 해당 값없을때 반환!! int라면 0넣으면 됨
        tv.setText(sessiont);


특정 데이터 삭제:
editor.remove("key");
editor.commit();
모든 데이터 삭제:
editor.clear();
editor.commit();


mysql

onCreate{
personList = new ArrayList<HashMap<String, String>>();
Button selectbt=findViewById(R.id.selectbt);
selectbt.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        getData("http://192.168.56.1/PHP_connection.php","s"); //수정 필요
    }
});

crete 밖에서 함수 선언..?
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
                        return sb.toString().trim();   이 리턴값이 밑에 postexcute로 가는것 같아
                } catch (Exception e) {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(String result) {
                myJSON = result;       //db로 부터 받은 문자열들이 있는것 같아
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }
}


얘는 사실 getdata보다 위쪽에 만들어 놨어야해
    protected void showList() {
        try {
            JSONObject jsonObj = new JSONObject(myJSON);   
              // myJSON은 db로 부터받은 문자열(String)이야
            peoples = jsonObj.getJSONArray(TAG_RESULTS);

            for (int i = 0; i < peoples.length(); i++) {
//select 부분이구나, rs 역할인것 같아

                JSONObject c = peoples.getJSONObject(i);
                String id = c.getString(TAG_ID);
                String name = c.getString(TAG_NAME);
                String address = c.getString(TAG_ADD);
                String content = c.getString(TAGC);

                HashMap<String, String> persons = new HashMap<String, String>();
//해쉬 맵에 key : value형식으로 저장하고  그걸 리스트에 push
                persons.put(TAG_ID, id);System.out.println(id);
                persons.put(TAG_NAME, name);System.out.println(name);
                persons.put(TAG_ADD, address);System.out.println(address);
                persons.put(TAGC, content);System.out.println(content);

                personList.add(persons);            }

                ad = new ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, personList) ;
            list.setAdapter(ad);// 이거 하면 출력이지!


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


