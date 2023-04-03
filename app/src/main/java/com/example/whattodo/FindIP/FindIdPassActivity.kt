package com.example.whattodo.FindIP

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.whattodo.R

class FindIdPassActivity : AppCompatActivity() {
    private lateinit var emailTxt:TextView
    private lateinit var passTxt:TextView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id_pass)

        emailTxt=findViewById(R.id.findId)
        passTxt=findViewById(R.id.findPass)

        val txt: String? =intent.getStringExtra("text")

        if (txt!!.equals(emailTxt.text.toString())) {
            emailTxt.setBackgroundResource(R.drawable.background_rectangle_color)
            val fragment=FindIdFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,fragment).addToBackStack(null).commit()
        }

           else if(txt!!.equals(passTxt.text.toString()))  {
               passTxt.setBackgroundResource(R.drawable.background_rectangle_color)
            val fragment=FindPassFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer,fragment).addToBackStack(null)
                .commit()
        }


        emailTxt.setOnClickListener {
            emailTxt.setBackgroundResource(R.drawable.background_rectangle_color)
            passTxt.setBackgroundResource(R.drawable.background_rectangle2)
            val fragment=FindIdFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment)
                .commit()
        }
        passTxt.setOnClickListener {
            emailTxt.setBackgroundResource(R.drawable.background_rectangle2)
            passTxt.setBackgroundResource(R.drawable.background_rectangle_color)
            val fragment=FindPassFragment()
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer,fragment)
                .commit()
        }



    }
}