package com.uteev.clevercalc

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uteev.clevercalc.screens.circle.CircleFragment
import com.uteev.clevercalc.screens.prime.PrimeFragment

class ViewPagerAdapter(fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
       return when(position) {
           0 -> CircleFragment()
           1 -> PrimeFragment()
           else -> CircleFragment()
       }
    }
}