package com.example.whattodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.room.Room
import com.example.whattodo.FindIP.FindIdPassActivity
import com.example.whattodo.databinding.ActivityMainBinding
import com.example.whattodo.db.UserDatabase
import com.example.whattodo.dto.UserDto
import com.example.whattodo.entity.Member
import com.example.whattodo.network.RetrofitAPI
import com.example.whattodo.survey.FirstSurveyActivity
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Response

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
//            val intent = Intent(this, UserInputActivity::class.java)
//            startActivity(intent)
            val id = binding.idArea.text.toString()
            val pass = binding.passArea.text.toString()
            val userdata = UserDto(
                id, pass, null,
                null, null, null,
                null, null, null
            )

            val loginCall = RetrofitAPI.loginService.login(userdata)
            loginCall.enqueue(object : retrofit2.Callback<UserDto> {
                override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        val member = Member(
                            data!!.memberId.toString(), data!!.password.toString(),
                            data!!.email.toString(), data!!.memberName.toString(),
                            data!!.birthday.toString(), data!!.gender.toString(), null,
                            null, null
                        )
                        val database: UserDatabase = Room.databaseBuilder(
                            this@MainActivity,
                            UserDatabase::class.java, "members"
                        ).allowMainThreadQueries().build()
                        database.memberDao().insert(member)
                        val intent = Intent(applicationContext, FirstSurveyActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.d(TAG, "WHY NOT")
                    }
                }

                override fun onFailure(call: Call<UserDto>, t: Throwable) {
                    if (id.isEmpty() && pass.isEmpty()) {
                        FancyToast.makeText(
                            applicationContext,
                            "아이디 및 비밀번호를 입력해주세요",
                            FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR,
                            true
                        ).show()
                    } else {
                        FancyToast.makeText(
                            applicationContext,
                            "아이디 혹은 비밀번호가 일치하지 않습니다",
                            FancyToast.LENGTH_SHORT,
                            FancyToast.ERROR,
                            true
                        ).show()
                    }
                }
            })
        }
    }
}