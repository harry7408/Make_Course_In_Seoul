package com.example.whattodo.SecondFeature

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.viewpager2.widget.ViewPager2
import com.example.whattodo.R
import com.example.whattodo.databinding.FragmentCourseBinding


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
        /* fragment에서 뒤로가기 눌렀을 때 home으로 이동 */
        val onBackPressedCallback=object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val viewPager=requireActivity().findViewById<ViewPager2>(R.id.viewpager)
                viewPager.setCurrentItem(0,true)

            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,onBackPressedCallback
        )

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