package com.example.whattodo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.whattodo.FindingIdPass.FindIdPassActivity
import com.example.whattodo.databinding.ActivityMainBinding
import com.example.whattodo.datas.User
import com.example.whattodo.FirstFeature.MainInfoActivity
import com.example.whattodo.db.UserDatabase
import com.example.whattodo.entity.Member
import com.example.whattodo.network.RetrofitAPI
import com.example.whattodo.survey.FirstSurveyActivity
import com.shashank.sony.fancytoastlib.FancyToast
import retrofit2.Call
import retrofit2.Response


/* 로그인 페이지 */
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var member: Member

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE)
        val userPass = sharedPreferences.getString(PASS, null)


        if (!userPass.equals(null)) {
            val intent = Intent(this, MainInfoActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }


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
            val id = binding.idArea.text.toString()
            val pass = binding.passArea.text.toString()
            val userdata = User(
                id, pass, null,
                null, null, null,
                -1, -1, -1
            )

            val loginCall = RetrofitAPI.loginService.login(userdata)
            loginCall.enqueue(object : retrofit2.Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        /* 여기부분 고민해보기 db 써야할 수도 */
                        with(getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit()) {
                            putString(ID, data?.memberId)
                            putString(PASS, data?.password)
                            putString(EMAIL, data?.email)
                            putString(NAME, data?.memberName)
                            putString(BDAY, data?.birthday)
                            putString(GENDER, data?.gender)
                            putInt(FATIGUE, data?.fatigability!!)
                            putInt(EXOTIC,data?.specification!!)
                            putInt(ACTIVITY,data?.active!!)
                        }.apply()
                        member = Member(
                            data?.memberId.toString(),
                            data?.password,
                            data?.email,
                            data?.memberName,
                            data?.birthday,
                            data?.gender,
                            data?.fatigability,
                            data?.specification,
                            data?.active
                        )
                        Thread {
                            UserDatabase.getInstance(this@MainActivity)?.memberDao()?.insert(member)
                        }.start()
                        intent = Intent(applicationContext, MainInfoActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "Failure")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
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

    private fun checkCurrentUser(id: String): Member {
        return UserDatabase.getInstance(this@MainActivity)?.memberDao()?.getMember(id)!!
    }
}


