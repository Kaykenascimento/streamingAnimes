package com.example.streaminganimes.fragments.generos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.streaminganimes.R
import com.example.streaminganimes.fragments.generos.viewPager.ViewPagerGenAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_favoritos.*

class GenerosFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_generos, container, false)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPagerCatalogo)
        val tbLayout = view.findViewById<TabLayout>(R.id.tbLayoutCatalogo)

        val fragmentManager = fragmentManager

        val adapter = ViewPagerGenAdapter(fragmentManager!!, lifecycle)
        viewPager.adapter = adapter

        tbLayout.addTab(tbLayout.newTab().setText("Gêneros"))
        tbLayout.addTab(tbLayout.newTab().setText("Catálogo"))

        tbLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            @Override
            override fun onPageSelected(position: Int) {
                tbLayout.selectTab(tbLayout.getTabAt(position))
            }
        })
        return view
    }
}