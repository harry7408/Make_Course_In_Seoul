package com.example.whattodo.FirstFeature

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityShowMapBinding
import com.example.whattodo.datas.PlaceCategory
import com.example.whattodo.datas.StoreList
import com.example.whattodo.network.RetrofitAPI
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.chip.Chip
import net.daum.android.map.coord.MapCoordLatLng
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPoint.PlainCoordinate
import net.daum.mf.map.api.MapView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const private val TAG="ShowMapActivity"
class ShowMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowMapBinding

    private lateinit var myMap:MapView

    private val dList = mutableListOf<String>()

    private val mapGetAdapter=MapGetAdapter {
        collapseBottomSheet()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)
        val categoryC = intent.getStringExtra("selected").toString()
        initCategoryD(categoryC)
        categoryChips()




        /* 여기 안에 서버랑 통신하는 부분 넣어야 할 듯*/
        binding.categoryChipGroup.setOnCheckedStateChangeListener { _, _ ->
            val chip=findViewById<Chip>(binding.categoryChipGroup.checkedChipId)
            val categoryD=chip.text.toString()
            Log.e(TAG,"$categoryC - $categoryD")

            val serverRequestData=PlaceCategory(categoryC,categoryD)


            RetrofitAPI.storeService.requestStore(serverRequestData).enqueue(object: Callback<StoreList>{
                override fun onResponse(call: Call<StoreList>, response: Response<StoreList>) {
                    val responseData=response.body()?.storeList.orEmpty()

                    if (responseData.isEmpty()) {
                        Toast.makeText(this@ShowMapActivity,"검색 결과가 없습니다",Toast.LENGTH_SHORT).show()
                        return
                    } else {
//                        markers=responseData.map {
//                            PlainCoordinate(it.x.toDouble(),it.y.toDouble())
//                        }
                    }
//                    mapGetAdapter.setData(responseData)
//                    moveCamera(markers.first())

                }

                override fun onFailure(call: Call<StoreList>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

    private fun collapseBottomSheet() {
        val bottomSheet=BottomSheetBehavior.from(binding.bottomSheetLayout.root)
        bottomSheet.state=STATE_COLLAPSED
    }

    /*private fun moveCamera(position: MapCoordLatLng, zoomLevel:Double) {

    }*/

    override fun onStop() {
        super.onStop()
        dList.clear()
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
                dList.add(7, "사격")
                dList.add(8, "스크린야구")
                dList.add(9, "스크린골프")
            }
            "자연" -> {
                dList.add(0, "산")
                dList.add(1, "계곡")
            }
            "어트랙션" -> {
                dList.add(0, "서바이벌게임")
                dList.add(1, "테마파크(중*대형)")
                dList.add(2, "눈썰매장")
                dList.add(3, "테마파크(소형)")
            }
            "혼자도가능" -> {
                dList.add(0, "산")
                dList.add(1, "계곡")
            }
            "여러명" -> {
                dList.add(0, "VR게임")
                dList.add(1, "방탕출카페")
                dList.add(2, "보드게임카페")
            }
            "요리&베이킹" -> {
//                dList.add(1,"방탕출카페")
            }
            "식물" -> {
                dList.add(0, "플라워*가드닝")
                dList.add(1, "테라리움")
            }
            "메이크업" -> {
//                dList.add(0, "산")
//                dList.add(1, "계곡")
            }

            "다도" -> {
//                dList.add(0, "산")
//                dList.add(1, "계곡")
            }
            "미술" -> {
//                dList.add(0, "산")
//                dList.add(1, "계곡")
            }
            "핸드메이드" -> {
//                dList.add(0, "산")
//                dList.add(1, "계곡")
            }
            "관람" -> {
                dList.add(0, "야외 동물원")
                dList.add(1, "실내 동물원")
            }
            "관광명소" -> {
                dList.add(0, "궁")
                dList.add(1, "전망대")
                dList.add(2, "관광,명소")
            }
            "테마거리" -> {
                dList.add(0, "테마거리")
                dList.add(1, "카페거리")
            }
            "풍경" -> {
                dList.add(0, "공원")
                dList.add(1, "숲")
                dList.add(2, "호수")
            }
            "휴식" -> {
                dList.add(0, "룸카페&멀티방")
                dList.add(1, "파티룸")
                dList.add(2, "스파")
            }
            "쇼핑" -> {
                dList.add(0, "백화점")
            }
            "카페" -> {
                dList.add(0, "플라워카페")
                dList.add(1, "갤러리카페")
                dList.add(2, "슬라임카페")
                dList.add(3, "고양이카페")
                dList.add(4, "상담카페")
                dList.add(5, "카페")
                dList.add(6, "이색카페")
                dList.add(7, "북카페")
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
                dList.add(0, "공연장,연극극장")
                dList.add(1, "영화관")
            }
        }
    }
}