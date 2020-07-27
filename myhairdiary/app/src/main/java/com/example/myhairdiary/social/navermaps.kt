package com.example.myhairdiary.social

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.example.myhairdiary.R
import com.example.myhairdiary.designers.designer
import com.google.firebase.firestore.FirebaseFirestore
import com.naver.maps.map.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.FusedLocationSource
import kotlinx.android.synthetic.main.activity_face_b.*
import kotlinx.android.synthetic.main.activity_navermaps.*

class navermaps : AppCompatActivity()  {//,OnMapReadyCallback
    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap
    val marker = Marker()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navermaps)

                wv.settings.javaScriptEnabled=true
        wv.webViewClient= WebViewClient()
        wv.webChromeClient= WebChromeClient()


        val id=intent.getStringExtra("did")?:"null"
        readQueryWhereEqulToData(id)

       // val url="https://m.place.naver.com/hairshop/34883169/location?subtab=location"
       // wv.loadUrl(url)


//https://m.place.naver.com/restaurant/34616224/location?subtab=location   - 김포 스벅

//        val coord = LatLng(37.5670135, 126.9783740)
//
//            print("위도: ${coord.latitude}, 경도: ${coord.longitude}")
//
//
//     locationSource =
//            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
//
//        val fm = supportFragmentManager
//        val mapFragment = fm.findFragmentById(R.id.Nmap) as MapFragment?
//            ?: MapFragment.newInstance().also {
//                fm.beginTransaction().add(R.id.Nmap, it).commit()
//            }
//

    }
    public fun readQueryWhereEqulToData(id:String){
        println("read")
        var firestore = FirebaseFirestore.getInstance()
        firestore?.collection("hair_diary").whereEqualTo("id",id).get()
            .addOnCompleteListener {
                if(it.isSuccessful){
                    for(dc in it.result!!.documents){
                        var userDTO =dc.toObject(designer::class.java)
                        println("success ${userDTO.toString()}")// 비동기식으로 되는건가봐 맨 마지막에 출력되네
                        wv.loadUrl(userDTO!!.naverurl)
                    }
                }else{
                    println("fail")
                }
            }
        println("read end")
    }

//    override fun onRequestPermissionsResult(requestCode: Int,
//                                            permissions: Array<String>,
//                                            grantResults: IntArray) {
//        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
//                grantResults)) {
//            if (!locationSource.isCompassEnabled) { // 권한 거부됨
//                naverMap.locationTrackingMode = LocationTrackingMode.None
//            }
//            return
//        }
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }
//    companion object {
//        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
//    }
//
//    override fun onMapReady(naverMap: NaverMap) {
//        this.naverMap = naverMap
//        naverMap.locationSource = locationSource
//
//
//        marker.position = LatLng(37.5670135, 126.9783740)
//        marker.map = naverMap
//    }
}
