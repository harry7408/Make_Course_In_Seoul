package com.example.whattodo.SecondFeature

import android.graphics.Color
import android.os.Build.VERSION_CODES.M
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.databinding.ActivityCourseListShowBinding
import com.example.whattodo.datas.Store
import com.google.android.material.bottomsheet.BottomSheetBehavior
import net.daum.android.map.coord.MapCoord
import net.daum.android.map.coord.MapCoordLatLng
import net.daum.mf.map.api.CameraUpdateFactory
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPointBounds
import net.daum.mf.map.api.MapPolyline


private const val TAG = "CourseListShowActivity"

class CourseListShowActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCourseListShowBinding
    private lateinit var serverOutput: ArrayList<Store>

    private var mapFlag = false

    private var markers = ArrayList<MapPOIItem>()

    private var storeAdapter = CourseListAdapter {
        collapseBottomSheet()
        moveCamera(it, 15)
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

        setMarker(serverOutput)
        binding.mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(
                serverOutput[0].y,
                serverOutput[0].x
            ), true
        )
        binding.mapView.setZoomLevel(8, true)
        /* val marker=MapPOIItem()
         marker.itemName=serverOutput[0].placeName
         marker.setMapPoint(MapPoint.mapPointWithGeoCoord(serverOutput[0].y,serverOutput[0].x))
         binding.mapView.addPOIItem(marker)*/

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
        polyLine.lineColor = (Color.argb(128, 0, 0, 0))
        val marker = MapPOIItem()
        stores.forEachIndexed { index, store ->
            marker.apply {
                Log.e(TAG, "${store.x}, ${store.y}")
                mapPoint = MapPoint.mapPointWithGeoCoord(store.y, store.x)
                itemName = stores[index].placeName
                markerType = MapPOIItem.MarkerType.BluePin
                markers.add(marker)
            }
            polyLine.addPoint(MapPoint.mapPointWithGeoCoord(store.y, store.x))
            binding.mapView.addPOIItems(markers.toTypedArray())
        }
        binding.mapView.addPolyline(polyLine)
        val mapPointBounds = MapPointBounds(polyLine.mapPoints)
        val padding = 100
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
}