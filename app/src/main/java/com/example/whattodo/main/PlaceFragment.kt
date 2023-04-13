package com.example.whattodo.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentPlaceBinding


class PlaceFragment : Fragment() {
    private lateinit var binding : FragmentPlaceBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_place,container,false)
        binding.courseTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_placeFragment_to_courseFragment)
        }
        binding.myPageTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_placeFragment_to_mypageFragment)
        }

        return binding.root
    }
}