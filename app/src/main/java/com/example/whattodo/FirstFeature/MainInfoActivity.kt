package com.example.whattodo.FirstFeature

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.viewpager2.widget.ViewPager2
import com.example.whattodo.*
import com.example.whattodo.SecondFeature.CourseFragment
import com.example.whattodo.ThirdFeature.MypageFragment
import com.example.whattodo.databinding.ActivityMainInfoBinding
import com.google.android.material.tabs.TabLayoutMediator


private const val TAG = "MainInfoActivity"

class MainInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolBar)

        val barList = listOf<String>(
            getString(R.string.app_name),
            getString(R.string.make_course),
            getString(R.string.mypage)
        )
        val textList = listOf<String>(
            getString(R.string.home),
            getString(R.string.make_course),
            getString(R.string.mypage)
        )
        val iconList = listOf(R.drawable.home, R.drawable.course, R.drawable.mypage)
        val myAdapter = MyMainAdapter(this)

        myAdapter.addFragment(PlaceFragment())
        myAdapter.addFragment(CourseFragment())
        myAdapter.addFragment(MypageFragment())
        binding.viewpager.adapter = myAdapter
        val sharedPreferences=getSharedPreferences(USER_INFO, MODE_PRIVATE)
        val userCode=sharedPreferences.getString(UID,null)
        Log.e(TAG,"$userCode")
        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                supportActionBar?.title = barList[position]
            }
        })

        TabLayoutMediator(binding.tab, binding.viewpager) { tab, position ->
            tab.text = textList[position]
            tab.setIcon(iconList[position])
        }.attach()
    }

    /* 프래그먼트 화면 전환 처리 */
    override fun onBackPressed() {
        if (binding.viewpager.currentItem==0) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("정말로 종료하시겠습니까?")
            builder.setPositiveButton("Ok") { _, _ ->
                this.finish()
            }
            builder.setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            builder.show()
        } else {
            binding.viewpager.setCurrentItem(0,true)
        }
    }
}


