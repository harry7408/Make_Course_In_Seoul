package com.example.whattodo.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentCourseBinding
import com.example.whattodo.makeCourse.MakeCourseActivity


class CourseFragment : Fragment() {
    private lateinit var binding: FragmentCourseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        val intent = Intent(activity, MakeCourseActivity::class.java)
        binding.cardView1.setOnClickListener {
            intent.putExtra("meal",true)
            intent.putExtra("place",true)
            startActivity(intent)
        }
        binding.cardView2.setOnClickListener {
            intent.putExtra("meal",true)
            intent.putExtra("place",false)
            startActivity(intent)
        }
        binding.cardView3.setOnClickListener {
            intent.putExtra("meal",false)
            intent.putExtra("place",true)
            startActivity(intent)
        }
        binding.cardView4.setOnClickListener {
            intent.putExtra("meal",false)
            intent.putExtra("place",false)
            startActivity(intent)
        }
    }
}