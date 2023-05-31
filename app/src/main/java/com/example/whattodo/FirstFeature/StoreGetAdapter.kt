package com.example.whattodo.FirstFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.databinding.ItemGetBinding
import com.example.whattodo.datas.Store
import net.daum.mf.map.api.MapPoint

class StoreGetAdapter(private val onClick: (Store)->Unit):ListAdapter<Store,StoreGetAdapter.MapGetViewHolder>(diffUtil) {
    private var dataSet= emptyList<Store>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapGetViewHolder {
        return MapGetViewHolder(
            ItemGetBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: MapGetViewHolder, position: Int) {
        val store=currentList[position]
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    inner class MapGetViewHolder(private val binding:ItemGetBinding) :RecyclerView.ViewHolder(binding.root) {
            fun bind(store: Store) {
                binding.storeNameTextView.text=store.placeName
                binding.storeAddressTextView.text=store.addressName
                binding.storePhoneTextView.text=store.phone
                binding.root.setOnClickListener {
                    onClick(store)
                }
            }
    }
    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<Store>() {
            override fun areItemsTheSame(oldItem: Store, newItem: Store): Boolean {
                return oldItem.id==newItem.id
            }

            override fun areContentsTheSame(oldItem: Store, newItem: Store): Boolean {
                return oldItem==newItem
            }

        }
    }
}
