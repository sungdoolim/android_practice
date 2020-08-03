package com.example.myhairdiary.test

import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myhairdiary.R
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import kotlinx.android.synthetic.main.activity_n_login.*
import org.jetbrains.anko.act
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


class NLogin : AppCompatActivity() {
   var mOAuthLoginModule = OAuthLogin.getInstance();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_login)
        mOAuthLoginModule.init(this.applicationContext, "cNRaBGpTcpzIQMv8aWuB", "It47BDxH0G", "staris_NL");


        button_naverlogin.setOAuthLoginHandler(mOAuthLoginHandler);
        mOAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler);

    }
     var mOAuthLoginHandler: OAuthLoginHandler =
        object : OAuthLoginHandler() {
            override fun run(success: Boolean) {
                if (success) {
                   var password = mOAuthLoginModule.getAccessToken(act)
                    //getNaverInfo(accessToken);
                    val task = ProfileTask()
                    // 이 클래스가 유저정보를 가져오는 업무를 담당합니다.
                    task.execute(password)
                 //   val refreshToken = mOAuthLoginModule.getRefreshToken(act)
                //    val expiresAt = mOAuthLoginModule.getExpiresAt(act)
                  //  val tokenType = mOAuthLoginModule.getTokenType(act)
                } else {
                    val errorCode = mOAuthLoginModule.getLastErrorCode(act).code
                    val errorDesc = mOAuthLoginModule.getLastErrorDesc(act)
                    Toast.makeText(
                        act, "errorCode:" + errorCode
                                + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    inner class ProfileTask : AsyncTask<String, Void, String>() {
        var result:String=""
        override fun doInBackground(vararg params: String?): String {
            val token: String = strings.get(0) // 네이버 로그인 접근 토큰;

            val header = "Bearer $token" // Bearer 다음에 공백 추가

            try {
                val apiURL = "https://openapi.naver.com/v1/nid/me"
                val url = URL(apiURL)
                val con: HttpURLConnection = url.openConnection() as HttpURLConnection
                con.setRequestMethod("GET")
                con.setRequestProperty("Authorization", header)
                val responseCode: Int = con.getResponseCode()
                val br: BufferedReader
                if (responseCode == 200) { // 정상 호출
                    br = BufferedReader(InputStreamReader(con.getInputStream()))
                    println("정상")
                } else {  // 에러 발생
                    br = BufferedReader(InputStreamReader(con.getErrorStream()))
                    println("에러")
                }
                var inputLine: String?=""
                val response = StringBuffer()
                while (br.readLine().also({ inputLine = it }) != null) {
                    response.append(inputLine)
                }
                 result = response.toString()
                br.close()
                println(response.toString())
            } catch (e: Exception) {
                println(e)
            }
            //result 값은 JSONObject 형태로 넘어옵니다.
            //result 값은 JSONObject 형태로 넘어옵니다.

            return result


        }
        override fun onPostExecute(s: String?) {
            super.onPostExecute(s)
            try {
                //넘어온 result 값을 JSONObject 로 변환해주고, 값을 가져오면 되는데요.
                // result 를 Log에 찍어보면 어떻게 가져와야할 지 감이 오실거에요.
                val `object` = JSONObject(result)
                Log.d("CommonUtil.TAG", "결과 : $result")
                if (`object`.getString("resultcode") == "00") {
                    val jsonObject = JSONObject(`object`.getString("response"))
                    var email = jsonObject.getString("id")
                    Log.d("jsonObject", jsonObject.toString())

                    /*SharedPreferences.Editor editor = activity.userData.edit();
                editor.putString("email", jsonObject.getString("email"));
                editor.putString("name", jsonObject.getString("name"));
                editor.putString("nickname", jsonObject.getString("nickname"));
                editor.putString("profile", jsonObject.getString("profile_image"));
                editor.apply();
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);*/
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }

    }
}


