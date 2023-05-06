package com.example.whattodo.survey

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivitySecondSurveyBinding

class SecondSurveyActivity : AppCompatActivity() {

    private lateinit var binding:ActivitySecondSurveyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySecondSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result=intent.getIntArrayExtra("scores")



    }
}