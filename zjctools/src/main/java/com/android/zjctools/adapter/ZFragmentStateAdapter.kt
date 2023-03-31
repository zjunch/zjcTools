package com.android.zjctools.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class ZFragmentStateAdapter(activity: FragmentActivity, list: List<Fragment>?) : FragmentStateAdapter(activity!!) {
    var fragments=list


    fun getCount(): Int {
        return fragments!!.size
    }

    override fun getItemCount(): Int =      fragments!!.size

    override fun createFragment(position: Int): Fragment {
        return fragments!![position]
    }


}