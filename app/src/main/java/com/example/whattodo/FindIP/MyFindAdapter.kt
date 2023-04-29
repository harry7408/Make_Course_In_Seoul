package com.example.whattodo.FindIP

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

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