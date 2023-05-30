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
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityCourseListShowBinding
import com.example.whattodo.databinding.CustomBalloonBinding
import com.example.whattodo.datas.Store
import com.google.android.material.bottomsheet.BottomSheetBehavior
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
    private lateinit var serverOutput: ArrayList<Store>

    private var mapFlag = false

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
        serverOutput = intent.getParcelableArrayListExtra("response")!!



        storeAdapter.setData(serverOutput)
        binding.bottomSheetLayout.storeListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
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
        polyLine.lineColor = (Color.argb(128, 255, 105, 180))
        val marker = MapPOIItem()
        stores.forEachIndexed { index, store ->
            marker.apply {
                Log.e(TAG, "${store.x}, ${store.y}")
                mapPoint = MapPoint.mapPointWithGeoCoord(store.y, store.x)
                itemName = stores[index].placeName
                markerType = MapPOIItem.MarkerType.CustomImage
                customImageResourceId=R.drawable.else_marker    //initMarker(store.categoryName)
                isCustomImageAutoscale=true
                setCustomImageAnchor(0.5f,0.5f)
                markers.add(marker)
            }
            binding.mapView.setCalloutBalloonAdapter(CustomBalloonAdapter(markers))
            polyLine.addPoint(MapPoint.mapPointWithGeoCoord(store.y, store.x))
            binding.mapView.addPOIItems(markers.toTypedArray())
        }
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

    inner class CustomBalloonAdapter(markers:ArrayList<MapPOIItem>) : CalloutBalloonAdapter {
        val custombinding=CustomBalloonBinding.inflate(layoutInflater)
        val newinflater=layoutInflater.inflate(R.layout.custom_balloon,custombinding.root)
        override fun getCalloutBalloon(p0: MapPOIItem?): View {
            newinflater.findViewById<TextView>(R.id.balloonStoreTextView).text=p0?.itemName
            return newinflater
        }

        override fun getPressedCalloutBalloon(p0: MapPOIItem?): View? {
            return null
        }
    }

    /*fun initMarker(store: Store):Int {
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
