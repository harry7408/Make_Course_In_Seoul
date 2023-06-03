package com.example.whattodo.ThirdFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.R
import com.example.whattodo.databinding.ItemFriendBinding
import com.example.whattodo.datas.Friend
import com.example.whattodo.datas.User


class FriendListAdapter(
    private val imageViewClick: (Friend) -> Unit,
) : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {

    private var friends = mutableListOf<Friend>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        return FriendListViewHolder(
            ItemFriendBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.bind(friends[position])
    }

    override fun getItemCount(): Int {
        return friends.size
    }


    inner class FriendListViewHolder(val binding: ItemFriendBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(friend: Friend) {
            if (friend.userGender == "남성") {
                binding.friendImageView.setImageResource(R.drawable.male)
            } else {
                binding.friendImageView.setImageResource(R.drawable.female)
            }
            binding.friendNameTextView.text = friend.userName
            binding.friendCodeTextView.text = friend.userCode

            binding.friendDeleteImageView.setOnClickListener {
                imageViewClick(friend)
            }
        }
    }

    fun setData(dataSet: MutableList<Friend>) {
        this.friends = dataSet
        notifyDataSetChanged()
    }
}