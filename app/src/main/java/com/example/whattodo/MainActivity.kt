package com.example.whattodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var emailedt:EditText
    private lateinit var passedt:EditText
    private lateinit var findemailtxt:TextView
    private lateinit var findpasstxt:TextView
    private lateinit var jointxt:TextView
    private lateinit var loginbtn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailedt=findViewById<EditText>(R.id.emailArea)
        passedt=findViewById<EditText>(R.id.passArea)
        findemailtxt=findViewById<TextView>(R.id.findIdText)
        findpasstxt=findViewById<TextView>(R.id.findPassText)
        jointxt=findViewById<TextView>(R.id.joinText)
        loginbtn=findViewById<Button>(R.id.loginButton)




        jointxt.setOnClickListener {
            val intent=Intent(this,JoinActivity::class.java)
            startActivity(intent)
        }
        findemailtxt.setOnClickListener {
            val intent=Intent(this,FindingEpActivity::class.java)
            startActivity(intent)
        }

        findpasstxt.setOnClickListener {
            val intent=Intent(this,FindingEpActivity::class.java)
            startActivity(intent)
        }
    }
}