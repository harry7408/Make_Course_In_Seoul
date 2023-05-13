package com.example.whattodo.FirstFeature

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


/* main 기능이 있는 페이지에서 ViewPager를 위한 프래그먼트 */
class MyMainAdapter(activity:FragmentActivity) : FragmentStateAdapter(activity) {
    val fragments :ArrayList<Fragment> = ArrayList()
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