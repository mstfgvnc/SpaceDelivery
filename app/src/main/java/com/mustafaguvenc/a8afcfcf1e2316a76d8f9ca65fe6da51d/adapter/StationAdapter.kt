package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter



import android.content.Context
import android.graphics.Color
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
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view.SpaceStationsDirections
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
    lateinit var context:Context
    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int) : StationViewHolder {
         val inflater =LayoutInflater.from(parent.context)
            context=parent.context
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
        val eusSpaceship=distanceCalculate(stationFilterList[position].coordinateX!!,stationFilterList[position].coordinateY!!)
        val ugsSpaceShip= stationFilterList[position].need

       if(eusSpaceship!=0.0){
           if(!customPreferences.getVisitedPosition().contains(realPosition)){
               if(customPreferences.getMinEus()!!.toFloat()==-1f){
                   customPreferences.saveMinEus(eusSpaceship.toFloat())
               }else{
                   customPreferences.saveMinEus(Math.min(customPreferences.getMinEus()!!.toFloat(),eusSpaceship.toFloat()))
               }
           }

       }

        if(ugsSpaceShip!=0){
            if(!customPreferences.getVisitedPosition().contains(realPosition)){
                if(customPreferences.getMinUgs()==-1){
                    customPreferences.saveMinUgs(ugsSpaceShip!!)
                }else{
                    customPreferences.saveMinUgs(Math.min(customPreferences.getMinUgs()!!,ugsSpaceShip!!))
                }
            }

        }

        holder.view.nowEus.text="Uzaklık: "+DecimalFormat("##.##").format(eusSpaceship).toString()+"EUS"
        holder.view.capasityStock.text="Kapasite: "+stationFilterList[position].capacity.toString()+"\n"+"Stok: "+stationFilterList[position].stock.toString()
        if(customPreferences.getVisitedPosition().contains(realPosition)){

            holder.view.travelButton.setTextColor(Color.WHITE)
            holder.view.travelButton.setBackgroundColor(Color.BLACK)
            holder.view.travelButton.text="İHTİYAÇ YOK"
            holder.view.travelButton.isClickable=false
            holder.view.capasityStock.text="Kapasite: "+stationFilterList[position].capacity.toString()+"\n"+"Stok: " +stationFilterList[position].capacity.toString()


        }else{
            holder.view.travelButton.setTextColor(Color.BLACK)
            holder.view.travelButton.setBackgroundColor(Color.GREEN)
            holder.view.travelButton.text="İSTASYONA GİT"

            holder.view.travelButton.setOnClickListener {
                if(customPreferences.getRemainEus()!!<eusSpaceship){
                    Toast.makeText(context,"Bu istasyona gitmek için yeterli EUS yok !!!",
                        Toast.LENGTH_SHORT).show()

                }else if(customPreferences.getRemainUgs()!!<ugsSpaceShip!!){
                    Toast.makeText(context,"Bu istasyona gitmek için yeterli UGS yok !!!",
                        Toast.LENGTH_SHORT).show()
                } else{
                    customPreferences.saveCurrentCoordinateX(stationFilterList[position].coordinateX!!)
                    customPreferences.saveCurrentCoordinateY(stationFilterList[position].coordinateY!!)
                    customPreferences.saveCurrentStationName(stationFilterList[position].name!!)

                    val visitedHashSetStaion = customPreferences.getVisitedPosition()
                    visitedHashSetStaion.add(realPosition)
                    customPreferences.saveVisitedPosition(visitedHashSetStaion)

                    customPreferences.saveRemainUgs(customPreferences.getRemainUgs()?.minus(stationFilterList[position].need!!)!!)
                    customPreferences.saveRemainEus(customPreferences.getRemainEus()?.minus(eusSpaceship.toFloat())!!)
                    customPreferences.saveFromMain(false)
                    val action =SpaceStationsDirections.actionSpaceStationsSelf(true)
                    Navigation.findNavController(it).navigate(action)

                }




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
