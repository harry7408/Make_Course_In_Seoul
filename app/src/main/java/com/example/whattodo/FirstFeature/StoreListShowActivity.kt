package com.example.whattodo.FirstFeature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityStoreListShowBinding
import com.example.whattodo.datas.PlaceCategory
import com.example.whattodo.datas.Store
import com.example.whattodo.network.RetrofitAPI
import com.google.android.material.chip.Chip
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


private const val TAG = "StoreListShowActivity"

class StoreListShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoreListShowBinding
    private lateinit var storeAdapter: StoreGetAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreListShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)
        /* 이전 프래그먼트에서 누른 카테고리 이름 */
        val categoryD = intent.getParcelableArrayListExtra<Store>("selected")
        storeAdapter.submitList(categoryD)

        storeAdapter = StoreGetAdapter {
            val intent = Intent(this, StoreDetailActivity::class.java)
            intent.putExtra("clickedStore", it)
            startActivity(intent)
        }

        binding.storeListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = storeAdapter
        }


    }
}