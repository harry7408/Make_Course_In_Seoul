package com.example.whattodo.SecondFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityRequestFriendBinding
import com.example.whattodo.datas.Friend

class RequestFriendActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRequestFriendBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityRequestFriendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "친구목록"

        val intentResponse=intent.getParcelableArrayExtra("friendList") as Array<Friend>


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
}