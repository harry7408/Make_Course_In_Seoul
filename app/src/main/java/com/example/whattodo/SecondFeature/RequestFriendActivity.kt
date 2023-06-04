package com.example.whattodo.SecondFeature

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.children
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityRequestFriendBinding
import com.example.whattodo.datas.Friend


private const val TAG="RequestFriendActivity"
class RequestFriendActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRequestFriendBinding
    private var friendAdapter=FriendCourseAdapter()
    private lateinit var submitList:ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRequestFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "친구목록"

        val friendResponse=intent.getParcelableArrayListExtra<Friend>("friendList")
        Log.e(TAG,"$friendResponse")


        binding.friendListRecyclerView.apply {
            layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
            adapter=friendAdapter
            friendAdapter.submitList(friendResponse)
            friendAdapter.notifyDataSetChanged()
        }

        binding.finishButton.setOnClickListener {
            actionCheckFinish()
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {}
        }
        return super.onOptionsItemSelected(item)
    }
    private fun actionCheckFinish() {
        submitList=friendAdapter.checkedItemList
        val intent= Intent().putExtra("finish",submitList)
        setResult(1,intent)
        finish()
    }

}