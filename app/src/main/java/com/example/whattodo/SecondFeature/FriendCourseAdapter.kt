package com.example.whattodo.SecondFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.FirstFeature.StoreGetAdapter.Companion.diffUtil
import com.example.whattodo.R
import com.example.whattodo.databinding.ItemCourseFriendBinding
import com.example.whattodo.databinding.ItemFriendBinding
import com.example.whattodo.datas.Friend

class FriendCourseAdapter :ListAdapter<Friend,FriendCourseAdapter.FriendCourseViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendCourseViewHolder {
        return FriendCourseViewHolder(ItemCourseFriendBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ))
    }

    override fun onBindViewHolder(holder: FriendCourseViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    inner class FriendCourseViewHolder(val binding: ItemCourseFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: Friend) {
            if (friend.userGender == "남성") {
                binding.friendImageView.setImageResource(R.drawable.male)
            } else {
                binding.friendImageView.setImageResource(R.drawable.female)
            }
            binding.friendNameTextView.text = friend.userName
            binding.friendCodeTextView.text = friend.userCode

            binding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            }
        }
    }

    companion object {
        val diffUtil=object :DiffUtil.ItemCallback<Friend>() {
            override fun areItemsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                return oldItem.userCode==newItem.userCode
            }

            override fun areContentsTheSame(oldItem: Friend, newItem: Friend): Boolean {
                return oldItem==newItem
            }

        }
    }


}