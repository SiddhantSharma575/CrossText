package com.example.crosstext.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.crosstext.fragments.BookMarkFragment
import com.example.crosstext.fragments.DataFragment
import com.example.crosstext.fragments.LinkFragment
import com.example.crosstext.fragments.SettingFragment

class ViewPagerAdapter(fm:FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return DataFragment()
            1 -> return BookMarkFragment()
            2 -> return LinkFragment()
            3 -> return SettingFragment()
        }
        return DataFragment()
    }

}