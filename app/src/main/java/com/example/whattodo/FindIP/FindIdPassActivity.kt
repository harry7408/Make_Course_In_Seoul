package com.example.whattodo.FindIP

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.whattodo.R

class FindIdPassActivity : AppCompatActivity() {
    private lateinit var emailTxt:TextView
    private lateinit var passTxt:TextView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id_pass)

//        뷰 객체 얻기
        emailTxt=findViewById(R.id.findId)
        passTxt=findViewById(R.id.findPass)

//        이전에서 터치한 텍스트 전달 받기위한 변수
        val txt: String? =intent.getStringExtra("text")
  
//        구분해주기 위한 (어떤 프래그먼트 적용할 것인지)
        if (txt!!.equals(emailTxt.text.toString())) {
            emailTxt.setBackgroundResource(R.drawable.background_rectangle_color)
            val fragment=FindIdFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,fragment)
                .commit()
        }  else if(txt!!.equals(passTxt.text.toString()))  {
               passTxt.setBackgroundResource(R.drawable.background_rectangle_color)
            val fragment=FindPassFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,fragment)
                .commit()
        }

//        이메일찾기 비밀번호 찾기 안에서 서로 이동 가능하도록
        emailTxt.setOnClickListener {
            emailTxt.setBackgroundResource(R.drawable.background_rectangle_color)
            passTxt.setBackgroundResource(R.drawable.background_rectangle2)
            val fragment=FindIdFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,fragment)
                .commit()
        }
        passTxt.setOnClickListener {
            emailTxt.setBackgroundResource(R.drawable.background_rectangle2)
            passTxt.setBackgroundResource(R.drawable.background_rectangle_color)
            val fragment=FindPassFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,fragment)
                .commit()
        }



    }
}