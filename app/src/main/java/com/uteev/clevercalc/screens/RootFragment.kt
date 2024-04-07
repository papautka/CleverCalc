package com.uteev.clevercalc.screens

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.uteev.clevercalc.R
import com.uteev.clevercalc.ViewPagerAdapter

class RootFragment : Fragment() {

    private var ctx : Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_root, container, false)
        val viewPager = view.findViewById<ViewPager2>(R.id.view_pager)
        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        viewPager.adapter = ViewPagerAdapter(ctx as FragmentActivity)
        tabLayout.tabIconTint = null

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.circle_draw)
                }

                1 -> {
                    tab.setIcon(R.drawable.prime_draw)
                }
                2 -> {
                    tab.setIcon(R.drawable.therm_draw)
                }
            }
        }.attach()
        return view
    }
}