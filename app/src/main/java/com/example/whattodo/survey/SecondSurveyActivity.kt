package com.example.whattodo.survey

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.whattodo.*
import com.example.whattodo.databinding.ActivitySecondSurveyBinding
import com.example.whattodo.FirstFeature.MainInfoActivity
import com.example.whattodo.db.UserDatabase
import com.example.whattodo.entity.Member

private const val TAG = "SecondSurveyActivity"

class SecondSurveyActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondSurveyBinding
    private lateinit var currentMember: Member

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondSurveyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = getString(R.string.survey)

        val sharedPreferences = getSharedPreferences(USER_INFO, MODE_PRIVATE)
        val currentUserId = sharedPreferences.getString(ID, null)
        val currentUserPassword = sharedPreferences.getString(PASS, null)
        val currentUserName = sharedPreferences.getString(NAME, null)
        val currentUserEmail = sharedPreferences.getString(EMAIL, null)
        val currentUserBirthDay = sharedPreferences.getString(BDAY, null)
        val currentUserGender = sharedPreferences.getString(GENDER, null)

        var fatigue: Int = intent.getIntExtra("fatigue", 0)
        var exotic: Int = intent.getIntExtra("exotic", 0)
        var active: Int = intent.getIntExtra("active", 0)
        var extraFatigue = 0
        var extraExotic = 0
        var extraActive = 0
        binding.firstQuestion.setOnCheckedChangeListener { _, checkedId ->
            extraFatigue = when (checkedId) {
                R.id.first1 -> 0
                R.id.first2 -> 20
                R.id.first3 -> 40
                R.id.first4 -> 60
                R.id.first5 -> 80
                else -> 50
            }
            fatigue += extraFatigue
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
            exotic += extraExotic
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
            active += extraActive
        }
        binding.layer.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this@SecondSurveyActivity)
            builder.setTitle("회원가입")
            builder.setMessage("회원가입 성공")
            builder.setPositiveButton(
                R.string.ok,
                DialogInterface.OnClickListener { dialog, which ->
                    val intent = Intent(this@SecondSurveyActivity, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    with(getSharedPreferences(USER_INFO, MODE_PRIVATE).edit()) {
                        putInt(FATIGUE, fatigue)
                        putInt(EXOTIC, exotic)
                        putInt(ACTIVITY, active)
                    }.apply()
                    currentMember = Member(
                        currentUserId.toString(), currentUserPassword, currentUserEmail,
                        currentUserName, currentUserBirthDay, currentUserGender, fatigue, exotic, active
                    )

                    Thread {
                        UserDatabase.getInstance(this@SecondSurveyActivity)?.memberDao()
                            ?.updateMember(currentMember)
                    }.start()

                    startActivity(intent)
                })
            builder.create()
            builder.show()
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
