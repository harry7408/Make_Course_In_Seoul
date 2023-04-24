package com.example.whattodo.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.whattodo.R

class UserInputActivity : AppCompatActivity() {
    private lateinit var nextbutton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_input)
        nextbutton=findViewById(R.id.okBtn)
        nextbutton.setOnClickListener {
            val intent= Intent(this,MainInfoActivity::class.java)
            startActivity(intent)
        }
    }
}