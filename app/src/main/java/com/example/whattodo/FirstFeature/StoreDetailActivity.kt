package com.example.whattodo.FirstFeature

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.net.toUri
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityStoreDetailBinding
import com.example.whattodo.datas.Coordinate
import com.example.whattodo.datas.Store

class StoreDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityStoreDetailBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

         val clickedStore= intent.getParcelableExtra<Store>("clickedStore")

        clickedStore?.let { initStoreDetail(it) }

        binding.storeDetailCallButton.setOnClickListener {
            lateinit var intent:Intent
            if (clickedStore?.phone !=null) {
                intent=Intent(Intent.ACTION_VIEW, Uri.parse("tel:${clickedStore.phone}"))
                startActivity(intent)
            } else {
                Toast.makeText(this,"전화번호가 없습니다",Toast.LENGTH_SHORT).show()
            }
        }

        binding.storeDetailMapButton.setOnClickListener {
            val intent= Intent(applicationContext,ShowMapActivity::class.java)
            if (clickedStore != null) {
                intent.putExtra("storePosition",Coordinate(clickedStore.x,clickedStore.y))
            }
            startActivity(intent)
        }
    }

    private fun initStoreDetail(store :Store) {
        if (store?.imgUrl==null) {
            binding.storeDetailImageView.setImageResource(R.drawable.noimage)
        } else {
            binding.storeDetailImageView.setImageURI(store?.imgUrl!!.toUri())
        }
        binding.storeDetailStoreNameTextView.text=store?.placeName
        /* 등등 초기화 */
    }
}