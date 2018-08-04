package example.duyal.mete.tablayoutinterpolation

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return EmptyFragment.newInstance("tabExample $position")

    }

    override fun getCount(): Int {
        return 15
    }
}