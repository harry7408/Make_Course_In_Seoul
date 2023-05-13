package com.example.whattodo.SecondFeature

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.databinding.ItemCourseBinding

class CourseListAdapter():RecyclerView.Adapter<CourseListAdapter.CourseListViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseListViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: CourseListViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }






    inner class CourseListViewHolder(binding: ItemCourseBinding):RecyclerView.ViewHolder(binding.root)
}