package com.example.whattodo.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.whattodo.R
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
        binding.courseTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mypageFragment_to_courseFragment)
        }
        binding.placeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_mypageFragment_to_placeFragment)
        }
    }
}