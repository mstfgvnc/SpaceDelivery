package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util.CustomSharedPreferences
import kotlinx.android.synthetic.main.activity_second.*


class SecondActivity : AppCompatActivity() {
    private lateinit var navigationController : NavController
    private var customPreferences = CustomSharedPreferences()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        getSupportActionBar()?.hide()

        stationButton.setBackgroundColor(Color.BLUE)
        favoriteButton.setBackgroundColor(Color.WHITE)
        stationButton.setTextColor(Color.WHITE)
        favoriteButton.setTextColor(Color.BLACK)
        var navigationWhere = 0

        stationButton.setOnClickListener {
            if(navigationWhere!=0){
                stationButton.setBackgroundColor(Color.BLUE)
                favoriteButton.setBackgroundColor(Color.WHITE)
                stationButton.setTextColor(Color.WHITE)
                favoriteButton.setTextColor(Color.BLACK)
                navigationWhere=0
                customPreferences.saveFromMain(false)
                val action = FavoriteStationsDirections.actionFavoriteStationsToSpaceStations(true)
                navigationController.navigate(action)

            }

        }
        favoriteButton.setOnClickListener {
            if(navigationWhere!=1){
                favoriteButton.setBackgroundColor(Color.BLUE)
                stationButton.setBackgroundColor(Color.WHITE)
                stationButton.setTextColor(Color.BLACK)
                favoriteButton.setTextColor(Color.WHITE)
                navigationWhere=1

                val action = SpaceStationsDirections.actionSpaceStationsToFavoriteStations()
                navigationController.navigate(action)
            }
        }

        navigationController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this,navigationController)

    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navigationController,null)
    }

    override fun onBackPressed() {

      //  super.onBackPressed()
    }

}