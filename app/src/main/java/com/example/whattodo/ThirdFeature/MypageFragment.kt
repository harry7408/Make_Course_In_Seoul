package com.example.whattodo.ThirdFeature

import android.app.ProgressDialog.show
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.viewpager2.widget.ViewPager2
import com.example.whattodo.*
import com.example.whattodo.FirstFeature.MainInfoActivity
import com.example.whattodo.databinding.FragmentMypageBinding
import com.example.whattodo.datas.User
import com.example.whattodo.db.UserDatabase
import com.example.whattodo.network.RetrofitAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MypageFragment"

class MypageFragment : Fragment() {
    private lateinit var binding: FragmentMypageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*회원 이름 가져오기*/
        val sharedPreferences =
            requireActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        val currentUserName = sharedPreferences.getString(ID, null).toString()
        binding.userId.text = currentUserName
        val currentUserEmail = sharedPreferences.getString(EMAIL, null).toString()
        val currentUserGender = sharedPreferences.getString(GENDER, null).toString()

        if (sharedPreferences.getString(GENDER, null).toString() == "남성") {
            binding.userImageView.setImageResource(R.drawable.male)
        } else if (sharedPreferences.getString(GENDER, null).toString() == "여성") {
            binding.userImageView.setImageResource(R.drawable.female)
        } else {
            binding.userImageView.setImageResource(R.drawable.human)
        }



        binding.userInfoChangeLayer.setOnClickListener {
            val intent = Intent(context, ChangeUserInfoActivity::class.java)
            intent.putExtra("username", currentUserName)
            intent.putExtra("userEmail", currentUserEmail)
            intent.putExtra("userGender", currentUserGender)
            startActivity(intent)
        }

        binding.friendListCardView.setOnClickListener {
            /* 여기서 서버와 통신 후 넘어오는 데이터 받아서 넘겨야 한다 */
            val intent = Intent(context, FriendListActivity::class.java)
            startActivity(intent)
        }

        binding.historyCardView.setOnClickListener {
            /* 서버랑 통신해서 저장된 히스토리를 다음으로 넘겨서 리사이클러뷰로 띄워야할 듯?*/
        }

        binding.deleteCardView.setOnClickListener {
            /* 서버와 회원탈퇴 기능 맞춰서 해야함 */
            val currentUser = User(
                sharedPreferences.getString(UID, null),
                sharedPreferences.getString(ID, null),
                sharedPreferences.getString(PASS, null),
                sharedPreferences.getString(NAME, null),
                null,
                sharedPreferences.getString(EMAIL, null),
                sharedPreferences.getString(BDAY, null),
                sharedPreferences.getString(GENDER, null),
                sharedPreferences.getFloat(FATIGUE, 0.0f).toDouble(),
                sharedPreferences.getFloat(EXOTIC, 0.0f).toDouble(),
                sharedPreferences.getFloat(ACTIVITY, 0.0f).toDouble(),
            )

            /* 회원 탈퇴 기능*/
            AlertDialog.Builder(requireActivity()).run {
                setMessage("정말로 탈퇴 하시겠습니까?")
                setNegativeButton("Cancel", null)
                setPositiveButton(
                    R.string.ok,
                    DialogInterface.OnClickListener { _, _ ->
                        val withDrawCall = RetrofitAPI.deleteService.delete(currentUser).enqueue(
                            object : Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    if (response.isSuccessful) {
                                        Thread {
                                            UserDatabase.getInstance(requireActivity())?.memberDao()
                                                ?.delete(currentUser.memberId.toString())
                                        }.start()
                                        val editor = sharedPreferences.edit()
                                        editor.clear().commit()
                                        val intent =
                                            Intent(requireActivity(), MainActivity::class.java)
                                        intent.flags =
                                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        startActivity(intent)
                                    } else {
                                        Log.d(TAG, "umm")
                                    }
                                }

                                override fun onFailure(call: Call<String>, t: Throwable) {
                                    Log.e(TAG, "ERROR OCCUR")
                                    t.printStackTrace()
                                }
                            }
                        )
                    })
                create()
                show()
            }
        }

        /* 로그아웃 부분 */
        binding.logoutText.setOnClickListener {
            val intent = Intent(activity, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            val editor = sharedPreferences.edit()
            editor.clear().apply()
            startActivity(intent)
        }
    }
}


