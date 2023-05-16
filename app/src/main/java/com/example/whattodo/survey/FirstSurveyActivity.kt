package com.example.whattodo.survey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityFirstSurveyBinding
import com.example.whattodo.entity.Member

private const val TAG="FirstSurveyActivity"
class FirstSurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstSurveyBinding
    private var fatigue:Int=0
    private var exotic:Int=0
    private var active:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)

        var memberData=intent.getParcelableExtra<Member>("memberInfo")
        /* 넘어오는 데이터 확인용 로그*/

        binding.firstQuestion.setOnCheckedChangeListener { _, checkedId ->
           fatigue= when (checkedId) {
                R.id.first1->0
                R.id.first2 -> 20
                R.id.first3 -> 40
                R.id.first4 -> 60
                R.id.first5 ->80
                else-> 50
            }
            Log.e(TAG,"$fatigue")
        }
        binding.secondQuestion.setOnCheckedChangeListener { _, checkedId ->
            exotic= when (checkedId) {
                R.id.second1->0
                R.id.second2 -> 20
                R.id.second3 -> 40
                R.id.second4 -> 60
                R.id.second5 ->80
                else-> 50
            }
            Log.e(TAG,"$exotic")
        }
        binding.thirdQuestion.setOnCheckedChangeListener { _, checkedId ->
            active= when (checkedId) {
                R.id.third1->0
                R.id.third2 -> 20
                R.id.third3 -> 40
                R.id.third4 -> 60
                R.id.third5 ->80
                else-> 50
            }
            Log.e(TAG,"$active")
        }

        binding.layer.setOnClickListener {
            val intent = Intent(this, SecondSurveyActivity::class.java)
            intent.apply {
                putExtra("fatigue",fatigue)
                putExtra("exotic",exotic)
                putExtra("active",active)
            }
            intent.putExtra("memberInfo1", memberData)
            startActivity(intent)
        }
    }
}