package com.example.whattodo

import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Build.VERSION_CODES.S
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.whattodo.FindIP.FindIdPassActivity
import com.example.whattodo.databinding.ActivityMainBinding
import com.example.whattodo.dto.JoinData
import com.example.whattodo.main.UserInputActivity
import com.example.whattodo.network.RetrofitAPI
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Response
import java.security.MessageDigest

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


//        클릭 이벤트 처리부분
        binding.joinText.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }
        binding.findIdText.setOnClickListener {
            val intent = Intent(this, FindIdPassActivity::class.java)
            intent.putExtra("text", binding.findIdText.text.toString())
            startActivity(intent)
        }

        binding.findPassText.setOnClickListener {
            val intent = Intent(this, FindIdPassActivity::class.java)
            intent.putExtra("text", binding.findPassText.text.toString())
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, UserInputActivity::class.java)
            startActivity(intent)
//            val id = binding.idArea.text.toString()
//            val pass = binding.passArea.text.toString()
//            val userdata = JoinData(
//                id, pass, null,
//                null, null, null
//            )
//            val intent = Intent(this, UserInputActivity::class.java)
//
//            val loginCall = RetrofitAPI.loginService.login(userdata)
//            loginCall.enqueue(object : retrofit2.Callback<JoinData> {
//                override fun onResponse(call: Call<JoinData>, response: Response<JoinData>) {
//                    if (response.isSuccessful) {
//                        startActivity(intent)
//                    } else {
//                        Log.d(TAG, "WHY NOT")
//                    }
//                }
//
//                override fun onFailure(call: Call<JoinData>, t: Throwable) {
//                    if (id.isEmpty() && pass.isEmpty()) {
//                        FancyToast.makeText(
//                            applicationContext,
//                            "아이디 및 비밀번호를 입력해주세요",
//                            FancyToast.LENGTH_SHORT,
//                            FancyToast.ERROR,
//                            true
//                        ).show()
//                    } else {
//                        FancyToast.makeText(
//                            applicationContext,
//                            "아이디 혹은 비밀번호가 일치하지 않습니다",
//                            FancyToast.LENGTH_SHORT,
//                            FancyToast.ERROR,
//                            true
//                        ).show()
//                    }
//                }
//            })
        }
    }
}