package com.example.whattodo.survey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityFirstSurveyBinding
import com.example.whattodo.datas.User
import com.example.whattodo.entity.Member

private const val TAG = "FirstSurveyActivity"

class FirstSurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstSurveyBinding
    private var fatigue: Double = 0.0
    private var exotic: Double = 0.0
    private var active: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)

        var userData = intent.getParcelableExtra<User>("joinUserInfo")

        /* 다음 설문에서 뒤로가기 했을 때 이전 설문 값 저장 */
        if (savedInstanceState != null) {
            fatigue = savedInstanceState.getDouble("savedFatigue")
            exotic = savedInstanceState.getDouble("savedExotic")
            active = savedInstanceState.getDouble("savedActive")
        }

        binding.firstQuestion.setOnCheckedChangeListener { _, checkedId ->
            fatigue = when (checkedId) {
                R.id.first1 -> 80.0
                R.id.first2 -> 60.0
                R.id.first3 -> 40.0
                R.id.first4 -> 20.0
                R.id.first5 -> 0.0
                else -> 50.0
            }
            Log.e(TAG, "$fatigue")
        }
        binding.secondQuestion.setOnCheckedChangeListener { _, checkedId ->
            exotic = when (checkedId) {
                R.id.second1 -> 0.0
                R.id.second2 -> 20.0
                R.id.second3 -> 40.0
                R.id.second4 -> 60.0
                R.id.second5 -> 80.0
                else -> 50.0
            }
            Log.e(TAG, "$exotic")
        }
        binding.thirdQuestion.setOnCheckedChangeListener { _, checkedId ->
            active = when (checkedId) {
                R.id.third1 -> 0.0
                R.id.third2 -> 20.0
                R.id.third3 -> 40.0
                R.id.third4 -> 60.0
                R.id.third5 -> 80.0
                else -> 50.0
            }
            Log.e(TAG, "$active")
        }


        binding.layer.setOnClickListener {
            val intent = Intent(this, SecondSurveyActivity::class.java)
            intent.apply {
                putExtra("fatigue1", fatigue)
                putExtra("exotic1", exotic)
                putExtra("active1", active)
//                flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            intent.putExtra("userInfo1", userData)
            startActivity(intent)
        }
    }

    /* 이전 값 저장 */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("savedFatigue", fatigue)
        outState.putDouble("savedExotic", exotic)
        outState.putDouble("savedActive", active)
    }
}