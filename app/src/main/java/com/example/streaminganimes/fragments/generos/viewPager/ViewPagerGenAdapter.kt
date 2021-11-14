package com.example.streaminganimes.fragments.generos.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.streaminganimes.fragments.CatalogoFragment
import com.example.streaminganimes.fragments.generos.GenerosListaFragment

class ViewPagerGenAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return CatalogoFragment()
        }
        return GenerosListaFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}