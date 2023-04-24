package com.example.whattodo

import android.content.Intent
import android.os.Build.VERSION_CODES.S
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.whattodo.FindIP.FindIdPassActivity
import com.example.whattodo.databinding.ActivityMainBinding
import com.example.whattodo.main.UserInputActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        클릭 이벤트 처리부분
        binding.joinText.setOnClickListener {
            val intent=Intent(this,JoinActivity::class.java)
            startActivity(intent)
        }
        binding.findIdText.setOnClickListener {
            val intent=Intent(this, FindIdPassActivity::class.java)
            intent.putExtra("text",binding.findIdText.text.toString())
            startActivity(intent)
        }

        binding.findPassText.setOnClickListener {
            val intent=Intent(this,FindIdPassActivity::class.java)
            intent.putExtra("text",binding.findPassText.text.toString())
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val email=binding.emailArea.text.toString()
            val pass=binding.passArea.text.toString()


            val intent=Intent(this,UserInputActivity::class.java)
            startActivity(intent)

//            서버와 통신 필요
        }
    }
}