package com.example.whattodo.SecondFeature

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.databinding.ItemCourseBinding
import com.example.whattodo.datas.Store
import net.daum.mf.map.api.MapPoint
import org.osgeo.proj4j.CRSFactory

private const val TAG="CourseListAdapter"
class CourseListAdapter(private val itemClickListener: (MapPoint.GeoCoordinate) -> Unit) :
    RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {

    private var stores= emptyList<Store>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemCourseBinding.inflate(inflater, parent, false)
        return CourseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        val store = stores[position]
        holder.bind(store)
    }

    override fun getItemCount(): Int {
        return stores.size
    }


    fun setData(data: List<Store>) {
        this.stores = data
        notifyDataSetChanged()
    }

    inner class CourseListViewHolder(val binding: ItemCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(store: Store) {
            binding.storeNameTextView.text = store.placeName
            binding.storeAddressTextView.text = store.addressName
            binding.storeImageView.setImageURI(store.imgUrl?.toUri())
            binding.storePhoneTextView.text = store.phone
            binding.storeCategoryNameTextView.text=store.categoryName
            binding.avgRatingTextView.text="별점 : ${store.avgRating}(${store.ratingNum})"
            binding.reviewNumTextView.text=store.reviewNum.toString()
            binding.storeTagsTextView.text=store.tags

            binding.root.setOnClickListener {
                Log.e(TAG,"${store.y}, ${store.x}")
                itemClickListener(MapPoint.GeoCoordinate(store.y,store.x))
            }
        }
    }
}

