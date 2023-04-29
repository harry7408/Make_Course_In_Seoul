package com.example.whattodo.FindIP

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toolbar
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityFindIdPassBinding
import com.google.android.material.tabs.TabLayoutMediator

class FindIdPassActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindIdPassBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindIdPassBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /* toolBar 적용 */
        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.find_id_pass)

        val tabList = listOf<String>(getString(R.string.find_Id), getString(R.string.find_Pass))

        val myAdapter = MyFindAdapter(this)
        myAdapter.addFragment(FindIdFragment())
        myAdapter.addFragment(FindPassFragment())



        binding.viewpager.adapter = myAdapter

        binding.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
            }
        })

        val txt: String? = intent.getStringExtra("text")
        if (txt.equals(getString(R.string.find_Id))) {
            /* 뷰 페이져의 원하는 페이지로 디폴트는 0인듯 */
            binding.viewpager.setCurrentItem(0)
        } else {
            binding.viewpager.setCurrentItem(1)
        }

        TabLayoutMediator(binding.tab, binding.viewpager) { tab, position ->
            tab.text = tabList[position].toString()
        }.attach()


    }

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
//        이전에서 터치한 텍스트 전달 받기위함
//

//        이메일찾기 비밀번호 찾기 안에서 서로 이동 가능하도록
//        binding.findId.setOnClickListener {
//            binding.findId.setBackgroundResource(R.drawable.background_rectangle_yellow)
//            binding.findPass.setBackgroundResource(R.drawable.background_rectangle2)
//            val fragment = FindIdFragment()
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.viewpager, fragment)
//                .commit()
//        }
//        binding.findPass.setOnClickListener {
//            binding.findId.setBackgroundResource(R.drawable.background_rectangle2)
//            binding.findPass.setBackgroundResource(R.drawable.background_rectangle_yellow)
//            val fragment = FindPassFragment()
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.viewpager, fragment)
//                .commit()
//        }