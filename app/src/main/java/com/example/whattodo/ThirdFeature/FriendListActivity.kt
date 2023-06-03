package com.example.whattodo.ThirdFeature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil.setContentView
import com.example.whattodo.R
import com.example.whattodo.databinding.ActivityFriendListBinding
import com.example.whattodo.datas.User


private const val TAG = "FriendListActivity"

class FriendListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFriendListBinding
    private var friends = mutableListOf<User>()
    /* 어뎁터 선언 부분 */
    /*private val friendAdapter = FriendListAdapter{
      deleteFriend(it)
    }*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFriendListBinding.inflate(layoutInflater)
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
                val builder = AlertDialog.Builder(this)
                builder.run {
                    setTitle("친구추가")
                    setView(R.layout.add_friend_edittext)
                    val friendNameView = findViewById<EditText>(R.id.friendAddEditText)
                    setPositiveButton("OK") { _, _ ->
                        /* 서버와 통신이 들어가는 부분 (친구추가) */
//                        val addFriendCall = RetrofitAPI.addFriendService
//                            .addFriend(friendNameView.toString()).enqueue(object : Callback<User> {
//                                override fun onResponse(
//                                    call: Call<User>,
//                                    response: Response<User>
//                                ) {
//                                    if (response.isSuccessful) {
//                                        /*어뎁터 연결? 부분 */
//                                        val responseData = response.body()
//                                        if (responseData != null) friends.add(responseData)
//                                        else Toast.makeText(applicationContext,"아이디를 다시 입력해주세요",Toast.LENGTH_SHORT).show()
//
//                                        friendAdapter.setData(friends)
//                                        binding.friendListRecyclerView.apply {
//                                            adapter=friendAdapter
//                                            layoutManager=LinearLayoutManager(applicationContext,LinearLayoutManager.VERTICAL,false)
//                                            val dividerItemDecoration =
//                                                DividerItemDecoration(applicationContext,LinearLayoutManager.VERTICAL)
//                                            addItemDecoration(dividerItemDecoration)
//                                        }
//
//
//                                    } else {
//                                        Log.e(TAG, "NULL값 넘어옴")
//                                    }
//                                }
//
//                                override fun onFailure(call: Call<User>, t: Throwable) {
//                                    Log.e(TAG, "통신 실패")
//                                    t.printStackTrace()
//                                }
//
//                            })
                    }
                    setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                }.show()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.friend_menu, menu)
        return true
    }

    private fun deleteFriend(user: User) {

    }

}

/* 친구 삭제는 onItemClick 오버라이드 받아서 진행해야할듯*/