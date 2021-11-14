package com.example.streaminganimes.fragments.favoritos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.streaminganimes.R
import com.example.streaminganimes.activitys.inicioActivity
import com.example.streaminganimes.fragments.favoritos.viewPager.ViewPagerFavAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_favoritos.*

class FavoritosFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)

        val viewPagerFavorito = view.findViewById<ViewPager2>(R.id.viewPagerFavoritos)
        val tbLayoutFavorito = view.findViewById<TabLayout>(R.id.tbLayoutFavoritos)

        val fragmentManager = fragmentManager

        val adapter = ViewPagerFavAdapter(fragmentManager!!, lifecycle)
        viewPagerFavorito.adapter = adapter

        tbLayoutFavorito.addTab(tbLayoutFavorito.newTab().setText("Favoritos"))
        tbLayoutFavorito.addTab(tbLayoutFavorito.newTab().setText("Histórico"))

        tbLayoutFavorito.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPagerFavoritos.currentItem = tab!!.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        viewPagerFavorito.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            @Override
            override fun onPageSelected(position: Int) {
                tbLayoutFavoritos.selectTab(tbLayoutFavoritos.getTabAt(position))
            }
        })
        return view
    }
}