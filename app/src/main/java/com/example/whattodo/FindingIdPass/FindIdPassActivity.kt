package com.example.whattodo.FindingIdPass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.viewpager2.widget.ViewPager2
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityFindIdPassBinding
import com.google.android.material.tabs.TabLayoutMediator

/* 프래그먼트들 담기 위한 액티비티 */
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
        })

        val txt: String? = intent.getStringExtra("text")
        if (txt.equals(getString(R.string.find_Id))) {
            /* 뷰 페이져의 원하는 페이지로 디폴트는 0인듯 */
            binding.viewpager.currentItem = 0
        } else {
            binding.viewpager.currentItem = 1
        }
        TabLayoutMediator(binding.tab, binding.viewpager) { tab, position ->
            tab.text = tabList[position].toString()
        }.attach()
    }

    /* 툴바 <- 눌렀을 때 뒤로가기 */
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
