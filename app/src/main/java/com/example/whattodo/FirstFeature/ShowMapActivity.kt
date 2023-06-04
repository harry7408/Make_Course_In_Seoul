package com.example.whattodo.FirstFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityShowMapBinding
import com.example.whattodo.datas.Coordinate
import com.example.whattodo.datas.Store
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord

private const val TAG = "ShowMapActivity"

class ShowMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowMapBinding

    private var mapFlag = false
    private var marker=MapPOIItem()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val coordinate = intent.getParcelableExtra<Coordinate>("storePosition")
         Log.e(TAG,"$coordinate")

        val storeName=intent.getStringExtra("storeName")


        binding.mapView.apply {
            this.setZoomLevel(2,true)
            this.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(coordinate!!.y,coordinate!!.x),true)
        }

        marker.apply {
            /* 마커에 이름이 없으면 마커가 찍히지 않는다 */
            itemName=storeName.toString()
            mapPoint= mapPointWithGeoCoord(coordinate!!.y,coordinate!!.x)
            markerType=MapPOIItem.MarkerType.CustomImage
            customImageResourceId= R.drawable.else_marker
            isCustomImageAutoscale=true
            setCustomImageAnchor(0.5f,0.5f)
        }
        binding.mapView.addPOIItem(marker)
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






