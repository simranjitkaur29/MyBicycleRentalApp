package com.example.bicyclerentalapp

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 3  // ONGOING, PREVIOUS, CANCELLED

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnGoingFragment()
            1 -> PreviousFragment()
            2 -> CancelledFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}

