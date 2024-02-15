package com.example.onlinestoretest.presentation.main.favorites

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class FavoritesViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> FavoriteProductsFragment()
        else -> FavoriteBrandsFragment()
    }
}