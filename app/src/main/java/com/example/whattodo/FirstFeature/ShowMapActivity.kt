package com.example.whattodo.FirstFeature

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.Touch.scrollTo
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.R
import com.example.whattodo.SecondFeature.CourseListAdapter
import com.example.whattodo.databinding.ActivityShowMapBinding
import com.example.whattodo.datas.PlaceCategory
import com.example.whattodo.datas.Store
import com.example.whattodo.network.RetrofitAPI
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.STATE_COLLAPSED
import com.google.android.material.chip.Chip
import net.daum.android.map.coord.MapCoord
import net.daum.android.map.coord.MapCoordLatLng
import net.daum.mf.map.api.CameraUpdate
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPOIItem.MarkerType
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPoint.PlainCoordinate
import net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord
import net.daum.mf.map.api.MapView
import org.jetbrains.annotations.Contract
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "ShowMapActivity"

class ShowMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowMapBinding
    private var mapAdapter = MapGetAdapter {
        collapseBottomSheet()
        moveCamera(it, 1)
    }
    private val dList = mutableListOf<String>()
    private lateinit var serverOutput: List<Store>
    private var markers = ArrayList<MapPOIItem>()
    private var mapFlag = false
    private lateinit var categoryD:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)
        /* 이전 프래그먼트에서 누른 카테고리 이름 */
        val categoryC = intent.getStringExtra("selected").toString()

        initCategoryD(categoryC)
        categoryChips()

        binding.bottomSheetLayout.storeListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = mapAdapter
        }

        /* 지도의 처음 위치 설정 */
        binding.mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(
                /* 서울시청 좌표 */
                37.566826,126.9786567
            ), true
        )
        binding.mapView.setZoomLevel(7, true)


        /* 여기 안에 서버랑 통신하는 부분 넣어야 할 듯*/
        binding.categoryChipGroup.setOnCheckedStateChangeListener { _, _ ->
            val chip = findViewById<Chip>(binding.categoryChipGroup.checkedChipId)
            categoryD= chip.text.toString()
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
                            if (mapFlag.not()) {
                                Toast.makeText(applicationContext, "오류가 발생했습니다", Toast.LENGTH_SHORT)
                                    .show()
                            } else if (responseData.isEmpty()) {
                                Toast.makeText(
                                    applicationContext,
                                    "장소 정보가 없습니다",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            serverOutput = responseData
                            /* 데이터 연결 부분 */
                            mapAdapter.setData(serverOutput)
                            val marker=MapPOIItem()
                            serverOutput.forEachIndexed { index,store->
//                                val markerImage=initMarker(store)
                                marker.apply {
                                    itemName = store.placeName
                                    mapPoint = mapPointWithGeoCoord(store.x, store.y)
                                    markerType = MapPOIItem.MarkerType.CustomImage
//                                    customImageResourceId=markerImage
                                    markers.add(marker)
                                }
                                binding.mapView.addPOIItems(markers.toTypedArray())
                            }
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

    private fun collapseBottomSheet() {
        val bottomSheet = BottomSheetBehavior.from(binding.bottomSheetLayout.root)
        bottomSheet.state = STATE_COLLAPSED
    }


    private fun moveCamera(position: MapPoint.GeoCoordinate, zoomLevel: Int) {
        if (mapFlag.not()) return
        val cameraUpdate = CameraUpdateFactory.newMapPoint(
            mapPointWithGeoCoord(
                position.latitude,
                position.longitude)
        )
        binding.mapView.moveCamera(cameraUpdate)
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
        mapFlag = true
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onSurfaceDestroyed()
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
                dList.add(0,"터프팅")
                dList.add(1,"캔들&향수&비누")
                dList.add(2,"식물")
                dList.add(3,"켈리그라피")
                dList.add(4,"포장")
                dList.add(5,"미니어쳐")
                dList.add(6,"뜨개질")
            }
            "Level2" -> {
                dList.add(0, "미술")
                dList.add(1, "금속&유리")
                dList.add(2,"라탄")
                dList.add(3,"가죽")

            }
            "Level3" -> {
                dList.add(0, "요리")
                dList.add(1, "목공")
                dList.add(2,"도자기")
            }
            "관람" -> {
                dList.add(0, "동물원")
                dList.add(1, "식물원")
            }
            "관광" -> {
                dList.add(0, "궁")
                dList.add(1, "전망대")
                dList.add(2, "관광&명소")
                dList.add(3,"고개")
                dList.add(4,"광장")
                dList.add(5,"촬영지")
                dList.add(6,"케이블카")
            }
            "풍경" -> {
                dList.add(0, "폭포")
                dList.add(1, "하천")
                dList.add(2, "공원")
                dList.add(3,"숲")
                dList.add(4,"호수")
            }
            "테마거리" -> {
                dList.add(0,"테마거리")
                dList.add(1,"카페거리")
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
                dList.add(10,"드로잉카페")
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
   /* fun initMarker(store: Store):Int {
        return when(store.categoryName) {
            "수상스포츠","클라이밍","수영장","스킨스쿠버","스케이트장",
            "탁구","볼링","사격&궁도","스크린야구","스크린골프"->R.drawable.sports
            "산","계곡"->R.drawable.nature
            "테마파크","워터테마파크","눈썰매장"->R.drawable.activity
            "오락실","실내낚시","만화카페"->R.drawable.alone
            "VR","방탈출카페","보드게임카페" -> R.drawable.many
            "터프팅","캔들&향수&비누","식물","켈리그라피","포장",
            "미니어처","뜨개질" -> R.drawable.level1
            "미술","금속&유리","라탄","가죽" -> R.drawable.level2
            "요리","목공","도자기" -> R.drawable.level3
            "동물원","식물원"->R.drawable.show
            "궁","전망대","관광&명소","고개","광장","촬영지","케이블카"->R.drawable.besttrip
            "폭포","하천","공원","숲","호수"->R.drawable.scene
            "테마거리","카페거리"->R.drawable.themaroad
            "룸카페&멀티방","파티룸","스파"->R.drawable.relax
            "백화점"->R.drawable.shopping
            "갤러리카페","고양이카페","디저트카페","뮤직카페","북카페",
            "타로&사주&상담카페","플라워카페","한옥카페","슬라임카페",
            "피로회복카페","드로잉카페" -> R.drawable.cafe
            "실내포장마차","와인바","일본식주점","칵테일바",
            "호프&요리주점"->R.drawable.alchol
            "아쿠아리움","미술관","전시관"->R.drawable.gallery
            "공연장&연극극장","영화관"->R.drawable.concert
            else ->{ R.drawable.else_marker}
        }
    }*/
}