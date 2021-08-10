package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.databinding.FragmentSpaceShipCreateBinding
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util.CustomSharedPreferences
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.SpaceShipCreateViewModel
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.viewmodel.SpaceStationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_space_ship_create.*


@AndroidEntryPoint
class SpaceShipCreate : Fragment() {


    private lateinit var binding: FragmentSpaceShipCreateBinding
    private val viewModel : SpaceShipCreateViewModel by viewModels()
    private var customPreferences = CustomSharedPreferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_space_ship_create, container, false)
        binding.lifecycleOwner=this
        binding.stationShip=viewModel

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewModel.refresh()

           if(viewModel.sub.value==15){
                total.setTextColor(Color.GREEN)
             }else{
                total.setTextColor(Color.RED)
             }
            strenght.progress=viewModel.dsValue.value!!
            speed.progress=viewModel.eusValue.value!!
            capasity.progress=viewModel.ugsValue.value!!
            spaceshipName.setText(viewModel.shipName.value)

        strenght.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                viewModel.dsValue.value=progress
                viewModel.sub.value=strenght.progress + speed.progress + capasity.progress
                if(viewModel.sub.value==15){
                    total.setTextColor(Color.GREEN)
                }else{
                    total.setTextColor(Color.RED)
                }
                if(progress==0){
                    strenght.progress=1
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started

            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped

            }
        })

        speed.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                viewModel.eusValue.value=progress
                viewModel.sub.value=strenght.progress + speed.progress + capasity.progress
                if(viewModel.sub.value==15){
                    total.setTextColor(Color.GREEN)
                }else{
                    total.setTextColor(Color.RED)
                }
                if(progress==0){
                    speed.progress=1
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {

            }
        })

        capasity.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                viewModel.ugsValue.value=progress
                viewModel.sub.value=strenght.progress + speed.progress + capasity.progress
                if(viewModel.sub.value==15){
                    total.setTextColor(Color.GREEN)
                }else{
                    total.setTextColor(Color.RED)
                }
                if(progress==0){
                    capasity.progress=1
                }
            }

            override fun onStartTrackingTouch(seek: SeekBar) {

            }

            override fun onStopTrackingTouch(seek: SeekBar) {

            }
        })

        start_button.setOnClickListener {
            if(viewModel.sub.value==15 && !spaceshipName.text.toString().equals("")){

                customPreferences.saveUgs( viewModel.ugsValue.value!!)
                customPreferences.saveEus( viewModel.eusValue.value!!)
                customPreferences.saveDs( viewModel.dsValue.value!!)
                customPreferences.saveName(spaceshipName.text.toString())

                val action = SpaceShipCreateDirections.actionSpaceShipCreateToSpacePort(viewModel.ugsValue.value!!,viewModel.dsValue.value!!,viewModel.eusValue.value!!,spaceshipName.text.toString())
                it.findNavController().navigate(action)

            }else if(viewModel.sub.value!=15){
                Toast.makeText(context,"Total Points Distributed Must Be 15 !!!",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(context,"Please Enter the Name of the Spaceship...",Toast.LENGTH_SHORT).show()
            }
        }

    }

}