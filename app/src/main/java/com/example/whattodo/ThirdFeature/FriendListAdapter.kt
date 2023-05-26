package com.example.whattodo.ThirdFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.R
import com.example.whattodo.databinding.ItemFriendBinding
import com.example.whattodo.datas.User


class FriendListAdapter:RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {

    private var friends= mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
      return FriendListViewHolder(
          ItemFriendBinding.inflate(LayoutInflater.from(parent.context),parent,false)
      )
    }

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
       holder.bind(friends[position])
    }

    override fun getItemCount(): Int {
      return friends.size
    }


    inner class FriendListViewHolder(val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : User) {
            if (user.gender.equals("남성")) {
                binding.friendImageView.setImageResource(R.drawable.male)
            } else {
                binding.friendImageView.setImageResource(R.drawable.female)
            }
            binding.textView.text=user.memberName
        }

    }

    fun setData(dataSet: MutableList<User>) {
        this.friends= dataSet
        notifyDataSetChanged()
    }
}