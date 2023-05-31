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
    var fatigue=0
    var exotic=0
    var active=0
    var extraFatigue = 0
    var extraExotic = 0
    var extraActive = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)

        val memberData = intent.getParcelableExtra<Member>("memberInfo1")

        if (savedInstanceState != null) {
           fatigue = savedInstanceState.getInt("savedFatigue")
            exotic = savedInstanceState.getInt("savedExotic")
            active = savedInstanceState.getInt("savedActive")
        } else {
            fatigue= intent.getIntExtra("fatigue1",0)
            exotic=intent.getIntExtra("exotic1", 0)
            active= intent.getIntExtra("active1", 0)
        }
        Log.e(TAG,"$fatigue, $exotic, $active")
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
            val intent = Intent(this, ThirdSurveyActivity::class.java)
            intent.apply {
                putExtra("fatigue2", fatigue+extraFatigue)
                putExtra("exotic2", exotic+extraExotic)
                putExtra("active2", active+extraActive)
            }
            intent.putExtra("memberInfo2", memberData)
            startActivity(intent)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("savedFatigue", fatigue+extraFatigue)
        outState.putInt("savedExotic", exotic+extraExotic)
        outState.putInt("savedActive", active+extraActive)
    }
}


