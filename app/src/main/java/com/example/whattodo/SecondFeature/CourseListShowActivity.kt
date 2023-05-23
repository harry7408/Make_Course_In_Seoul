package com.example.whattodo.SecondFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.datas.Course

import com.example.whattodo.databinding.ActivityCourseListShowBinding
import com.example.whattodo.datas.Store
import com.google.android.material.bottomsheet.BottomSheetBehavior


private const val TAG="CourseListShowActivity"

class CourseListShowActivity : AppCompatActivity() {

    private lateinit var binding:ActivityCourseListShowBinding
    private lateinit var serverOutput:ArrayList<Store>
    private lateinit var storeAdapter: CourseListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCourseListShowBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTheme(com.google.android.material.R.style.Theme_MaterialComponents_Light)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.title = "코스 목록"

        serverOutput= intent.getParcelableArrayListExtra("response")!!
        Log.e(TAG,"${serverOutput[0]}")



        storeAdapter= CourseListAdapter(serverOutput)
        binding.bottomSheetLayout.storeListRecyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=storeAdapter
        }

    }
    private fun collapseBottomSheet() {
        val bottomSheet= BottomSheetBehavior.from(binding.bottomSheetLayout.root)
        bottomSheet.state= BottomSheetBehavior.STATE_COLLAPSED
    }

    /*private fun moveCamera(position: MapCoordLatLng, zoomLevel:Double) {

    }*/

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }
}