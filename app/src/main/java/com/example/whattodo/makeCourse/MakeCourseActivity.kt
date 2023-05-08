package com.example.whattodo.makeCourse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityMakeCourseBinding
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipDrawable

const val TAG="MakeCourseActivity"
class MakeCourseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakeCourseBinding
    private var keywords= mutableListOf<String>()
    private var userGoal= mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMakeCourseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)
        initChips()
        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "정보 입력"

        /*식사 포함여부와 필수장소 포함 여부 정하기 위한 변수들*/
        val mealFlag=intent.getBooleanExtra("meal",false)
        val placeFlag=intent.getBooleanExtra("place",false)



    }


    private fun initChips() {
        val goals= listOf<String>("산책","음주","메이킹","힐링","관람","뇌활동","경치","일반","운동","솔로")
        binding.chipGroup1.apply {
            goals.forEach { text->
                addView(createChip(text))
            }
        }
    }

    private fun createChip(text:String):Chip {
        val chip= Chip(this).apply {
            setText(text)
            isCheckable=true
            isClickable=true
        }

        return chip
    }
}