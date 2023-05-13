package com.example.whattodo.FindingIdPass

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/* 프래그먼트 담을 어뎁터 (ViewPager를 위해) */
class MyFindAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
    private var fragments : ArrayList<Fragment> = ArrayList()
    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
      return fragments[position]
    }
    fun addFragment(fragment:Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size-1)
    }
}