package com.example.whattodo.Search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentFindPassBinding

class FindPassFragment : Fragment() {
    private lateinit var binding:FragmentFindPassBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_find_pass,container,false)
        binding.findId.setOnClickListener {
            it.findNavController().navigate(R.id.action_findPassFragment_to_findIdFragment)
        }
        return binding.root
    }
}