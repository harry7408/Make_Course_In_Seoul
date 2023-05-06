package com.example.whattodo.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityMainInfoBinding
import com.google.android.material.tabs.TabLayoutMediator

//      사용자 취향 입력받았을때 넘어가는 처음 페이지 -> 네비게이션 + 프래그먼트로 이동 화면 이동 구현 해야할 듯
class MainInfoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        val barList= listOf<String>(getString(R.string.app_name),getString(R.string.make_course),getString(R.string.mypage))
        val textList= listOf<String>(getString(R.string.home),getString(R.string.make_course),getString(R.string.mypage))
        val iconList=listOf(R.drawable.home,R.drawable.course,R.drawable.mypage)
        val myAdapter=MyMainAdapter(this)

        myAdapter.addFragment(CourseFragment())
        myAdapter.addFragment(PlaceFragment())
        myAdapter.addFragment(MypageFragment())
        binding.viewpager.adapter=myAdapter

        binding.viewpager.registerOnPageChangeCallback(object:ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                supportActionBar?.setTitle(barList[position])
            }
        })

        TabLayoutMediator(binding.tab,binding.viewpager) { tab,position->
            tab.text=textList[position]
            tab.setIcon(iconList[position])
        }.attach()
    }
}