package com.example.myhairdiary_c.frag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myhairdiary_c.R

class home: Fragment(){
    // 화면 출력은 확인했으나 버튼이나 값들을 조정하기에 어려움이 있어 사용하지 않습니다.
    // 현재 쓰이지 않습니다. 지워도 무방합니다.

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var param= arguments?.getString("param")
        val fragment: Fragment = home() // Fragment 생성

        val bundle=Bundle()

        bundle.putString("param2", "dfsef") // Key, Value

        fragment.arguments = bundle

        return inflater.inflate(R.layout.home,container,false)

    }
}