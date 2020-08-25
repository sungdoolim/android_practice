package com.example.myhairdiary_c

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myhairdiary_c.designers.designer
import com.example.myhairdiary_c.designers.designer_list
import com.example.myhairdiary_c.firedb.fireDB
import com.example.myhairdiary_c.main.Home2
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_main.*
import org.eazegraph.lib.models.BarModel
import org.tensorflow.lite.DataType
import org.tensorflow.lite.Interpreter
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.FileInputStream
import java.io.IOException
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

// 임시 로그인/ 임시 네이버 로그인/ 임시 색상지정 코드입니다.




class MainActivity : AppCompatActivity() {
    val GALLERY=0
    var SAVED_STATE_KEY_COLOR = "saved_state_key_color"
    var  INITIAL_COLOR: Long = 0xFFFF8000
    fun  getTfliteInterpreter(modelPath: String): Interpreter? {
        try{
            return loadModelFile(this, modelPath)?.let { Interpreter(it) }
        }catch (e: Exception){
            e.printStackTrace()
        }
        return null
    }

    @Throws(IOException::class)
    private fun loadModelFile(activity: Activity, modelPath: String): MappedByteBuffer? {
        val fileDescriptor: AssetFileDescriptor = activity.assets.openFd(modelPath)
        val inputStream: FileInputStream = FileInputStream(fileDescriptor.getFileDescriptor())
        val fileChannel = inputStream.channel
        val startOffset: Long = fileDescriptor.getStartOffset()
        val declaredLength: Long = fileDescriptor.getDeclaredLength()
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //tf
        val tflite=getTfliteInterpreter("first_model.tflite")
        if (tflite != null) {
            val input=1
            val input2=intArrayOf(25)
            val inp=TensorBuffer.createFixedSize(input2, DataType.UINT8)
            val output=intArrayOf(1,3)
            tflite.run(input2, output)

            Toast.makeText(this, "냐옹...", Toast.LENGTH_SHORT).show()


        }else{
            Toast.makeText(this, "실패", Toast.LENGTH_SHORT).show()
        }


        wvN_login.settings.javaScriptEnabled=true
        wvN_login.webViewClient= WebViewClient()
        wvN_login.webChromeClient= WebChromeClient()
        wvN_login.loadUrl("http://172.30.1.8:8052/web/nlogin.do")
        //  wvnaver.loadUrl("https://www.naver.com")
        val webSettings: WebSettings = wvN_login.getSettings()
        webSettings.javaScriptEnabled = true
        wvN_login.addJavascriptInterface(AndroidBridge(this), "MyTestApp")
        wvN_login.addJavascriptInterface(AndroidBridge(this), "MyTestApp_logout")
        // 네이버 로그인을 웹으로 구현하고 로그인시 정보를 가져옵니다.
        // 앱에서만 로그인하는 방법을 다시 찾아야 할것 같습니다.

        Nloginbt.setOnClickListener(){
        wvN_login.reload()
        }
        val pref=getSharedPreferences("session", 0)
        var edit=pref.edit()
        val db= fireDB(this)

        register_bt.setOnClickListener(){
            var id=etid.getText()
            var name=etpw.getText()
            db.createData(id.toString(), name.toString())
            var intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
            // 회원 가입하면 db에 넣습니다.
        }
        login_bt.setOnClickListener(){
            //로그인시 모든 session유지를 위해 preference를 사용합니다.

            var id=etloginid.text

            edit.putString("id", id.toString())
            edit.apply()
            var firestore = FirebaseFirestore.getInstance()
            firestore?.collection("hair_diary").whereEqualTo("id", id.toString()).get()
                .addOnCompleteListener {
                    // 회원 정보를 id를 이용하여 가져와서 저장합니다.
                    // 임의의 로그인이기에 회원가입 여부와 상관없습니다.
                    var dclass:designer=designer()
                    if(it.isSuccessful){
                        for(dc in it.result!!.documents){
                           dclass=dc.toObject(designer::class.java)!!

                            edit.putInt("perm", dclass.perm)
                            edit.putString("name", dclass.name)
                            edit.putInt("year", dclass.year)
                            edit.putInt("index", dclass.index)
                            edit.putString("memo", dclass.memo)
                            edit.putString("phone", dclass.phone)
                            edit.putInt("age", dclass.age)
                            edit.putString("profile", dclass.profile)
                            edit.putString("major", dclass.major)
                            edit.putString("major_length", dclass.major_length)
                            edit.putString("region", dclass.region)
                            edit.apply()
                        }
                        var intent= Intent(this, Home2::class.java)

                        startActivity(intent)
                    }else{
                        println("fail")
                    }
                }
        }
        profilephoto.setOnClickListener(){
            edit.putInt("happen", 0)
            edit.apply()
         openAlbum()
        }
        upphoto.setOnClickListener(){
            edit.putInt("happen", 1)
            edit.apply()
            openAlbum()
        }
        upphoto_customer.setOnClickListener(){
            edit.putInt("happen", 2)
            edit.apply()
            openAlbum()
        }
        dlist.setOnClickListener(){
            var intent= Intent(this, designer_list::class.java)
            startActivity(intent)
        }

        // 색상 선택등....
        pichart()
        barchart()
        colorpic(savedInstanceState)
// 파이차트/ 바차트/ 색상 선택등의 메서드입니다.
        //나중에 디자이너 부분에서 사용될 듯 합니다.

    }
    fun pichart(){
        val colorArray=ArrayList<Int>()
        colorArray.add(Color.LTGRAY)
        colorArray.add(Color.BLUE)
        colorArray.add(Color.RED)
        val pieDataSet: PieDataSet = PieDataSet(data1(), "설문!!")
        pieDataSet.setColors(colorArray)
        val pieData: PieData = PieData(pieDataSet)
        picChart.setDrawEntryLabels(true);
        picChart.setUsePercentValues(true)
        pieData.setValueTextSize(30f)
        picChart.setCenterTextSize(25f)
        picChart.holeRadius=30f
        picChart.setData(pieData)
        picChart.invalidate();
    }
    fun data1(): ArrayList<PieEntry>? {
        val datavalue: ArrayList<PieEntry> = ArrayList()
        datavalue.add(PieEntry(20f, "무응답"))
        datavalue.add(PieEntry(45f, "냐옹"))
        datavalue.add(PieEntry(35f, "멍멍"))
        return datavalue
    }
    fun barchart(){
        tab1_chart_2!!.clearChart()
        tab1_chart_2!!.addBar(BarModel("1", 10f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("2", 10f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("3", 10f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("4", 20f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("5", 10f, -0xa9480f))
        tab1_chart_2!!.addBar(BarModel("6", 10f, -0xa9480f))
        tab1_chart_2!!.startAnimation()
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(SAVED_STATE_KEY_COLOR, colorPicker.getColor())
        // Toast.makeText(this,"onsaveinstancestate",Toast.LENGTH_SHORT).show()
    }
    fun colorpic(savedInstanceState: Bundle?) {
        colorPicker.subscribe { color, fromUser, shouldPropagate ->
            var c=color.toString()

            // pickedColor.setBackgroundColor(Integer.parseInt(c))
            //  Toast.makeText(this,"setbackground : ${color.toString()}",Toast.LENGTH_SHORT).show()
            // colorHex.text=colorHex(color).toString()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //   window.statusBarColor = Integer.parseInt(c)
            }
            val actionBar: ActionBar? = supportActionBar
           // if (actionBar != null) {// 여기서 색상 바뀌는 거 확인 가능
                //    Toast.makeText(this,"",Toast.LENGTH_SHORT).show()
               // actionBar.setBackgroundDrawable(ColorDrawable(Integer.parseInt(c)))
                println("색상 : ${c}")
                val a = Color.alpha(color)
                val r = Color.red(color)
                val g = Color.green(color)
                val b = Color.blue(color)
                var R= select_color(r)
                var G= select_color(g)
                var B= select_color(b)//27가지 경우
                println("alpha : $a")
                println("red : $r")
                println("green : $g")
                println("blue : $b")

                iffunc(R, G, B)

         //   }
        }

        var color: Long = INITIAL_COLOR
        if (savedInstanceState != null) {
            color = savedInstanceState.getInt(SAVED_STATE_KEY_COLOR, INITIAL_COLOR.toInt()).toLong()

            // Toast.makeText(this,"saved instance state",Toast.LENGTH_SHORT).show()
        }

        colorPicker.setInitialColor(color.toInt())
    }
    fun iffunc(R: Int, G: Int, B: Int){
        // 선택한 색상을 추론합니다. 많은 경우의 수를 하드코딩했습니다.

        /*13 개
    black
    blue
    sea
    sky
    green
    mint
    pink
    purple
    yellow
    white
    grass
    red
    orange
         */
        if(R==0&&G==0&&B==0){
            println("black")
        }else if(R==0&&G==0&&B==2){
            println("blue")
        }

        else if(R==0&&G==1&&B==1){
         //   println("sky")
            println("blue")
        }else if(R==0&&G==1&&B==2){
        //    println("sea")
            println("blue")
        }

        else if(R==0&&G==2&&B==0){
            println("green")

        }
        else if(R==0&&G==2&&B==1){
         //   println("mint")
            println("green")
        }else if(R==0&&G==2&&B==2){
         //   println("sky")
            println("blue")
        }
        else if(R==1&&G==0&&B==1){
      //      println("pink")
            println("purple")
        }else if(R==1&&G==0&&B==2){
            println("purple")
        }
        else if(R==1&&G==1&&B==0){
            println("yellow")
        }else if(R==1&&G==1&&B==1){
            println("white")

        }else if(R==1&&G==1&&B==2){
          //  println("sea")
            println("blue")

        }

        else if(R==1&&G==2&&B==0){
            println("green")
          //  println("grass")
        }
        else if(R==1&&G==2&&B==1){

            println("green")

        }else if(R==1&&G==2&&B==2){
        //    println("sky")
            println("blue")
        }


        else if(R==2&&G==0&&B==0){
            println("red")
        }else if(R==2&&G==0&&B==1){
            println("red")
        }else if(R==2&&G==0&&B==2){
            //println("pink")
            println("purple")
        }

        else if(R==2&&G==1&&B==0){
            println("oragne")
        }else if(R==2&&G==1&&B==1){
            println("red")
        }else if(R==2&&G==1&&B==2){
           // println("pink")
            println("purple")
        }

        else if(R==2&&G==2&&B==0){
            println("yellow")
           }
        else if(R==2&&G==2&&B==1){
            println("yellow")
         }else if(R==2&&G==2&&B==2) {
            println("white")
         }else{
        }

    }
    fun select_color(r: Int):Int{
        if(r<100) {
            return 0
        }else if(r>=100&&r<220){
            return 1
        }
        else{
            return 2
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
    }

    public fun openAlbum() {
        // 휴대폰 내에 앨범을 엽니다.
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"

        val pref=getSharedPreferences("session", 0)
        println("openalbum ${pref.getString("id", "")}")
        startActivityForResult(intent, GALLERY)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
// 앨범이 선택되면 실행되는 듯 합니다.

        val pref=getSharedPreferences("session", 0)
        var happen=pref.getInt("happen", 0)
        var index=pref.getInt("index", -1)
        if(requestCode==GALLERY){
            var photoUri=data?.data!!
            //album_imageview.setImageURI(photoUri)
            when(happen){
                1 -> {
                    var builder = AlertDialog.Builder(this)
                    builder.setTitle("사진 올리기")
                    val li = LinearLayout(this)
                    li.setOrientation(LinearLayout.VERTICAL)
                    val et = EditText(this)
                    et.width = 300
                    et.hint = "제목"

                    val et2 = EditText(this)
                    et2.width = 300
                    et2.hint = "스타일 : (~컷)"
                    val et3 = EditText(this)
                    et3.width = 300
                    et3.hint = "기장 :(단발,장발,중간)"
                    val et4 = EditText(this)
                    et4.width = 300
                    et4.hint = "성별"
                    li.addView(et)
                    li.addView(et2)
                    li.addView(et3)
                    li.addView(et4)
                    builder.setView(li).setPositiveButton("확인") { dialogInterface, i ->
                        var style = et2.text.toString()
                        val name = et.text.toString()
                        val length = et3.text.toString()
                        val gender = et4.text.toString()
                        uploadPhoto(photoUri, index, name, style, length, gender)
                    }.setNegativeButton("취소") { dialogInterface, i ->
                        ""
                    }.show()
                }
                2 -> uploadPhoto_diary_to_Customer(photoUri)
                else-> uploadPhoto_profile(photoUri)
            }
        }
    }
    fun uploadPhoto_profile(photoUri: Uri) {
        // 프로필 사진을 올리고, 그 url을 profile필드에 저장합니다.
        var db=fireDB(this)
        val pref=getSharedPreferences("session", 0)
        val edit=pref.edit()
        var id=pref.getString("id", "").toString()
        if(id==""){return ;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child("profile")
        storageRef.putFile(photoUri).addOnSuccessListener {
           storageRef.downloadUrl.addOnSuccessListener { uri->
               db.updateData_one("hair_diary", "profile", uri.toString(), id)
               edit.putString("profile", uri.toString())
               edit.apply()

           }
            Toast.makeText(this, "프로필 바꾸기 성공", Toast.LENGTH_LONG).show()

        }

    }
    fun deletePhoto() {
        // 이거 삭제인데 수정은 그냥 같은 이름으로 putfile하면 되는 거잖아???
        FirebaseStorage.getInstance().reference.child("images")
            .child("").delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Delete photo completed", Toast.LENGTH_LONG).show()
            }
    }


    fun uploadPhoto_diary_to_Customer(photoUri: Uri) {
        // 디자이너 폴더 -> customer 폴더를 만들고 그 안에 사진을 올립니다.
        var db=fireDB(this)
        val pref=getSharedPreferences("session", 0)
        var id=pref.getString("id", "").toString()
        if(id==""){return ;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child("profile")
        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                db.updateData_one("hair_diary", "profile", uri.toString(), id)
            }
            Toast.makeText(this, "url? :${it.toString()}", Toast.LENGTH_LONG).show()
        }

    }
    fun uploadPhoto(
        photoUri: Uri,
        index: Int,
        name: String,
        style: String,
        length: String,
        gender: String,
    ) {
        // 디자이너가 사진을 올립니다. 포트폴리오용 입니다.
        var db=fireDB(this)
        val pref=getSharedPreferences("session", 0)
        var edit=pref.edit()
        var id=pref.getString("id", "").toString()
        if(id==""){return;}
        var storageRef = FirebaseStorage.getInstance().reference.child("images")
        storageRef=storageRef.child(id).child(name)//Timestamp(java.util.Date()).toString()
        storageRef.putFile(photoUri).addOnSuccessListener {
            storageRef.downloadUrl.addOnSuccessListener { uri->
                //url:String="",id:String="",pcount:Int=-1,name:String="")
                db.insert_onephoto(uri.toString(), id, index, name, style, length, gender)
                edit.putInt("index", index + 1)
                edit.apply()
            }
            Toast.makeText(this, "url? :${it.toString()}", Toast.LENGTH_LONG).show()

        }
    }
    class AndroidBridge(context: Context) {
        val db=fireDB(context)
        val context=context
        @JavascriptInterface
        fun AlertMsg_logout(arg: String){
            println("logout : ${arg}")
            var pref=context.getSharedPreferences("session", 0)
            val edit=pref.edit()
            edit.clear()
            edit.apply()
            var intent= Intent(context, Home2::class.java)
            context.startActivity(intent)
        }
        @JavascriptInterface
        fun AlertMsg(arg: String) { // 웹뷰내의 페이지에서 호출하는 함수
            Handler().post(Runnable() {
                var splitarg = arg.split(" ")
                var name = splitarg[0]
                var id = splitarg[1]
                println("id : ${id} name : ${name}")
                check_isprimaryid(id, name, context)
            })
        }

         fun check_isprimaryid(id: String, name: String, context: Context){
             // 회원가입시 아이디 중복 체크인데 임시로 만들었습니다.
            println("read")
            val pref=context.getSharedPreferences("session", 0)
            val edit=pref.edit()
            db.firestore?.collection("hair_diary").whereEqualTo("id", id).get()
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        if(it.result!!.documents.size==0){
                            db.createData(id.toString(), name.toString())
                        }
                        edit.putString("id", id)
                        edit.apply()
                        var firestore = FirebaseFirestore.getInstance()
                        firestore?.collection("hair_diary").whereEqualTo("id", id.toString()).get()
                            .addOnCompleteListener {
                                if(it.isSuccessful){
                                    for(dc in it.result!!.documents){
                                        println("\nget test!!! : ${dc.getString("ttttt")}")
                                        //  println("${len+1} : ${dc.toString()}")
                                        edit.putString("perm",dc.toObject(designer::class.java)?.perm.toString() )
                                        edit.putString("name", dc.toObject(designer::class.java)?.name.toString() )
                                        edit.putInt( "year",dc.toObject(designer::class.java)?.year!!.toInt()  )
                                        edit.putInt( "index",  dc.toObject(designer::class.java)?.index!!.toInt())
                                        edit.putString(  "memo", dc.toObject(designer::class.java)?.memo.toString())
                                        edit.putString( "phone", dc.toObject(designer::class.java)?.phone.toString() )
                                        edit.putInt(  "age",   dc.toObject(designer::class.java)?.age!!.toInt()     )
                                        edit.putString( "profile",  dc.toObject(designer::class.java)?.profile.toString()  )
                                        edit.apply()
                                    }
                                    var intent= Intent(context, Home2::class.java)
                                    context.startActivity(intent)
                                }else{
                                    println("fail")
                                }
                            }
                    }else{
                        println("fail")
                    }
                }
            println("read end")
        }

    }


}
