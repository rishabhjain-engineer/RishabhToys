package com.example.rishabhtoys

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class HomePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {


    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return BuyerFragment()
            }
            1 -> {
                return VendorFragment()
            }
        }

        return BuyerFragment()
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return Purchase.toUpperCase()
            1 -> return Sale.toUpperCase()
        }
        return super.getPageTitle(position)

    }
}