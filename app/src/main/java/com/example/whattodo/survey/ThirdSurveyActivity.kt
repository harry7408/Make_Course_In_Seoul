package com.example.whattodo.survey

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.CheckBox
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
    var fatigue = 0
    var exotic = 0
    var active = 0
    private var extraFatigue: Int = 0
    private var extraExotic: Int = 0
    private var extraActive: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)
        val memberData = intent.getParcelableExtra<Member>("memberInfo2")
        limitCheckBoxCount()

        fatigue = intent.getIntExtra("fatigue2", 0)
        exotic = intent.getIntExtra("exotic2", 0)
        active = intent.getIntExtra("active2", 0)

        Log.e(TAG,"$fatigue, $exotic, $active")

        binding.finishTextView.setOnClickListener {
            checkCheckBox()
            val currentMember = Member(
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

            val joinCall = RetrofitAPI.joinService.Join(currentUser)
            joinCall.enqueue(object : retrofit2.Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        Thread {
                            UserDatabase.getInstance(this@ThirdSurveyActivity)?.memberDao()
                                ?.updateMember(currentMember)
                        }.start()
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

    /* 체크 갯수 제한 */
    private fun limitCheckBoxCount() {
        val maxCheck = 2
        var checkedCount = 0
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
            extraFatigue += 65
            extraExotic += 65
            extraActive += 60
        }
        if (binding.check2.isChecked) {
            extraFatigue += 65
            extraExotic += 20
            extraActive += 45
        }
        if (binding.check3.isChecked) {
            extraFatigue += 45
            extraExotic += 35
            extraActive += 20
        }
        if (binding.check4.isChecked) {
            extraFatigue += 15
            extraExotic += 35
            extraActive += 5
        }
        if (binding.check5.isChecked) {
            extraFatigue += 5
            extraExotic += 45
            extraActive += 5
        }
        if (binding.check6.isChecked) {
            extraFatigue += 40
            extraExotic += 40
            extraActive += 15
        }
        if (binding.check7.isChecked) {
            extraFatigue += 15
            extraExotic += 20
            extraActive += 15
        }
        if (binding.check8.isChecked) {
            extraFatigue += 55
            extraExotic += 60
            extraActive += 5
        }
        if (binding.check9.isChecked) {
            extraFatigue += 40
            extraExotic += 35
            extraActive += 20
        }
        if (binding.check10.isChecked) {
            extraFatigue += 45
            extraExotic += 60
            extraActive += 5
        }
        if (binding.check11.isChecked) {
            extraFatigue += 15
            extraExotic += 0
            extraActive += 20
        }
        if (binding.check12.isChecked) {
            extraFatigue += 40
            extraExotic += 40
            extraActive += 15
        }
        if (binding.check13.isChecked) {
            extraFatigue += 40
            extraExotic += 20
            extraActive += 25
        }
        if (binding.check14.isChecked) {
            extraFatigue += 45
            extraExotic += 60
            extraActive += 5
        }
        if (binding.check15.isChecked) {
            extraFatigue += 55
            extraExotic += 60
            extraActive += 5
        }
        if (binding.check16.isChecked) {
            extraFatigue += 40
            extraExotic += 0
            extraActive += 25
        }
        if (binding.check17.isChecked) {
            extraFatigue += 20
            extraExotic += 40
            extraActive += 20
        }
        if (binding.check18.isChecked) {
            extraFatigue += 55
            extraExotic += 25
            extraActive += 20
        }
        if (binding.check19.isChecked) {
            extraFatigue += 35
            extraExotic += 40
            extraActive += 5
        }
        if (binding.check20.isChecked) {
            extraFatigue += 45
            extraExotic += 0
            extraActive += 45
        }
    }
}


