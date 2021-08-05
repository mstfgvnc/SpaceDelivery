package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter.ViewPagerAdapter
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.FavoriteStationViewModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.SpaceStationsViewModel
import kotlinx.android.synthetic.main.fragment_space_port.*


class SpacePort : Fragment() {

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var viewModelSpaceStations : SpaceStationsViewModel
    private lateinit var viewModelFavoriteStations : FavoriteStationViewModel
    var eusValue = 0
    var dsValue = 0
    var ugsValue = 0
    var shipName =""

    val tabArray = arrayOf(
        "İstasyonlar",
        "Favoriler"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_space_port, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            eusValue=SpacePortArgs.fromBundle(it).eusValue
            dsValue=SpacePortArgs.fromBundle(it).dsValue
            ugsValue=SpacePortArgs.fromBundle(it).ugsValue
            shipName=SpacePortArgs.fromBundle(it).shipName
        }

        pagerAdapter = ViewPagerAdapter(requireActivity())
        view_pager.adapter = pagerAdapter

        TabLayoutMediator(tab_layout,view_pager){tab, position ->
            tab.text = tabArray[position]
        }.attach()

        var count1 =true;
        var count2 =true;

        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {

                super.onPageSelected(position)

                if(position==0){
                    viewModelSpaceStations = ViewModelProvider(pagerAdapter.createFragment(position)).get(SpaceStationsViewModel::class.java)
                    if(count1){
                        count1=false
                        viewModelSpaceStations.ugsValue.value=ugsValue*10000
                        viewModelSpaceStations.eusValue.value=eusValue.toDouble()*20
                        viewModelSpaceStations.dsValue.value=dsValue*10000
                        viewModelSpaceStations.currentDamageTime.value= viewModelSpaceStations.dsValue.value?.div(1000)?.minus(1)
                        viewModelSpaceStations.shipName.value=shipName
                        viewModelSpaceStations.refreshData()
                        viewModelSpaceStations.downTimer()

                    }else {

                        viewModelSpaceStations.refreshData()

                    }
                }else if(position==1){
                    if(count2){
                        count2=false
                    }else {
                        viewModelFavoriteStations = ViewModelProvider(pagerAdapter.createFragment(position)).get(FavoriteStationViewModel::class.java)
                        viewModelFavoriteStations.refreshData()

                    }

                }

            }
        })



    }

}