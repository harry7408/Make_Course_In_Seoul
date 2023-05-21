package com.example.whattodo.ThirdFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityFriendListBinding

class FriendListActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFriendListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFriendListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "친구목록"
    }



    /* 툴바에서 <- 눌렀을때 이벤트 처리 */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                val builder=AlertDialog.Builder(this)
                builder.run {
                    setTitle("친구추가")
                    setView(R.layout.add_friend_edittext)
                    setPositiveButton("OK") {_,_ ->
                        /* 서버와 통신이 들어가는 부분 (친구추가) */

                    }
                    setNegativeButton("Cancel") {dialog,_ ->
                        dialog.dismiss()
                    }
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.friend_menu,menu)
        return true
    }
}