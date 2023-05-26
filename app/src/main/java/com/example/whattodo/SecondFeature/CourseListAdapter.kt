package com.example.whattodo.SecondFeature

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.FirstFeature.PlaceFragmentAdapter
import com.example.whattodo.databinding.ItemCategoryBinding
import com.example.whattodo.databinding.ItemCourseBinding
import com.example.whattodo.datas.Coordinate
import com.example.whattodo.datas.Store
import net.daum.android.map.coord.MapCoord
import org.osgeo.proj4j.BasicCoordinateTransform
import org.osgeo.proj4j.CoordinateReferenceSystem
import org.osgeo.proj4j.ProjCoordinate
import org.osgeo.proj4j.Proj4jException
import org.osgeo.proj4j.CoordinateTransform
import org.osgeo.proj4j.CoordinateTransformFactory
import org.osgeo.proj4j.util.ProjectionUtil
import org.osgeo.proj4j.CRSFactory

private const val TAG="CourseListAdapter"
class CourseListAdapter(val onClick:(MapCoord)->Unit) :
    RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {

    private var stores= emptyList<Store>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemCourseBinding.inflate(inflater, parent, false)
        return CourseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        val store=stores[position]
        holder.bind(store)
    }

    override fun getItemCount(): Int {
       return stores.size
    }


    fun setData(data:List<Store>) {
        this.stores=data
        notifyDataSetChanged()
    }

    inner class CourseListViewHolder(val binding: ItemCourseBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(store : Store) {
            binding.storeNameTextView.text=store.placeName
            binding.storeAddressTextView.text=store.addressName
            binding.storeImageView.setImageURI(store.imgUrl?.toUri())
            binding.storePhoneTextView.text=store.phone
            binding.storeCostTextView.text=store.cost
            binding.root.setOnClickListener {
                Log.d(TAG,"${store.x} : ${store.y}")
                onClick(MapCoord(store.x,store.y))
            }
        }
    }

   /* 좌표 변환계 */
   /* @SuppressLint("SuspiciousIndentation")
    private fun changeXY(store:Store) : Coordinate {
        val kakaox=store.x
        val kakaoy=store.y

        val crsFactory=CRSFactory();
        val wsg84Crs=crsFactory.createFromName("EPSG:4326") as CoordinateReferenceSystem
        val katechCrs=crsFactory.createFromName("EPSG:5178") as CoordinateReferenceSystem

       val transformator=BasicCoordinateTransform(wsg84Crs,katechCrs)

        val wsg84Coord=ProjCoordinate(kakaox,kakaoy)
        val katechCoord=ProjCoordinate()
            transformator.transform(wsg84Coord,katechCoord)

        
        return Coordinate(katechCoord.x,katechCoord.y)
    }*/
}