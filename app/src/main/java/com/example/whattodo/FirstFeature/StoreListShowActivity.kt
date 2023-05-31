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
    private val dList = mutableListOf<String>()
    private lateinit var categoryD: String
    private lateinit var storeAdapter:StoreGetAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoreListShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)
        /* 이전 프래그먼트에서 누른 카테고리 이름 */
        val categoryC = intent.getStringExtra("selected").toString()

        initCategoryD(categoryC)
        categoryChips()

        storeAdapter= StoreGetAdapter {
            val intent= Intent(this,StoreDetailActivity::class.java)
            intent.putExtra("clickedStore",it)
            startActivity(intent)
        }

        binding.storeListRecyclerView.apply {
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
            adapter=storeAdapter
        }

        /* 여기 안에 서버랑 통신하는 부분 넣어야 할 듯*/
        binding.categoryChipGroup.setOnCheckedStateChangeListener { _, _ ->
            val chip = findViewById<Chip>(binding.categoryChipGroup.checkedChipId)
            categoryD = chip.text.toString()
            Log.e(TAG, "$categoryC - $categoryD")

            val serverRequestData = PlaceCategory(categoryC, categoryD)


            RetrofitAPI.storeService.requestStore(/*serverRequestData*/)
                .enqueue(object : Callback<List<Store>> {
                    override fun onResponse(
                        call: Call<List<Store>>,
                        response: Response<List<Store>>
                    ) {
                        if (response.isSuccessful) {
                            val responseData = response.body() ?: emptyList()
                            storeAdapter.submitList(responseData)

                        } else {
                            Log.e(TAG, "NULL값 넘어옴")
                        }
                    }

                    override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                        Log.e(TAG, "통신실패")
                        t.printStackTrace()
                    }
                })
        }
    }

    /* 필수장소 선택 chip 내용 초기화 하는 부분 */
    private fun categoryChips() {
        binding.categoryChipGroup.apply {
            dList.forEach { text ->
                addView(createChip(text))
            }
        }
    }

    /* 칩 제작 부분 */
    private fun createChip(text: String): Chip {
        val chip = Chip(this).apply {
            setText(text)
            isCheckable = true
            isClickable = true
        }
        return chip
    }

    private fun initCategoryD(txt: String) {
        when (txt) {
            "스포츠" -> {
                dList.add(0, "수상스포츠")
                dList.add(1, "클라이밍")
                dList.add(2, "수영장")
                dList.add(3, "스킨스쿠버")
                dList.add(4, "스케이트장")
                dList.add(5, "탁구")
                dList.add(6, "볼링")
                dList.add(7, "사격&궁도")
                dList.add(8, "스크린야구")
                dList.add(9, "스크린골프")
            }
            "자연" -> {
                dList.add(0, "산")
                dList.add(1, "계곡")
            }
            "어트랙션" -> {
                dList.add(0, "테마파크")
                dList.add(1, "워터테마파크")
                dList.add(2, "눈썰매장")
            }
            "1인가능" -> {
                dList.add(0, "오락실")
                dList.add(1, "실내낚시")
                dList.add(2, "만화카페")
            }
            "여러명" -> {
                dList.add(0, "VR")
                dList.add(1, "방탕출카페")
                dList.add(2, "보드게임카페")
            }
            "Level1" -> {
                dList.add(0, "터프팅")
                dList.add(1, "캔들&향수&비누")
                dList.add(2, "식물")
                dList.add(3, "켈리그라피")
                dList.add(4, "포장")
                dList.add(5, "미니어쳐")
                dList.add(6, "뜨개질")
            }
            "Level2" -> {
                dList.add(0, "미술")
                dList.add(1, "금속&유리")
                dList.add(2, "라탄")
                dList.add(3, "가죽")

            }
            "Level3" -> {
                dList.add(0, "요리")
                dList.add(1, "목공")
                dList.add(2, "도자기")
            }
            "관람" -> {
                dList.add(0, "동물원")
                dList.add(1, "식물원")
            }
            "관광" -> {
                dList.add(0, "궁")
                dList.add(1, "전망대")
                dList.add(2, "관광&명소")
                dList.add(3, "고개")
                dList.add(4, "광장")
                dList.add(5, "촬영지")
                dList.add(6, "케이블카")
            }
            "풍경" -> {
                dList.add(0, "폭포")
                dList.add(1, "하천")
                dList.add(2, "공원")
                dList.add(3, "숲")
                dList.add(4, "호수")
            }
            "테마거리" -> {
                dList.add(0, "테마거리")
                dList.add(1, "카페거리")
            }
            "휴식" -> {
                dList.add(0, "룸카페&멀티방")
                dList.add(1, "파티룸")
                dList.add(2, "스파")
            }
            "쇼핑" -> {
                dList.add(0, "백화점")
            }
            "테마카페" -> {
                dList.add(0, "갤러리카페")
                dList.add(1, "고양이카페")
                dList.add(2, "디저트카페")
                dList.add(3, "뮤직카페")
                dList.add(4, "북카페")
                dList.add(5, "타로&사주&상담카페")
                dList.add(6, "플라워카페")
                dList.add(7, "한옥카페")
                dList.add(8, "슬라임카페")
                dList.add(9, "피로회복카페")
                dList.add(10, "드로잉카페")
            }
            "음주" -> {
                dList.add(0, "실내포장마차")
                dList.add(1, "와인바")
                dList.add(2, "일본식주점")
                dList.add(3, "칵테일바")
                dList.add(4, "호프,요리주점")
            }
            "전시시설" -> {
                dList.add(0, "아쿠아리움")
                dList.add(1, "미술관")
                dList.add(2, "전시관")
            }
            "공연시설" -> {
                dList.add(0, "공연장&연극극장")
                dList.add(1, "영화관")
            }
        }
    }
}