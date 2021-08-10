package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter


import androidx.fragment.app.*
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view.FavoriteStations
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view.SpaceStations


class ViewPagerAdapter( fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    val items = arrayOf(
        SpaceStations(),
        FavoriteStations()
    )

    override fun getItemCount(): Int =items.size
    override fun createFragment(position: Int): Fragment=items[position]

}