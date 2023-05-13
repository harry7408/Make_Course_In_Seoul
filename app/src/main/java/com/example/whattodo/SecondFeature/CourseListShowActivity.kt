package com.example.whattodo.SecondFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whattodo.datas.Course

import com.example.whattodo.databinding.ActivityCourseListShowBinding

class CourseListShowActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCourseListShowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCourseListShowBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "코스 목록"

        val networkInput =intent.getParcelableExtra<Course>("courseInput")

    }
}