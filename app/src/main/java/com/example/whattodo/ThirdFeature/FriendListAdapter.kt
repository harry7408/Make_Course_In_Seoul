package com.example.whattodo.ThirdFeature

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.whattodo.R
import com.example.whattodo.databinding.ItemFriendBinding
import com.example.whattodo.datas.User
import com.example.whattodo.entity.Friend


class FriendListAdapter(
    private val checkBoxClick:(User)->Unit,
    private val imageViewClick:(User)->Unit,
) : RecyclerView.Adapter<FriendListAdapter.FriendListViewHolder>() {

    private var friends = mutableListOf<User>()

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
        fun bind(user: User) {
            if (user.gender.equals("남성")) {
                binding.friendImageView.setImageResource(R.drawable.male)
            } else {
                binding.friendImageView.setImageResource(R.drawable.female)
            }
            binding.textView.text = user.memberName

            binding.friendListCheckBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) checkBoxClick(user)
            }

            binding.friendDeleteImageView.setOnClickListener {
                imageViewClick(user)
            }
        }

    }

    fun setData(dataSet: MutableList<User>) {
        this.friends = dataSet
        notifyDataSetChanged()
    }
}