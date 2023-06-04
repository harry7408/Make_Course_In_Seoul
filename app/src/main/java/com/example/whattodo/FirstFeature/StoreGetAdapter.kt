package com.example.whattodo.FirstFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whattodo.R
import com.example.whattodo.databinding.ItemGetBinding
import com.example.whattodo.datas.Store
import net.daum.mf.map.api.MapPoint

class StoreGetAdapter(private val onClick: (Store)->Unit):ListAdapter<Store,StoreGetAdapter.MapGetViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MapGetViewHolder {
        return MapGetViewHolder(
            ItemGetBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false)
        )
    }

    override fun onBindViewHolder(holder: MapGetViewHolder, position: Int) {
        val store=currentList[position]
        if (store.imgUrl.isNullOrEmpty()) {
            holder.binding.storeImageView.setImageResource(R.drawable.no_images)
        } else {
            Glide.with(holder.itemView.context).load("http:${store.imgUrl}")
                .into(holder.binding.storeImageView)
        }
        holder.bind(store)
    }



    inner class MapGetViewHolder(val binding:ItemGetBinding) :RecyclerView.ViewHolder(binding.root) {
            fun bind(store: Store) {
                binding.storeNameTextView.text=store.placeName
                binding.storeAddressTextView.text=store.addressName
                binding.ReviewTextView.text="${store.avgRating.toString()}"
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
