package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter.FavoriteAdapter
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.FavoriteStationViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.SpaceStationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_favorite_stations.*
import javax.inject.Inject
import javax.inject.Singleton

@AndroidEntryPoint
class FavoriteStations : Fragment() {

   private val viewModel : FavoriteStationViewModel by viewModels()
   private  val favoriteAdapter = FavoriteAdapter(arrayListOf(),this)

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {
        return inflater.inflate(R.layout.fragment_favorite_stations, container, false)
    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.refreshData()

        favoriteList.layoutManager= LinearLayoutManager(context)
        favoriteList.adapter=favoriteAdapter

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.favoriteStations.observe(viewLifecycleOwner, Observer { stations ->
            stations?.let{
                favoriteList.visibility = View.VISIBLE
                favoriteAdapter.updateFavoriteList(stations)
            }

        })

    }


}