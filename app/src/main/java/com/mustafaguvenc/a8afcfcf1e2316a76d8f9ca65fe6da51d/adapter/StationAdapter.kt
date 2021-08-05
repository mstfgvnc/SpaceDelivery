package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter



import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.databinding.ItemStationListBinding
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model.StationModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.service.StationDatabase
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view.*
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.SpaceStationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
import kotlin.math.floor

class StationAdapter
 //   @Inject constructor
    (private val stationList: ArrayList<StationModel>,val fragment: Fragment):RecyclerView.Adapter<StationAdapter.StationViewHolder>() ,StationClickListener,Filterable{

    private var currentX = 0.0
    private var currentY = 0.0
    private val visitedPositions = arrayListOf(0)
    var stationFilterList = ArrayList<StationModel>()
    var stationFilterListFull = ArrayList<StationModel>()
    private lateinit var viewModel : SpaceStationsViewModel
  // @Inject
  //  lateinit var viewModel : SpaceStationsViewModel

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder : StationViewHolder, position : Int) {
        var realPosition = position
        for(i in 0 until stationList.size-1){
            if(stationFilterList[position].name.equals(stationList[i].name)){
                realPosition= i
            }
        }

        holder.view.station= stationFilterList[position]
        holder.view.listener= this
        val eusSpaceship=Math.round(distanceCalculate(stationFilterList[position].coordinateX!!,stationFilterList[position].coordinateY!!)*100)/100.00
        stationFilterList[position].nowEus= eusSpaceship

        viewModel = ViewModelProvider(fragment).get(SpaceStationsViewModel::class.java)


        if(visitedPositions.contains(realPosition)){

            holder.view.travelButton.setTextColor(Color.BLACK)
            holder.view.travelButton.setBackgroundColor(Color.GREEN)
            holder.view.travelButton.text = "Mission Completed"
            holder.view.travelButton.textSize=8f
            holder.view.travelButton.isClickable = false
            holder.view.travelButton.isClickable = false
            stationFilterList[position].nowStock=stationFilterList[position].capacity!!

        }else if(viewModel.eusValue.value!!<eusSpaceship && viewModel.ugsValue.value!!<stationFilterList[position].need!!){
            holder.view.travelButton.setTextColor(Color.WHITE)
            holder.view.travelButton.setBackgroundColor(Color.BLACK)
            holder.view.travelButton.text="Insufficient UST ve SS"
            holder.view.travelButton.textSize=8f
            holder.view.travelButton.isClickable=false
            stationFilterList[position].nowStock=stationFilterList[position].stock!!

        }else if(viewModel.eusValue.value!!<eusSpaceship ){
            holder.view.travelButton.setTextColor(Color.WHITE)
            holder.view.travelButton.setBackgroundColor(Color.BLACK)
            holder.view.travelButton.text="Insufficient UST"
            holder.view.travelButton.textSize=8f
            holder.view.travelButton.isClickable=false
            stationFilterList[position].nowStock=stationFilterList[position].stock!!

        } else if(viewModel.ugsValue.value!!<stationFilterList[position].need!!){
            holder.view.travelButton.setTextColor(Color.WHITE)
            holder.view.travelButton.setBackgroundColor(Color.BLACK)
            holder.view.travelButton.text="Insufficient SS"
            holder.view.travelButton.textSize=8f
            holder.view.travelButton.isClickable=false
            stationFilterList[position].nowStock=stationFilterList[position].stock!!

        }else {
            holder.view.travelButton.setTextColor(Color.BLACK)
            holder.view.travelButton.setBackgroundColor(Color.YELLOW)
            holder.view.travelButton.text="GO"
            stationFilterList[position].nowStock=stationFilterList[position].stock!!

            holder.view.travelButton.setOnClickListener {

                currentX = stationFilterList[position].coordinateX!!
                currentY = stationFilterList[position].coordinateY!!
                viewModel.currentStation.value = stationFilterList[position].name!!
                viewModel.eusValue.value = floor((viewModel.eusValue.value!! - eusSpaceship) * 100) / 100
                viewModel.ugsValue.value = viewModel.ugsValue.value!! - stationFilterList[position].need!!
                visitedPositions.add(realPosition)
                notifyDataSetChanged()

                var eusMin =0.0
                var ugsMin =0

                  for(i in 0..stationList.size-1){
                      if(i!=realPosition && !visitedPositions.contains(i)){

                          if(eusMin==0.0){
                              eusMin=distanceCalculate(stationList[i].coordinateX!!,stationList[i].coordinateY!!)
                          }else{
                              val eusDetect=distanceCalculate(stationList[i].coordinateX!!,stationList[i].coordinateY!!)
                                eusMin=Math.min(eusMin,eusDetect)
                          }

                          if(ugsMin==0){
                              ugsMin=stationList[i].need!!
                          }else{
                              ugsMin=Math.min(stationList[i].need!!,ugsMin)
                          }
                      }

                  }


                  if (visitedPositions.size == stationList.size) {
                      Toast.makeText(context, "Tebrikler Görev Tamamlandı . Yeni Uzay Aracı Oluşturup Tekrar Deneyiniz..", Toast.LENGTH_LONG).show()
                      val action = SpacePortDirections.actionSpacePortToSpaceShipCreate()
                      it.findNavController().navigate(action)
                  }else if(eusMin>viewModel.eusValue.value!! && ugsMin>viewModel.ugsValue.value!!){
                      Toast.makeText(context,"Mevcut EUS ve UGS diğer istasyonlara gitmek için yeterli değil. Yeni Uzay Aracı Oluşturup Tekrar Deneyiniz..",Toast.LENGTH_LONG).show()
                      val action = SpacePortDirections.actionSpacePortToSpaceShipCreate()
                      it.findNavController().navigate(action)
                  }else if(eusMin>viewModel.eusValue.value!!){
                      Toast.makeText(context,"Mevcut EUS diğer istasyonlara gitmek için yeterli değil. Yeni Uzay Aracı Oluşturup Tekrar Deneyiniz..",Toast.LENGTH_LONG).show()
                      val action = SpacePortDirections.actionSpacePortToSpaceShipCreate()
                      it.findNavController().navigate(action)
                  }else if(ugsMin>viewModel.ugsValue.value!!){
                      Toast.makeText(context,"Mevcut UGS diğer istasyonların malzeme ihtiyaçları için yeterli değil. Yeni Uzay Aracı Oluşturup Tekrar Deneyiniz..",Toast.LENGTH_LONG).show()
                      val action = SpacePortDirections.actionSpacePortToSpaceShipCreate()
                      it.findNavController().navigate(action)
                  }
            }

        }

        if(stationList.get(realPosition).favoriteBool){
            holder.view.favoriteInButton.visibility=View.VISIBLE
            holder.view.favoriteOutButton.visibility=View.GONE
        }else{
            holder.view.favoriteInButton.visibility=View.GONE
            holder.view.favoriteOutButton.visibility=View.VISIBLE
        }

        holder.view.favoriteInButton.setOnClickListener {
            it.visibility=View.GONE
            holder.view.favoriteOutButton.visibility=View.VISIBLE
            updateStation(realPosition,false)
        }
        holder.view.favoriteOutButton.setOnClickListener {
            it.visibility=View.GONE
            holder.view.favoriteInButton.visibility=View.VISIBLE
            updateStation(realPosition,true)
        }
    }

    private fun updateStation(position: Int,favoriteBool : Boolean) {
        viewModel=ViewModelProvider(fragment).get(SpaceStationsViewModel::class.java)
        viewModel.launch {

            val editedItem=stationList.get(position)
            editedItem.uuid=stationList.get(position).uuid
            editedItem.favoriteBool=favoriteBool
            withContext(Dispatchers.IO) {
                StationDatabase(context).stationDao().update(editedItem)
            }

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
        val xDistance = Math.abs(x- currentX)
        val yDistance = Math.abs(y- currentY)
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
