package com.example.whattodo.FirstFeature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.databinding.ActivityStoreListShowBinding
import com.example.whattodo.datas.Store


private const val TAG = "StoreListShowActivity"

class StoreListShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreListShowBinding
    private lateinit var storeAdapter : StoreGetAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreListShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)
        /* 이전 프래그먼트에서 누른 카테고리 이름 */
        val categoryD = intent.getParcelableArrayListExtra<Store>("selected")
        Log.e(TAG,"$categoryD")

        storeAdapter = StoreGetAdapter {
            val intent = Intent(this, StoreDetailActivity::class.java)
            intent.putExtra("clickedStore", it)
            startActivity(intent)
        }

        binding.storeListRecyclerView.apply {
            storeAdapter.submitList(categoryD)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = storeAdapter
        }


    }
}