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
import java.util.UUID


/* 로그인 페이지 */
private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
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
                null,
                id, pass, null, null,
                null, null, null,
                -1.0, -1.0, -1.0
            )
//            Log.e(TAG,"$userdata")

            val loginCall = RetrofitAPI.loginService.login(userdata)
            loginCall.enqueue(object : retrofit2.Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val responseData = response.body()
                        Log.e(TAG,"${response.body()}")
//                         여기부분 고민해보기 db 써야할 수도
                        with(getSharedPreferences(USER_INFO, Context.MODE_PRIVATE).edit()) {
                            putString(UID,responseData?.userCode)
                            putString(ID, responseData?.memberId)
                            putString(PASS, responseData?.password)
                            putString(EMAIL, responseData?.email)
                            putString(NAME, responseData?.memberName)
                            putString(BDAY, responseData?.birthday)
                            putString(GENDER, responseData?.gender)
                            putFloat(FATIGUE, responseData?.fatigability!!.toFloat())
                            putFloat(EXOTIC, responseData.specification!!.toFloat())
                            putFloat(ACTIVITY, responseData.active!!.toFloat())
                        }.apply()


                     val intent = Intent(applicationContext, MainInfoActivity::class.java)
                        startActivity(intent)
                    } else {
                        Log.e(TAG, "Null return")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.e(TAG,"ERROR OCCUR")
                    Log.e(TAG,"$call")
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
            /*val intent =Intent(applicationContext,MainInfoActivity::class.java)
            startActivity(intent)*/
        }
    }

    private fun checkCurrentUser(id: String): Member {
        return UserDatabase.getInstance(this@MainActivity)?.memberDao()?.getMember(id)!!
    }
}


