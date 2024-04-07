package com.uteev.clevercalc

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.uteev.clevercalc.screens.circle.CircleFragment
import com.uteev.clevercalc.screens.prime.PrimeFragment
import com.uteev.clevercalc.screens.therm.ThermFragment

class ViewPagerAdapter(fragmentActivity : FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
       return when(position) {
           0 -> CircleFragment()
           1 -> PrimeFragment()
           2 -> ThermFragment()
           else -> CircleFragment()
       }
    }
}