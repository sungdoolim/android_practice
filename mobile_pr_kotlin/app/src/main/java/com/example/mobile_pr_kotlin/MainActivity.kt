package com.example.mobile_pr_kotlin

import android.app.*
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.ContextMenu
import android.view.ContextMenu.ContextMenuInfo
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_pr_kotlin.adapter.recycleAdapter
import com.example.mobile_pr_kotlin.frag.frag1
import com.example.mobile_pr_kotlin.frag.frag2
import com.example.mobile_pr_kotlin.vo.VO
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_dialog.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var NOTIFICATION_CHANNEL_ID: String? = null
    private var progress: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progress = ProgressDialog(this) // 프로그레스 바 // 진행율
        NOTIFICATION_CHANNEL_ID = "my_channel_id_01"
        call.setOnClickListener(){
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("tel: 010-3152-1372"))
            startActivity(intent)
        }
        registerForContextMenu(floatevent) // 텍스트 뷰를 컨텍스트로 등록하는 이미 있는 메서드
        createNotificationChannel() // 푸시 알림
        rtb.onRatingBarChangeListener =
            OnRatingBarChangeListener { ratingBar, v, b ->
                Toast.makeText(this, "${v}", Toast.LENGTH_SHORT).show()
            }
        openfrag1.setOnClickListener(){
            val manager = supportFragmentManager
            val ft = manager.beginTransaction()
            ft.replace(R.id.framecontainer, frag1(), "one")
            ft.commitAllowingStateLoss()
        }
        openfrag2.setOnClickListener(){
            val manager = supportFragmentManager
            val ft = manager.beginTransaction()
            ft.replace(R.id.framecontainer, frag2(), "one")
            ft.commitAllowingStateLoss()


        }


        //recyclerview
        var vo=ArrayList<VO>()
        vo.add(VO())
        vo.add(VO())
        vo.add(VO())
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                applicationContext,
                DividerItemDecoration.VERTICAL
            )
        )// 경계선 추가!!!!
        recyclerView.layoutManager= LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter=recycleAdapter(this, vo)
    }




    //어떤 버튼 눌러서
//    Intent in = new Intent(MainActivity.this, SubActivity.class);
//    startActivityForResult(in, GET_STRING);

    // 그리고 서브에서
//    Intent intent = new Intent();
//    intent.putExtra("INPUT_TEXT", edit.getText().toString());
//    setResult(RESULT_OK, intent);
//    finish();
    override protected fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==1) {
            if (resultCode == RESULT_OK) {
             // 여기다 할꺼 쓰면 되는데
            }
        }
    }



    fun ischeck(v: View) { // 체크 박스
        when (v.id) {
            R.id.cb1 -> Toast.makeText(this, "cb버튼1 테스트!!!", Toast.LENGTH_SHORT).show()
            R.id.cb2 -> Toast.makeText(this, "cb버튼2 테스트!!!", Toast.LENGTH_SHORT).show()
        }
    }
    fun isradio(v: View) { // 라디오 버튼
        when (v.id) {
            R.id.rb1 -> Toast.makeText(baseContext, "rb버튼1 테스트!!!", Toast.LENGTH_SHORT).show()
            R.id.rb2 -> Toast.makeText(baseContext, "rb버튼2 테스트!!!", Toast.LENGTH_SHORT).show()
        }
    }

    fun togglevent(v: View) {
        val on = (v as ToggleButton).isChecked
        if (on) {
            Toast.makeText(baseContext, "토글 온", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(baseContext, "토글 오프", Toast.LENGTH_SHORT).show()
        }
    }


    //두개 메서드가 한 세트고 이거 하면 자동으로 오른쪽 상단에 메뉴 탭을 가지게 됨
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.bottomnavi_x, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bottom1 -> Toast.makeText(applicationContext, "1111", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(applicationContext, "defa", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onCreateContextMenu(menu: ContextMenu, v: View?, menuInfo: ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menu.setHeaderTitle("컨텍스트 메뉴")
        menu.add(0, 1, 0, "배경색: RED")
        menu.add(0, 2, 0, "배경색: GREEN")
        menu.add(0, 3, 0, "배경색: BLUE")
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            1 -> {
                floatevent.setBackgroundColor(Color.RED)
                true
            }
            2 -> {
                floatevent.setBackgroundColor(Color.GREEN)
                true
            }
            3 -> {
                floatevent.setBackgroundColor(Color.BLUE)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }


    // 팝업 버튼
    fun onpopup(v: View?) {
        val popup = PopupMenu(this, v)
        popup.menuInflater.inflate(R.menu.action_bar, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            Toast.makeText(applicationContext, "클릭된 팝업 메뉴: " + item.title, Toast.LENGTH_SHORT).show()
            true
        }
        popup.show()
    }


    // 시계 달력
    fun callendar(v: View) {
        if (v === btcal) {
            val c = Calendar.getInstance()
            val y = c[Calendar.YEAR]
            val m = c[Calendar.MONTH]
            val d = c[Calendar.DAY_OF_MONTH]
            val dpd = DatePickerDialog(
                this, { datePicker, year, month, day ->
                    btcal.text = month.toString() + "/" + (month + 1) + "/" + year
                }, y, m, d
            )
            dpd.show()
        }
        if (v === bttime) {
            val c = Calendar.getInstance()
            val mHour = c[Calendar.HOUR_OF_DAY]
            val mMinute = c[Calendar.MINUTE]
            val tpd = TimePickerDialog(
                this, { timePicker, hour, minute -> bttime.text = "$hour : $minute" },
                mHour,
                mMinute,
                false
            )
            tpd.show()
        }
    }


    fun dialog(v: View?) {
        val loginDialog = Dialog(this)
        loginDialog.setContentView(R.layout.custom_dialog)
        loginDialog.setTitle("로그인 화면")
        // 이어서 계속
        val login = loginDialog.findViewById<View>(R.id.login) as Button
        val cancel = loginDialog.findViewById<View>(R.id.cancel) as Button
        login.setOnClickListener {
            if (username.text.toString().trim { it <= ' ' }.length > 0
                && password.text.toString().trim { it <= ' ' }.length > 0
            ) {
                Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                loginDialog.dismiss()
            } else {
                Toast.makeText(this, "다시 입력하시오", Toast.LENGTH_SHORT).show()
            }
        }
        cancel.setOnClickListener { loginDialog.dismiss() }
        loginDialog.show()
    }


    //핸드폰 푸시알림!! 메인에서 createnotifica~~예를 부르고... 무슨 notification 아이디도 main에서 설정했엉
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID, "My Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = "Channel description"
            val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun sendNotification(view: View?) {
        val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID!!)
        // 알림이 클릭되면, 아래의 인텐트가 전송된다.
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/"))
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        notificationBuilder.setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("메일 알림")
            .setContentText("새로운 메일이 도착하였습니다.")
            .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify( /*notification id*/1, notificationBuilder.build())
    }


    //프로그레스 바
    fun progress(view: View?) {
        progress!!.setCancelable(true)
        progress!!.setTitle("네트워크에서 다운로드 중입니다. ")
       // progress!!.setMessage("네트워크에서 다운로드 중입니다. ")

        progress!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progress!!.progress = 0
        progress!!.max = 200

        progress!!.show()
        val t: Thread = object : Thread() {
            override fun run() {
                var time = 0
                while (time < 100) {
                    try {
                        sleep(200)
                        time += 5
                        progress!!.progress = time*2 // prgress 진행율
                    } catch (e: InterruptedException) {
// TODO Auto-generated catch block
                        e.printStackTrace()
                    }
                }
                if(progress!!.progress ==200){
                    println("ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss")
                    progress!!.dismiss()
                }
            }
        }
        t.start()
    }

}