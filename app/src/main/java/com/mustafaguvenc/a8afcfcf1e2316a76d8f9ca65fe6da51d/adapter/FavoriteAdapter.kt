package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.databinding.ItemFavoriteListBinding
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.databinding.ItemStationListBinding
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util.CustomSharedPreferences
import kotlinx.android.synthetic.main.item_favorite_list.view.*
import java.text.DecimalFormat

class FavoriteAdapter(val favoriteList : ArrayList<StationModel>):
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private var customPreferences = CustomSharedPreferences()
    class FavoriteViewHolder(var view :ItemFavoriteListBinding) : RecyclerView.ViewHolder(view.root) {

    }


    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
      //  val view =inflater.inflate(R.layout.item_favorite_list,parent,false)
        val view = DataBindingUtil.inflate<ItemFavoriteListBinding>(inflater,R.layout.item_favorite_list,parent,false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder : FavoriteViewHolder, position : Int) {

        val realPosition = customPreferences.getFavoritePosition()
        val realPositionList = ArrayList<Int>()
        for(i in realPosition){
            realPositionList.add(i)
        }
        realPositionList.sort()
        val mapRealPosition = hashMapOf<Int,Int>()
        mapRealPosition.put(position,realPositionList[position])


        holder.view.stationFv = favoriteList[position]
        holder.view.eusForFavorite.text= DecimalFormat("##.##").format(distanceCalculate(favoriteList[position].coordinateX!!,favoriteList[position].coordinateY!!)).toString()

        holder.view.favoriteInButtonFv.setOnClickListener {
          //  it.visibility=View.GONE
            val favoriteHashSetStaion = customPreferences.getFavoritePosition()
            favoriteHashSetStaion.remove(mapRealPosition[position])
            customPreferences.saveFavoritePosition(favoriteHashSetStaion)
         //   holder.itemView.visibility=View.GONE
            val listFavorite = ArrayList<StationModel>()
            for(i in 0..favoriteList.size-1){
                if(i!=position){
                    listFavorite.add(favoriteList[i])
                }
            }
            updateFavoriteList(listFavorite)
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