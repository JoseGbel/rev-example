package com.example.revoluttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.revoluttest.ui.main.ConverterFragment
import com.example.revoluttest.ui.main.RatesFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar : Toolbar

    private lateinit var viewPager : ViewPager

    private lateinit var  tabLayout : TabLayout

    private lateinit var ratesFragment : RatesFragment

    private lateinit var converterFragment : ConverterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        tabLayout = findViewById(R.id.tab_layout)
        viewPager = findViewById(R.id.view_pager)

        ratesFragment = RatesFragment()
        converterFragment = ConverterFragment()

        tabLayout.setupWithViewPager(viewPager)

        val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager, 0)
        viewPagerAdapter.addFragment(ratesFragment, getString(R.string.rates))
        viewPagerAdapter.addFragment(converterFragment, getString(R.string.converter))
        viewPager.adapter = viewPagerAdapter
    }

    private class ViewPagerAdapter(fm: FragmentManager, behavior: Int
    ) : FragmentPagerAdapter(fm, behavior) {

        val fragments : MutableList<Fragment> = mutableListOf()
        val fragmentTitles : MutableList<String> = mutableListOf()

        override fun getItem(position: Int): Fragment {
            return fragments[position]
        }

        override fun getCount(): Int {
            return fragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return fragmentTitles[position]
        }

        fun addFragment(fragment : Fragment, title : String){
            fragments.add(fragment)
            fragmentTitles.add(title)
        }
    }

}
