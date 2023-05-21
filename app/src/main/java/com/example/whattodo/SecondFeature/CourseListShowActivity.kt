package com.example.whattodo.SecondFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.datas.Course

import com.example.whattodo.databinding.ActivityCourseListShowBinding
import com.example.whattodo.datas.StoreList

private const val TAG="CourseListShowActivity"

class CourseListShowActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCourseListShowBinding
    private lateinit var serverOutput:StoreList
    private lateinit var storeAdapter: CourseListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCourseListShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "코스 목록"

        serverOutput = intent.getParcelableExtra<StoreList>("response")!!

        Log.d(TAG,"${serverOutput.storeList}")

        storeAdapter= CourseListAdapter(serverOutput)
        binding.bottomSheetLayout.storeListRecyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=storeAdapter
        }

    }
}