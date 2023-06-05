package com.example.whattodo.survey

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.children
import com.example.whattodo.MainActivity
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityThirdSurveyBinding
import com.example.whattodo.datas.User
import com.example.whattodo.db.UserDatabase
import com.example.whattodo.entity.Member
import com.example.whattodo.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Response


private const val TAG = "ThirdSurveyActivity"

class ThirdSurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThirdSurveyBinding
    var fatigue = 0.0
    var exotic = 0.0
    var active = 0.0
    private var extraFatigue: Double = 0.0
    private var extraExotic: Double = 0.0
    private var extraActive: Double = 0.0
    var checkedCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)
        val memberData = intent.getParcelableExtra<User>("userInfo2")
        limitCheckBoxCount()

        fatigue = intent.getDoubleExtra("fatigue2", 0.0)
        exotic = intent.getDoubleExtra("exotic2", 0.0)
        active = intent.getDoubleExtra("active2", 0.0)

        Log.e(TAG, "$fatigue, $exotic, $active")

        binding.finishTextView.setOnClickListener {
            if (checkedCount != 3) {
                Toast.makeText(this, "3개를 체크해주세요", Toast.LENGTH_SHORT).show()
            } else {
                checkCheckBox()
                val currentUser = User(
                    null,
                    memberData?.memberId,
                    memberData?.password,
                    memberData?.memberName,
                    null,
                    memberData?.email,
                    memberData?.birthday,
                    memberData?.gender,
                    (fatigue + extraFatigue).div(3),
                    (exotic + extraExotic).div(3),
                    (active + extraActive).div(3)
                )

                val joinCall = RetrofitAPI.joinService.Join(currentUser)
                joinCall.enqueue(object : retrofit2.Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful) {
                            val builder = android.app.AlertDialog.Builder(this@ThirdSurveyActivity)
                            builder.setMessage("회원가입 성공")
                            builder.setPositiveButton(
                                R.string.ok,
                                DialogInterface.OnClickListener { dialog, which ->
                                    val intent =
                                        Intent(this@ThirdSurveyActivity, MainActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    startActivity(intent)
                                })
                            builder.create()
                            builder.show()

                        } else {
                            Log.d(TAG, "null returned")
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

    /* 체크 갯수 제한 */
    private fun limitCheckBoxCount() {
        val maxCheck = 3
        binding.listGridLayout.children.forEachIndexed { index, view ->
            if (view is CheckBox) {
                view.setOnCheckedChangeListener { _, isChecked ->
                    if (isChecked) {
                        checkedCount++
                        if (checkedCount > maxCheck) {
                            view.isChecked = false
                            checkedCount--
                        }
                    } else {
                        checkedCount--
                    }
                }
            }
        }
    }

    private fun checkCheckBox() {
        if (binding.check1.isChecked) {
            extraFatigue += 65.0
            extraExotic += 65.0
            extraActive += 60.0
        }
        if (binding.check2.isChecked) {
            extraFatigue += 65.0
            extraExotic += 20.0
            extraActive += 45.0
        }
        if (binding.check3.isChecked) {
            extraFatigue += 45.0
            extraExotic += 35.0
            extraActive += 20.0
        }
        if (binding.check4.isChecked) {
            extraFatigue += 15.0
            extraExotic += 35.0
            extraActive += 5.0
        }
        if (binding.check5.isChecked) {
            extraFatigue += 5.0
            extraExotic += 45.0
            extraActive += 5.0
        }
        if (binding.check6.isChecked) {
            extraFatigue += 40.0
            extraExotic += 40.0
            extraActive += 15.0
        }
        if (binding.check7.isChecked) {
            extraFatigue += 15.0
            extraExotic += 20.0
            extraActive += 15.0
        }
        if (binding.check8.isChecked) {
            extraFatigue += 55.0
            extraExotic += 60.0
            extraActive += 5.0
        }
        if (binding.check9.isChecked) {
            extraFatigue += 40.0
            extraExotic += 35.0
            extraActive += 20.0
        }
        if (binding.check10.isChecked) {
            extraFatigue += 45.0
            extraExotic += 60.0
            extraActive += 5.0
        }
        if (binding.check11.isChecked) {
            extraFatigue += 15.0
            extraExotic += 0.0
            extraActive += 20.0
        }
        if (binding.check12.isChecked) {
            extraFatigue += 40.0
            extraExotic += 40.0
            extraActive += 15.0
        }
        if (binding.check13.isChecked) {
            extraFatigue += 40.0
            extraExotic += 20.0
            extraActive += 25.0
        }
        if (binding.check14.isChecked) {
            extraFatigue += 45.0
            extraExotic += 60.0
            extraActive += 5.0
        }
        if (binding.check15.isChecked) {
            extraFatigue += 55.0
            extraExotic += 60.0
            extraActive += 5.0
        }
        if (binding.check16.isChecked) {
            extraFatigue += 40.0
            extraExotic += 0.0
            extraActive += 25.0
        }
        if (binding.check17.isChecked) {
            extraFatigue += 20.0
            extraExotic += 40.0
            extraActive += 20.0
        }
        if (binding.check18.isChecked) {
            extraFatigue += 55.0
            extraExotic += 25.0
            extraActive += 20.0
        }
        if (binding.check19.isChecked) {
            extraFatigue += 35.0
            extraExotic += 40.0
            extraActive += 5.0
        }
        if (binding.check20.isChecked) {
            extraFatigue += 45.0
            extraExotic += 0.0
            extraActive += 45.0
        }
        if (binding.check21.isChecked) {
            extraFatigue += 40
            extraExotic += 20
            extraActive += 25
        }
    }
}


