package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.adapter.StationAdapter
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.databinding.FragmentSpaceStationsBinding
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.SpaceStationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_space_stations.*
import javax.inject.Inject

// @AndroidEntryPoint
class  SpaceStations : Fragment() {

    private lateinit var viewModel : SpaceStationsViewModel
  //   @Inject
  //   lateinit var viewModel : SpaceStationsViewModel

    private lateinit var binding :FragmentSpaceStationsBinding
    var stationAdapter = StationAdapter(arrayListOf(),this)
  //  @Inject
 //   lateinit var stationAdapter :StationAdapter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater : LayoutInflater, container : ViewGroup?,
        savedInstanceState : Bundle?
    ) : View {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_space_stations, container, false)
        binding.lifecycleOwner=this

        viewModel = ViewModelProvider(this).get(SpaceStationsViewModel::class.java)

        binding.values = viewModel//attach your viewModel to xml
        return binding.root

    }

    override fun onViewCreated(view : View, savedInstanceState : Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
            val action=SpacePortDirections.actionSpacePortToSpaceShipCreate()
            it.findNavController().navigate(action)

        }

        observeLiveData()

        binding.executePendingBindings()

    }

    private fun observeLiveData(){
        viewModel.stations.observe(viewLifecycleOwner,  {stations ->
            stations?.let {

                station_list.visibility=View.VISIBLE
                stationAdapter.updateStationList(stations)

            }
        })


        viewModel.damage.observe(viewLifecycleOwner,  {damage ->
            damage?.let {
                if(it==0){
                    Toast.makeText(context,"Maalesef Uzay Aracının Dayanıklılığı Tükendi. Yeni Uzay Aracı Oluşturup Tekrar Deneyiniz..",
                        Toast.LENGTH_LONG).show()
                    val action = SpacePortDirections.actionSpacePortToSpaceShipCreate()
                    findNavController(fragment.requireView()).navigate(action)
                }
            }
        })

        viewModel.loading.observe(viewLifecycleOwner,  {loading ->
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
        viewModel.error.observe(viewLifecycleOwner,  {error ->
            error?.let {
                if(it){
                    station_error.visibility=View.VISIBLE

                }else{
                    station_error.visibility=View.GONE

                }
            }
        })

    }


}