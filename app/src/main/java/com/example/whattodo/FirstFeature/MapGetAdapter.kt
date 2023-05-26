package com.example.whattodo.FirstFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.databinding.ItemCourseBinding
import com.example.whattodo.databinding.ItemGetBinding
import com.example.whattodo.datas.Store
import net.daum.android.map.coord.MapCoord
import net.daum.android.map.coord.MapCoordLatLng

class MapGetAdapter(private val onClick: (MapCoord)->Unit):RecyclerView.Adapter<MapGetAdapter.MapGetViewHolder>() {
    private var dataSet= emptyList<Store>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapGetViewHolder {
        return MapGetViewHolder(
            ItemGetBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: MapGetViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class MapGetViewHolder(private val binding: ItemGetBinding) :RecyclerView.ViewHolder(binding.root) {
            fun bind(store: Store) {
                binding.storeNameTextView.text=store.placeName
                binding.storeAddressTextView.text=store.addressName
                binding.storePhoneTextView.text=store.phone

                binding.root.setOnClickListener {
                    onClick(MapCoord(store.x.toDouble(),store.y.toDouble()))
                }
            }
    }
    fun setData(dataSet:List<Store>) {
        this.dataSet=dataSet
        notifyDataSetChanged()
    }
}
