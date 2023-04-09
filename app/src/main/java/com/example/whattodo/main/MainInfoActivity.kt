package com.example.whattodo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whattodo.R

//      사용자 취향 입력받았을때 넘어가는 처음 페이지 -> 네비게이션 + 프래그먼트로 이동 화면 이동 구현 해야할 듯
class MainInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_info)

    }
}