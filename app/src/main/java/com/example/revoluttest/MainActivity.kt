package com.example.revoluttest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.example.revoluttest.ui.main.ConverterFragment
import com.example.revoluttest.ui.main.ratesFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar : Toolbar

    private lateinit var viewPager : ViewPager

    private lateinit var  tabLayout : TabLayout

    private lateinit var ratesFragment : ratesFragment

    private lateinit var converterFragment : ConverterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
    }

}
