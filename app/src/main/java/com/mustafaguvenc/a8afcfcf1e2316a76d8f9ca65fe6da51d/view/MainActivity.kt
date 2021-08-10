package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
/*
    @Inject
    lateinit var runDao : StationDao

 */

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSupportActionBar()?.hide()

    }

     override fun onBackPressed() {
         // super.onBackPressed()
     }

}


