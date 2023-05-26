package com.example.whattodo.SecondFeature

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.datas.Course
import com.example.whattodo.databinding.ActivityCourseListShowBinding
import com.example.whattodo.datas.Store
import com.google.android.material.bottomsheet.BottomSheetBehavior
import net.daum.android.map.coord.MapCoord
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView


private const val TAG = "CourseListShowActivity"

class CourseListShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseListShowBinding
    private lateinit var serverOutput: ArrayList<Store>

    private var mapView = MapView(this)
    private var marker = MapPOIItem()

    private var mapFlag = false

    private var storeAdapter = CourseListAdapter {
        collapseBottomSheet()
        moveCamera(it, 7)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseListShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "코스 목록"

        serverOutput = intent.getParcelableArrayListExtra("response")!!
        Log.e(TAG, "${serverOutput[0]}")

        storeAdapter.setData(serverOutput)
        binding.bottomSheetLayout.storeListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = storeAdapter
        }
    }

    private fun collapseBottomSheet() {
        val bottomSheet = BottomSheetBehavior.from(binding.bottomSheetLayout.root)
        bottomSheet.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun moveCamera(position: MapCoord, zoomLevel: Int) {
        if (!mapFlag) return
        mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(position.x, position.y), true)
        mapView.setZoomLevel(zoomLevel, true)
        val mapPoint = MapPoint.mapPointWithGeoCoord(position.x, position.y)
        val cameraUpdate = CameraUpdateFactory.newMapPoint(mapPoint)

        mapView.moveCamera(cameraUpdate)
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
        mapFlag = true
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onSurfaceDestroyed()
    }

}