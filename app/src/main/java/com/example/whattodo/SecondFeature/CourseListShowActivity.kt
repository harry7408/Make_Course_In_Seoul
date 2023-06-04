package com.example.whattodo.SecondFeature

import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityCourseListShowBinding
import com.example.whattodo.databinding.CustomBalloonBinding
import com.example.whattodo.datas.Store
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import net.daum.android.map.coord.MapCoord
import net.daum.android.map.coord.MapCoordLatLng
import net.daum.mf.map.api.CalloutBalloonAdapter
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapPolyline
import net.daum.mf.map.api.MapView


private const val TAG = "CourseListShowActivity"

class CourseListShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseListShowBinding
    private var mapFlag = false
    private lateinit var serverOutput: ArrayList<Store>

    private var markers = ArrayList<MapPOIItem>()

    private var storeAdapter = CourseListAdapter {
        collapseBottomSheet()
        moveCamera(it, 1)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseListShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "코스 목록"
        serverOutput = intent.getParcelableArrayListExtra<Store>("response")!!

        Log.e(TAG, "$serverOutput")


        storeAdapter.setData(serverOutput)
        binding.bottomSheetLayout.storeListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = storeAdapter
        }

        setMarker(serverOutput)
        binding.mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(
                serverOutput[0].y,
                serverOutput[0].x
            ), true
        )
        binding.mapView.setZoomLevel(2, true)
    }

    private fun collapseBottomSheet() {
        val bottomSheet = BottomSheetBehavior.from(binding.bottomSheetLayout.root)
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    /* List Item 클릭시 해당 위치로 이동*/
    private fun moveCamera(position: MapPoint.GeoCoordinate, zoomLevel: Int) {
        if (!mapFlag) return
        val cameraUpdate = CameraUpdateFactory.newMapPoint(
            MapPoint.mapPointWithGeoCoord(
                position.latitude,
                position.longitude
            )
        )
        binding.mapView.moveCamera(cameraUpdate)
    }

    /* 마커 찍기와 선 긋는 부분 */
    private fun setMarker(stores: ArrayList<Store>) {
        val polyLine = MapPolyline()
        polyLine.lineColor = (Color.argb(128, 51, 102, 51))
        val marker = MapPOIItem()
        stores.forEachIndexed { index, store ->
            marker.apply {
                val currentImage = initMarker(store)
                Log.e(TAG, "${store.x}, ${store.y}")
                mapPoint = MapPoint.mapPointWithGeoCoord(store.y, store.x)
                itemName = stores[index].placeName
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId = currentImage   //currentImage
                isCustomImageAutoscale = true
                setCustomImageAnchor(0.5f, 0.5f)
            }
            markers.add(marker)
            binding.mapView.addPOIItem(marker)
            polyLine.addPoint(MapPoint.mapPointWithGeoCoord(store.y, store.x))
        }
        binding.mapView.addPOIItems(markers.toTypedArray())
        binding.mapView.addPolyline(polyLine)
        val mapPointBounds = MapPointBounds(polyLine.mapPoints)
        val padding = 300
        binding.mapView.moveCamera(CameraUpdateFactory.newMapPointBounds(mapPointBounds, padding))

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

   /* inner class CustomBalloonAdapter(private val context: Context) : CalloutBalloonAdapter {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.custom_balloon, null)

        override fun getCalloutBalloon(p0: MapPOIItem?): View {
            view.findViewById<TextView>(R.id.balloonStoreTextView).text = p0?.itemName
            return view
        }

        override fun getPressedCalloutBalloon(p0: MapPOIItem?): View? {
            return null
        }
    }*/


    private fun initMarker(store: Store): Int {
        return when (store.categoryName) {
            "수상스포츠" -> R.drawable.d_1
            "클라이밍" -> R.drawable.d_2
            "수영장" -> R.drawable.d_3
            "스킨스쿠버" -> R.drawable.d_4
            "스케이트장" -> R.drawable.d_5
            "산" -> R.drawable.d_6
            "계곡" -> R.drawable.d_7
            "테마파크" -> R.drawable.d_8
            "워터테마파크" -> R.drawable.d_9
            "눈썰매장" -> R.drawable.d_10
            "탁구" -> R.drawable.d_11
            "볼링" -> R.drawable.d_12
            "사격&궁도" -> R.drawable.d_13
            "스크린야구" -> R.drawable.d_14
            "스크린골프" -> R.drawable.d_15
            "오락실" -> R.drawable.d_16
            "실내낚시" -> R.drawable.d_17
            "만화카페" -> R.drawable.d_18
            "VR" -> R.drawable.d_19
            "방탈출카페" -> R.drawable.d_20
            "보드게임카페" -> R.drawable.d_21
            "터프팅" -> R.drawable.d_22
            "캔들&향수&비누" -> R.drawable.d_23
            "식물" -> R.drawable.d_24
            "켈리그라피" -> R.drawable.d_25
            "포장" -> R.drawable.d_26
            "미니어처" -> R.drawable.d_27
            "뜨개질" -> R.drawable.d_28
            "미술" -> R.drawable.d_29
            "금속&유리" -> R.drawable.d_30
            "라탄" -> R.drawable.d_31
            "가죽" -> R.drawable.d_32
            "요리" -> R.drawable.d_33
            "목공" -> R.drawable.d_34
            "도자기" -> R.drawable.d_35
            "동물원" -> R.drawable.d_36
            "식물원" -> R.drawable.d_37
            "궁" -> R.drawable.d_38
            "전망대" -> R.drawable.d_39
            "관광&명소" -> R.drawable.d_40
            "고개" -> R.drawable.d_41
            "광장" -> R.drawable.d_42
            "촬영지" -> R.drawable.d_43
            "케이블카" -> R.drawable.d_44
            "폭포" -> R.drawable.d_45
            "하천" -> R.drawable.d_46
            "공원" -> R.drawable.d_47
            "숲" -> R.drawable.d_48
            "호수" -> R.drawable.d_49
            "테마거리" -> R.drawable.d_50
            "카페거리" -> R.drawable.d_51
            "룸카페&멀티방" -> R.drawable.d_52
            "파티룸" -> R.drawable.d_53
            "스파" -> R.drawable.d_54
            "백화점" -> R.drawable.d_55
            "갤러리카페" -> R.drawable.d_56
            "고양이카페" -> R.drawable.d_57
            "디저트카페" -> R.drawable.d_58
            "뮤직카페" -> R.drawable.d_59
            "북카페" -> R.drawable.d_60
            "타로&사주&상담카페" -> R.drawable.d_61
            "플라워카페" -> R.drawable.d_62
            "한옥카페" -> R.drawable.d_63
            "슬라임카페" -> R.drawable.d_64
            "피로회복카페" -> R.drawable.d_65
            "드로잉카페" -> R.drawable.d_66
            "실내포장마차" -> R.drawable.d_67
            "와인바" -> R.drawable.d_68
            "일본식주점" -> R.drawable.d_69
            "칵테일바" -> R.drawable.d_70
            "호프&요리주점" -> R.drawable.d_71
            "아쿠아리움" -> R.drawable.d_72
            "미술관" -> R.drawable.d_73
            "전시관" -> R.drawable.d_74
            "공연장&연극극장" -> R.drawable.d_75
            "영화관" -> R.drawable.d_76
            "음식점"->R.drawable.restaurant
            else -> {
                R.drawable.else_marker
            }
        }
    }
}
