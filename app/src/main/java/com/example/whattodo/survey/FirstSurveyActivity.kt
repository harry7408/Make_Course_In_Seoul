package com.example.whattodo.survey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityFirstSurveyBinding

class FirstSurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstSurveyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFirstSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)

        val fatigue = when {
            binding.first1.isChecked -> 0
            binding.first2.isChecked -> 20
            binding.first3.isChecked -> 40
            binding.first4.isChecked -> 60
            else -> 80
        }
        val exotic = when {
            binding.second1.isChecked -> 0
            binding.second2.isChecked -> 20
            binding.second3.isChecked -> 40
            binding.second4.isChecked -> 60
            else -> 80
        }
        val active = when {
            binding.third1.isChecked -> 0
            binding.third2.isChecked -> 20
            binding.third3.isChecked -> 40
            binding.third4.isChecked -> 60
            else -> 80
        }
        val scoreList = arrayOf<Int>(fatigue, exotic, active)
        binding.layer.setOnClickListener {
            val intent = Intent(this, SecondSurveyActivity::class.java)
            intent.putExtra("scores", scoreList)
            startActivity(intent)

        }
    }
}