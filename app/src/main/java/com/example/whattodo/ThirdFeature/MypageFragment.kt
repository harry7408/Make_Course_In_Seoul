package com.example.whattodo.ThirdFeature

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whattodo.*
import com.example.whattodo.databinding.FragmentMypageBinding


class MypageFragment : Fragment() {
    private lateinit var binding : FragmentMypageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentMypageBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*회원 이름 가져오기*/
        val sharedPreferences=requireActivity().getSharedPreferences(USER_INFO, Context.MODE_PRIVATE)
        binding.userId.text=sharedPreferences.getString(ID,null).toString()

        binding.logoutText.setOnClickListener {
            val intent= Intent(activity,MainActivity::class.java)
            val editor=sharedPreferences.edit()
            editor.remove(ID)
            editor.remove(PASS)
            startActivity(intent)
        }
    }
}


