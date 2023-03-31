package com.android.zjctools.adapter

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ZFragmentPagerAdapter (fm:FragmentManager, fragments:MutableList<Fragment>, titles:MutableList<String>): FragmentPagerAdapter(fm) {

    private var mFragments=fragments
    private var mTitles=titles

    override fun getCount(): Int {
      return  mFragments.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitles[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
    }
}