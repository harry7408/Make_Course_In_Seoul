package com.example.whattodo.survey

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.example.whattodo.*
import com.example.whattodo.databinding.ActivitySecondSurveyBinding
import com.example.whattodo.FirstFeature.MainInfoActivity
import com.example.whattodo.datas.User
import com.example.whattodo.db.UserDatabase
import com.example.whattodo.entity.Member
import com.example.whattodo.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Response

private const val TAG = "SecondSurveyActivity"

class SecondSurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondSurveyBinding
    private lateinit var currentMember: Member
    var extraFatigue = 0
    var extraExotic = 0
    var extraActive = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)

        val fatigue = intent.getIntExtra("fatigue", 0)
        val exotic = intent.getIntExtra("exotic", 0)
        val active = intent.getIntExtra("active", 0)

        Log.d(TAG, "$fatigue, $exotic, $active")
        val memberData = intent.getParcelableExtra<Member>("memberInfo1")

        binding.firstQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraFatigue = when (checkedId) {
                R.id.first1 -> 0
                R.id.first2 -> 20
                R.id.first3 -> 40
                R.id.first4 -> 60
                R.id.first5 -> 80
                else -> 50
            }
            Log.e(TAG, "$extraFatigue")
        }
        binding.secondQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraExotic = when (checkedId) {
                R.id.second1 -> 0
                R.id.second2 -> 20
                R.id.second3 -> 40
                R.id.second4 -> 60
                R.id.second5 -> 80
                else -> 50
            }
            Log.e(TAG, "$extraExotic")
        }
        binding.thirdQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraActive = when (checkedId) {
                R.id.third1 -> 0
                R.id.third2 -> 20
                R.id.third3 -> 40
                R.id.third4 -> 60
                R.id.third5 -> 80
                else -> 50
            }
            Log.e(TAG, "$extraActive")
        }

        binding.layer.setOnClickListener {
            currentMember = Member(
                memberData?.memberId.toString(),
                memberData?.password,
                memberData?.email,
                memberData?.memberName,
                memberData?.birthday,
                memberData?.gender,
                (fatigue + extraFatigue),
                (exotic + extraExotic),
                (active + extraActive)
            )


            val currentUser = User(
                memberData?.memberId.toString(),
                memberData?.password,
                memberData?.email,
                memberData?.memberName,
                memberData?.birthday,
                memberData?.gender,
                (fatigue + extraFatigue),
                (exotic + extraExotic),
                (active + extraActive)
            )

            /* 서버와 통신 필요*/
            val joinCall = RetrofitAPI.joinService.Join(currentUser)
            joinCall.enqueue(object : retrofit2.Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Thread {
                            UserDatabase.getInstance(this@SecondSurveyActivity)?.memberDao()
                                ?.updateMember(currentMember)
                        }.start()
                        val builder = android.app.AlertDialog.Builder(this@SecondSurveyActivity)
                        builder.setMessage("회원가입 성공")
                        builder.setPositiveButton(
                            R.string.ok,
                            DialogInterface.OnClickListener { dialog, which ->
                                val intent =
                                    Intent(this@SecondSurveyActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                startActivity(intent)
                            })
                        builder.create()
                        builder.show()

                    } else {
                        Log.d(TAG, "not Successful")
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Log.d(TAG, "FAILURE")
                    t.printStackTrace()
                    call.cancel()
                }
            })
        }
    }
}


