package com.example.whattodo.ThirdFeature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityChangeUserInfoBinding
import com.example.whattodo.survey.FirstSurveyActivity

private const val TAG="ChangeUserInfoActivity"

class ChangeUserInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangeUserInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityChangeUserInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "회원정보수정"

        val currentUserName=intent.getStringExtra("username")
        val currentUserEmail=intent.getStringExtra("userEmail")
        val currentUserGender=intent.getStringExtra("userGender")

        if (currentUserGender.equals("남성")) {
            binding.userImageView.setImageResource(R.drawable.male)
        } else {
            binding.userImageView.setImageResource(R.drawable.female)
        }

        binding.userNameTextView.text=currentUserName
        binding.userEmailTextView.text=currentUserEmail


        binding.passWordChangeCardView.setOnClickListener {
            val intent= Intent(this,PasswordChangeActivity::class.java)
            startActivity(intent)
        }

        binding.surveyChangeCardView.setOnClickListener {
            val intent=Intent(this,FirstSurveyActivity::class.java)
            intent.putExtra("reSurvey",true)
            startActivity(intent)
        }
    }

    /* 툴바에서 <- 눌렀을때 이벤트 처리 */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
}