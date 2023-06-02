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
    var fatigue=0.0
    var exotic=0.0
    var active=0.0
    var extraFatigue = 0.0
    var extraExotic = 0.0
    var extraActive = 0.0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)

        val userData = intent.getParcelableExtra<User>("userInfo1")

        if (savedInstanceState != null) {
           fatigue = savedInstanceState.getDouble("savedFatigue")
            exotic = savedInstanceState.getDouble("savedExotic")
            active = savedInstanceState.getDouble("savedActive")
        } else {
            fatigue= intent.getDoubleExtra("fatigue1",0.0)
            exotic=intent.getDoubleExtra("exotic1", 0.0)
            active= intent.getDoubleExtra("active1", 0.0)
        }
        Log.e(TAG,"$fatigue, $exotic, $active")
        binding.firstQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraFatigue = when (checkedId) {
                R.id.first1 -> 0.0
                R.id.first2 -> 20.0
                R.id.first3 -> 40.0
                R.id.first4 -> 60.0
                R.id.first5 -> 80.0
                else -> 50.0
            }

            Log.e(TAG, "$extraFatigue")
        }
        binding.secondQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraExotic = when (checkedId) {
                R.id.second1 -> 0.0
                R.id.second2 -> 20.0
                R.id.second3 -> 40.0
                R.id.second4 -> 60.0
                R.id.second5 -> 80.0
                else -> 50.0
            }

            Log.e(TAG, "$extraExotic")
        }
        binding.thirdQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraActive = when (checkedId) {
                R.id.third1 -> 0.0
                R.id.third2 -> 20.0
                R.id.third3 -> 40.0
                R.id.third4 -> 60.0
                R.id.third5 -> 80.0
                else -> 50.0
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
            intent.putExtra("userInfo2", userData)
            startActivity(intent)
        }
    }
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("savedFatigue", fatigue+extraFatigue)
        outState.putDouble("savedExotic", exotic+extraExotic)
        outState.putDouble("savedActive", active+extraActive)
    }
}


