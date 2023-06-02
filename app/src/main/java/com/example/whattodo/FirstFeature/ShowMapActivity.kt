package com.example.whattodo.FirstFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityShowMapBinding
import com.example.whattodo.datas.Coordinate
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapPoint.mapPointWithGeoCoord

private const val TAG = "ShowMapActivity"

class ShowMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShowMapBinding

    private var mapFlag = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val coordinate = intent.getParcelableExtra<Coordinate>("storePosition")

        /* 이전 프래그먼트에서 누른 카테고리 이름 */

        /* 지도의 처음 위치 설정 */
        binding.mapView.setMapCenterPoint(
            MapPoint.mapPointWithGeoCoord(
                coordinate!!.y, coordinate!!.x
            ), true
        )
        binding.mapView.setZoomLevel(2, true)
        val marker=MapPOIItem()
        marker.apply {
            mapPoint= mapPointWithGeoCoord(coordinate!!.y,coordinate!!.x)
            markerType=MapPOIItem.MarkerType.CustomImage
            customImageResourceId= R.drawable.else_marker
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
