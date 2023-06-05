package com.example.whattodo.FirstFeature

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityStoreDetailBinding
import com.example.whattodo.datas.Coordinate
import com.example.whattodo.datas.Store
import com.shashank.sony.fancytoastlib.FancyToast


private const val TAG="StoreDetailActivity"
class StoreDetailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityStoreDetailBinding
    private var isFABOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityStoreDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

         val clickedStore= intent.getParcelableExtra<Store>("clickedStore")

        clickedStore?.let { initStoreDetail(it) }

        binding.menuFloatingActionButton.setOnClickListener {
            if (isFABOpen) {
                closeFABMenu()

            } else {
                openFABMenu()
            }
        }

        binding.storeDetailCallFloatingButton.setOnClickListener {
            if (clickedStore?.phone.isNullOrEmpty()) {
                FancyToast.makeText(this,"전화번호가 없습니다",FancyToast.LENGTH_SHORT,
                FancyToast.DEFAULT,R.drawable.nocall,false).show()
            } else {
                val intent=Intent(Intent.ACTION_VIEW, Uri.parse("tel:${clickedStore!!.phone}"))
                startActivity(intent)
            }
        }

        binding.storeDetailMapFloatingButton.setOnClickListener {
            val intent= Intent(applicationContext,ShowMapActivity::class.java)
            if (clickedStore != null) {
                intent.putExtra("storePosition",Coordinate(clickedStore.x,clickedStore.y))
                intent.putExtra("storeName",clickedStore.placeName)
                startActivity(intent)
            }

        }


    }

    private fun closeFABMenu() {
        isFABOpen=false
        binding.menuFloatingActionButton.setImageResource(R.drawable.plus)
        binding.storeDetailCallFloatingButton.animate()
            .translationY(0f).setListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    if(!isFABOpen) {
                        binding.storeDetailCallFloatingButton.visibility=View.GONE
                    }
                }
            })

        binding.storeDetailMapFloatingButton.animate()
            .translationY(0f).setListener(object: AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator) {
                    if(!isFABOpen) {
                        binding.storeDetailMapFloatingButton.visibility=View.GONE
                    }
                }
            })


    }

    private fun openFABMenu() {
        isFABOpen=true
        binding.menuFloatingActionButton.setImageResource(R.drawable.close)
        binding.storeDetailCallFloatingButton.visibility= View.VISIBLE
        binding.storeDetailMapFloatingButton.visibility=View.VISIBLE

        binding.storeDetailCallFloatingButton.animate()
            .translationY(-resources.getDimension(com.google.android.material.R.dimen.m3_side_sheet_standard_elevation))
        binding.storeDetailMapFloatingButton.animate()
            .translationY(-resources.getDimension(com.google.android.material.R.dimen.m3_side_sheet_standard_elevation))
    }

    private fun initStoreDetail(store :Store) {
        if (store.imgUrl.isNullOrEmpty()) {
            binding.storeDetailImageView.setImageResource(R.drawable.no_images)
        } else {
            Glide.with(this).load("http:${store.imgUrl}").into(binding.storeDetailImageView)
        }
        binding.storeDetailStoreNameTextView.text=store.placeName
        /* 등등 초기화 */

        binding.storeDetailCategoryNameTextView.text=store.categoryName
        binding.storeDetailRatingTextView.text="${store.avgRating} (${store.ratingNum}) | 리뷰 (${store.reviewNum})"

        if (store.introduction.isNullOrEmpty()) {
            binding.storeDetailIntroductionTextView.text="사장님의 한마디가 없습니다"
        } else {
            binding.storeDetailIntroductionTextView.text=store.introduction
        }

        if (store.menu.isNullOrEmpty()) {
            binding.storeDetailMenuTextView.text="준비 중입니다"
        } else {
            binding.storeDetailMenuTextView.text=store.menu
        }
        if (store.time.isNullOrEmpty()) {
            binding.storeDetailTimeTextView.text="준비 중입니다"
        } else {
            binding.storeDetailTimeTextView.text=store.time
        }

    }
}

