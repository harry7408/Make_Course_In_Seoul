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
    private lateinit var latestMember:Member

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE)
        val userId = sharedPreferences.getString(ID, null)
        val userPass = sharedPreferences.getString(PASS, null)

        val userFatigue = sharedPreferences.getInt(FATIGUE, -1)
        val userExotic = sharedPreferences.getInt(EXOTIC, -1)
        val userActive = sharedPreferences.getInt(ACTIVITY, -1)




        /* 이 부분 다시 정리하기 */
        /* 자동 로그인 (사용자 아이디, 피로도 특이도, 활동성 무도 있다면 바로 메인 페이지로) */
        if (userId != null && userPass != null && userFatigue != -1 && userActive != -1 && userExotic != -1) {
            val intent = Intent(this, MainInfoActivity::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            /*피로도 활동성 특이도가 없다면 -1로 설정해놓고(값이 0인 경우가 있을 수 있기때문에) 설문 받는 페이지로 */
        } else if (userId != null && userPass != null && userFatigue == -1 && userActive == -1 && userExotic == -1) {
            val intent = Intent(this, FirstSurveyActivity::class.java)
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
//            val intent = Intent(this, FirstSurveyActivity::class.java)
//            startActivity(intent)
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
                        }.apply()
                        member= Member(data?.memberId.toString(),data?.password,data?.email,
                            data?.memberName,data?.birthday,data?.gender,userFatigue,userExotic,userActive)
                        Thread {
                           UserDatabase.getInstance(this@MainActivity)?.memberDao()?.insert(member)
                       }.start()
                            intent=Intent(applicationContext,MainInfoActivity::class.java)
                            startActivity(intent)
                    } else {
                        Log.e(TAG,"Failure")
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

    private fun checkCurrentUser(id:String) : Member {
       return UserDatabase.getInstance(this@MainActivity)?.memberDao()?.getMember(id)!!
    }
}


