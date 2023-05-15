package com.example.whattodo.ThirdFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityShowHistoryBinding

class ShowHistoryActivity : AppCompatActivity() {

    private lateinit var binding:ActivityShowHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityShowHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}