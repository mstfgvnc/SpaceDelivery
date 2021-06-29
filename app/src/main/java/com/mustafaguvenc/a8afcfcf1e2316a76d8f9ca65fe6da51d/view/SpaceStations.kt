package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view


import android.app.Application
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter.StationAdapter
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util.CustomSharedPreferences
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.SpaceStationsViewModel
import kotlinx.android.synthetic.main.fragment_space_stations.*
import java.text.DecimalFormat


class SpaceStations() : Fragment() {

    private lateinit var viewModel : SpaceStationsViewModel
    private  val stationAdapter = StationAdapter(arrayListOf())
    private var customPreferences = CustomSharedPreferences()

    var firtVisit =true

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View? {

        return inflater.inflate(R.layout.fragment_space_stations, container, false)

    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {

        }

        viewModel = ViewModelProviders.of(this).get(SpaceStationsViewModel::class.java)
        viewModel.refreshData()

        shipName.text = viewModel.shipName

        station_list.layoutManager= LinearLayoutManager(context)
        station_list.adapter=stationAdapter
        search.isIconifiedByDefault=false
        search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                    stationAdapter.filter.filter(newText)

                return false
            }

        })

        newSpaceship.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }

        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.stations.observe(viewLifecycleOwner, Observer {stations ->
            stations?.let {
                station_list.visibility=View.VISIBLE
                stationAdapter.updateStationList(stations)


                if(customPreferences.getMinEus()!!.toDouble()>viewModel.eusValue.value!!.toDouble()){
                     Toast.makeText(context,"Mevcut EUS diğer istasyonlara gitmek için yeterli değil. Yeni Uzay Aracı Oluşturup Tekrar Deneyiniz..",
                     Toast.LENGTH_LONG).show()
                     val intent = Intent(context, MainActivity::class.java)
                      startActivity(intent)
                      activity?.finish()
                }else if(customPreferences.getMinUgs()!!>viewModel.ugsValue.value!!){
                    Toast.makeText(context,"Mevcut UGS diğer istasyon ihtiyaçları için yeterli değil. Yeni Uzay Aracı Oluşturup Tekrar Deneyiniz..",
                        Toast.LENGTH_LONG).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }else if(customPreferences.getVisitedPosition().size==it.size){
                    Toast.makeText(context,"Tebrikler Görev Tamamlandı . Yeni Uzay Aracı Oluşturup Tekrar Deneyiniz..",
                        Toast.LENGTH_LONG).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }


            }
        })
        viewModel.ugsValue.observe(viewLifecycleOwner, Observer {ugsValue ->
            ugsValue?.let {
                ugs.text="UGS : "+(ugsValue.toString())

            }
        })
        viewModel.dsValue.observe(viewLifecycleOwner, Observer {dsValue ->
            dsValue?.let {
                ds.text="DS : "+dsValue.toString()
            }
        })
        viewModel.eusValue.observe(viewLifecycleOwner, Observer {eusValue ->
            eusValue?.let {
                eus.text="EUS : "+DecimalFormat("##.##").format(eusValue).toString()
            }
        })
        viewModel.damage.observe(viewLifecycleOwner, Observer {damage ->
            damage?.let {
                damageValue.text="Hasar : "+damage.toString()
                if(it==0){
                    Toast.makeText(context,"Maalesef Uzay Aracının Dayanıklılığı Tükendi. Yeni Uzay Aracı Oluşturup Tekrar Deneyiniz..",
                        Toast.LENGTH_LONG).show()
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }


            }
        })
        viewModel.currentDamageTime.observe(viewLifecycleOwner, Observer {currentDamageTime ->
            currentDamageTime?.let {
                currentDamage.text="Anlık DS : "+currentDamageTime.toString()+"s"
            }
        })

        viewModel.currentStation.observe(viewLifecycleOwner, Observer {currentStation ->
            currentStation?.let {
                currentStationName.text="Mevcut Konum : "+currentStation
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {loading ->
            loading?.let {
                if(it){
                    station_loading.visibility=View.VISIBLE
                    station_list.visibility=View.GONE
                    station_error.visibility=View.GONE

                }else{
                    station_loading.visibility=View.GONE

                }

            }
        })
        viewModel.error.observe(viewLifecycleOwner, Observer {error ->
            error?.let {
                if(it){
                    station_error.visibility=View.VISIBLE

                }else{
                    station_error.visibility=View.GONE

                }
            }
        })
    }

    override fun onPause() {
        viewModel.timer.cancel()
        super.onPause()
    }

    override fun onResume() {
        if(firtVisit){
            firtVisit=false
        }else{
           viewModel.timer.start()
        }
        super.onResume()
    }


}