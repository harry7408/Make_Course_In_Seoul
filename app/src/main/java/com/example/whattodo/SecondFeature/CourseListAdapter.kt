package com.example.whattodo.SecondFeature

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.FirstFeature.PlaceFragmentAdapter
import com.example.whattodo.databinding.ItemCategoryBinding
import com.example.whattodo.databinding.ItemCourseBinding
import com.example.whattodo.datas.Store



class CourseListAdapter(val stores: List<Store>):RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {
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






    inner class CourseListViewHolder(val binding: ItemCourseBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(store : Store) {
            binding.storeNameTextView.text=store.placeName
            binding.storeAddressTextView.text=store.addressName
            binding.storeImageView.setImageURI(store.imgUrl?.toUri())
            binding.storePhoneTextView.text=store.phone
            binding.storeCostTextView.text=store.cost
        }
    }
}