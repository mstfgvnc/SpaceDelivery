package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.databinding.ItemFavoriteListBinding
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.repository.Repository
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.FavoriteStationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject



class FavoriteAdapter @Inject constructor (val favoriteList : ArrayList<StationModel>,
                                           val fragment: Fragment,
                                           private val repository: Repository)
    : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private lateinit var viewModel: FavoriteStationViewModel

    class FavoriteViewHolder(var view :ItemFavoriteListBinding) : RecyclerView.ViewHolder(view.root) {

    }

    lateinit var context:Context
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context=parent.context
        val view = DataBindingUtil.inflate<ItemFavoriteListBinding>(inflater,R.layout.item_favorite_list,parent,false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder : FavoriteViewHolder, position : Int) {

        holder.view.stationFv = favoriteList[position]

        //İstasyonların dünyaya olan uzaklığının hesaplanması
        favoriteList[position].nowEusToEarth=Math.round(distanceCalculate(favoriteList[position].coordinateX!!,favoriteList[position].coordinateY!!)*100)/100.00

        holder.view.favoriteInButtonFv.setOnClickListener {

            // Favorilerden istasyonun çıkarılması ve listenin güncellenmesi
            updateStation(favoriteList.get(position).uuid,favoriteList.get(position))
            favoriteList.removeAt(position)
            val listFavorite = ArrayList<StationModel>(favoriteList)
            updateFavoriteList(listFavorite)

        }

    }

    // Favoriden çıkarılan istasyonun room da güncellenmesi
    private fun updateStation(position: Int,stationModel: StationModel) {

        viewModel=ViewModelProvider(fragment).get(FavoriteStationViewModel::class.java)
        viewModel.launch {

            val editedItem=stationModel
            editedItem.uuid=position
            editedItem.favoriteBool=false

            withContext(Dispatchers.IO) {
                repository.update(editedItem)
            }
        }



    }

    override fun getItemCount() : Int {
        return favoriteList.size
    }

    fun updateFavoriteList(newFavoriteList: List<StationModel >) {

        favoriteList.clear()
        favoriteList.addAll(newFavoriteList)
        notifyDataSetChanged()
    }

    fun distanceCalculate(x : Double,y : Double):Double{
        val distance = (x*x) + (y*y)
        return Math.sqrt(distance)
    }


}