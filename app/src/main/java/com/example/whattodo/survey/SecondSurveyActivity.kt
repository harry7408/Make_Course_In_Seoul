package com.example.whattodo.survey

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whattodo.*
import com.example.whattodo.databinding.ActivitySecondSurveyBinding
import com.example.whattodo.FirstFeature.MainInfoActivity

private const val TAG = "SecondSurveyActivity"

class SecondSurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondSurveyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)

        var fatigue: Int = intent.getIntExtra("fatigue", 0)
        var exotic: Int = intent.getIntExtra("exotic", 0)
        var active: Int = intent.getIntExtra("active", 0)
        var extraFatigue=0
        var extraExotic=0
        var extraActive=0
        binding.firstQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraFatigue=when (checkedId) {
                R.id.first1->0
                R.id.first2 -> 20
                R.id.first3 -> 40
                R.id.first4 -> 60
                R.id.first5 ->80
                else-> 50
            }
            fatigue+=extraFatigue
        }
        binding.secondQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraExotic=when (checkedId) {
                R.id.second1->0
                R.id.second2 -> 20
                R.id.second3 -> 40
                R.id.second4 -> 60
                R.id.second5 ->80
                else-> 50
            }
            exotic+=extraExotic
        }
        binding.thirdQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraActive=when (checkedId) {
                R.id.third1->0
                R.id.third2 -> 20
                R.id.third3 -> 40
                R.id.third4 -> 60
                R.id.third5 ->80
                else-> 50
            }
            active+=extraActive
        }
        binding.layer.setOnClickListener {
            val intent = Intent(this, MainInfoActivity::class.java)
            with(getSharedPreferences(USER_INFO, MODE_PRIVATE).edit()) {
                putInt(FATIGUE,fatigue)
                putInt(EXOTIC,exotic)
                putInt(ACTIVITY,active)
            }.apply()
            startActivity(intent)
        }
    }
}

/*
val database: UserDatabase = Room.databaseBuilder(
    this@SecondSurveyActivity,
    UserDatabase::class.java, "members"
).allowMainThreadQueries().build()
member = database.memberDao().getMembers()
*/
