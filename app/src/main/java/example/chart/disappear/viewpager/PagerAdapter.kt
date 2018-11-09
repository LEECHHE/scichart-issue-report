package example.chart.disappear.viewpager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import example.chart.disappear.FirstPagedFragment
import example.chart.disappear.SecondPagedFragment

class PagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

  override fun getItem(position: Int): Fragment {
    return when(position) {
      0 -> FirstPagedFragment()
      1 -> SecondPagedFragment()
      else -> throw IllegalArgumentException()
    }
  }

  override fun getCount(): Int = 2
}