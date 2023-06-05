package com.example.whattodo.SecondFeature

import android.app.Activity
import android.content.Context
import android.media.Image
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whattodo.R
import com.example.whattodo.databinding.ItemCourseBinding
import com.example.whattodo.datas.Store
import net.daum.mf.map.api.MapPoint
import org.osgeo.proj4j.CRSFactory
import kotlin.coroutines.coroutineContext

private const val TAG = "CourseListAdapter"

class CourseListAdapter(private val itemClickListener: (MapPoint.GeoCoordinate) -> Unit) :
    RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {

    private var stores = emptyList<Store>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemCourseBinding.inflate(inflater, parent, false)
        return CourseListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        val store = stores[position]

        Log.e(TAG, "$store")
        if (store.imgUrl.isNullOrEmpty()) {
            holder.binding.storeImageView.setImageResource(R.drawable.no_images)
        } else {
            Glide.with(holder.itemView.context).load("http:${store.imgUrl}")
                .into(holder.binding.storeImageView)
        }
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
            binding.storeCategoryNameTextView.text = store.categoryName
            binding.storeAddressTextView.text = store.addressName
            if (store.phone.isNullOrEmpty()) {
                binding.storePhoneTextView.visibility = TextView.GONE
                binding.phoneImageView.visibility = ImageView.GONE
            } else {
                binding.storePhoneTextView.text = store.phone
            }

            binding.avgRatingTextView.text = "${store.avgRating} (${store.ratingNum})"
            binding.reviewNumTextView.text = "리뷰 (${store.reviewNum})"

            if (store.tags.isNullOrEmpty()) {
                binding.storeTagsTextView.visibility = TextView.GONE
                binding.tagImageView.visibility = ImageView.GONE
            } else {
                binding.storeTagsTextView.text = store.tags
            }

            if (store.introduction.isNullOrEmpty()) {
                binding.storeIntroTextView.visibility = TextView.GONE
                binding.chiefImageView.visibility = ImageView.GONE
            } else {
                binding.storeIntroTextView.text = "사장님 한마디 : ${store.introduction}"
            }

            if (store.menu.isNullOrEmpty()) {
                binding.storeMenuTextView.visibility = TextView.GONE
                binding.menuImageView.visibility=ImageView.GONE
            } else {
                binding.storeMenuTextView.text = store.menu
            }
            if (store.time.isNullOrEmpty()) {
                binding.storeOpenTextView.visibility=TextView.GONE
                binding.timeImageView.visibility=ImageView.GONE
            } else {
                binding.storeOpenTextView.text=store.time
            }



            binding.root.setOnClickListener {
                Log.e(TAG, "${store.y}, ${store.x}")
                itemClickListener(MapPoint.GeoCoordinate(store.y, store.x))
            }
        }
    }
}

