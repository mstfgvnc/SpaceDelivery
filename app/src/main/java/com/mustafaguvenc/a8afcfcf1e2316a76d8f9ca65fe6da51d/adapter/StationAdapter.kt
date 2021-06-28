package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.databinding.ItemStationListBinding
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util.CustomSharedPreferences
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view.SpaceStations
import java.text.DecimalFormat
import java.util.*
import kotlin.collections.ArrayList

class StationAdapter(val stationList : ArrayList<StationModel>):RecyclerView.Adapter<StationAdapter.StationViewHolder>() ,StationClickListener,Filterable{
    private var customPreferences = CustomSharedPreferences()
    val currentX = customPreferences.getCurrentCoordinateX()?.toDouble()
    val currentY = customPreferences.getCurrentCoordinateY()?.toDouble()
    var stationFilterList = ArrayList<StationModel>()
    var stationFilterListFull = ArrayList<StationModel>()
    init {
        stationFilterList=stationList
        stationFilterListFull= stationList
    }


    class StationViewHolder(var view : ItemStationListBinding): RecyclerView.ViewHolder(view.root){

    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : StationViewHolder {
        val inflater =LayoutInflater.from(parent.context)
     //   val view =  inflater.inflate(R.layout.item_station_list,parent,false)
        val view = DataBindingUtil.inflate<ItemStationListBinding>(inflater,R.layout.item_station_list,parent,false)

        return StationViewHolder(view)
    }

    override fun onBindViewHolder(holder : StationViewHolder, position : Int) {
        var realPosition = position
        for(i in 0..stationList.size-1){
            if(stationFilterList[position].name.equals(stationList[i].name)){
                realPosition= i
            }
        }

        holder.view.station= stationFilterList[position]
        holder.view.listener= this
        holder.view.nowEus.text=DecimalFormat("##.##").format(distanceCalculate(stationFilterList[position].coordinateX!!,stationFilterList[position].coordinateY!!)).toString()+"EUS"
        holder.view.capasityStock.text=stationFilterList[position].capacity.toString()+"/"+stationFilterList[position].stock.toString()
        if(customPreferences.getVisitedPosition().contains(realPosition)){
            holder.view.travelButton.setTextColor(Color.WHITE)
            holder.view.travelButton.setBackgroundColor(Color.BLACK)
            holder.view.travelButton.text="İHTİYAÇ YOK"

        }else{
            holder.view.travelButton.setOnClickListener {
                customPreferences.saveCurrentCoordinateX(stationFilterList[position].coordinateX!!)
                customPreferences.saveCurrentCoordinateY(stationFilterList[position].coordinateY!!)
                customPreferences.saveCurrentStationName(stationFilterList[position].name!!)

                val visitedHashSetStaion = customPreferences.getVisitedPosition()
                visitedHashSetStaion.add(realPosition)
                customPreferences.saveVisitedPosition(visitedHashSetStaion)

                holder.view.travelButton.setTextColor(Color.WHITE)
                holder.view.travelButton.setBackgroundColor(Color.BLACK)
                holder.view.travelButton.text="İHTİYAÇ YOK"

            }
        }

        if(customPreferences.getFavoritePosition().contains(realPosition)){
            holder.view.favoriteInButton.visibility=View.VISIBLE
            holder.view.favoriteOutButton.visibility=View.GONE
        }else{
            holder.view.favoriteInButton.visibility=View.GONE
            holder.view.favoriteOutButton.visibility=View.VISIBLE
        }

        holder.view.favoriteInButton.setOnClickListener {
            it.visibility=View.GONE
            holder.view.favoriteOutButton.visibility=View.VISIBLE

            val favoriteHashSetStaion = customPreferences.getFavoritePosition()
            favoriteHashSetStaion.remove(realPosition)
            customPreferences.saveFavoritePosition(favoriteHashSetStaion)


        }
        holder.view.favoriteOutButton.setOnClickListener {
            it.visibility=View.GONE
            holder.view.favoriteInButton.visibility=View.VISIBLE


            val favoriteHashSetStaion = customPreferences.getFavoritePosition()
            favoriteHashSetStaion.add(realPosition)
            customPreferences.saveFavoritePosition(favoriteHashSetStaion)


        }
    }

    override fun getItemCount() : Int {
        return stationFilterList.size
    }

    fun updateStationList(newStationList: List<StationModel >) {
        stationList.clear()
        stationList.addAll(newStationList)
        notifyDataSetChanged()
    }

    override fun onStationClicked(v : View) {

    }



    fun distanceCalculate(x : Double,y : Double):Double{
        val xDistance = Math.abs(x- currentX!!)
        val yDistance = Math.abs(y- currentY!!)
        val distance = (xDistance*xDistance) + (yDistance*yDistance)
        return Math.sqrt(distance)
    }

    override fun getFilter() : Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                var filteredList = ArrayList<StationModel>()
                if (charSearch.isEmpty()) {
                    filteredList=stationFilterListFull

                } else {
                    val resultList = ArrayList<StationModel>()
                    for (row in stationFilterListFull) {

                        if (row.name?.lowercase(Locale.ROOT)!!.contains(charSearch.lowercase(Locale.ROOT))  ) {

                            filteredList.add(row)
                        }
                    }
                    stationFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                stationFilterList.clear()
                stationFilterList = results?.values as ArrayList<StationModel>
                notifyDataSetChanged()
            }

        }

    }




}
