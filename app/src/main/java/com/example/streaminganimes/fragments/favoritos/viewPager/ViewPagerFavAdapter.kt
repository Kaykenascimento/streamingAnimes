package com.example.streaminganimes.fragments.favoritos.viewPager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.streaminganimes.fragments.favoritos.FavoritosFragment
import com.example.streaminganimes.fragments.favoritos.FavoritosListaFragment
import com.example.streaminganimes.fragments.historico.HistoricoFragment

class ViewPagerFavAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            1 -> return HistoricoFragment()
        }
        return FavoritosListaFragment()
    }

    override fun getItemCount(): Int {
        return 2
    }
}