package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.R
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.databinding.ActivityMainBinding
import com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.util.CustomSharedPreferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    var sub = 0


    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        getSupportActionBar()?.hide()

        val customPreferences = CustomSharedPreferences(applicationContext)
        start_button.setOnClickListener {

            if(sub!=15){
                Toast.makeText(applicationContext,"Dağıtılan Puan Toplamı 15 Olmalı !!! ",Toast.LENGTH_SHORT).show()
            }else if(spaceshipName.text.toString().equals("")){
                Toast.makeText(applicationContext,"Lütfen Uzay Aracının İsmini Giriniz...",Toast.LENGTH_SHORT).show()
            }else{
                customPreferences.saveDs(strenght.progress)
                customPreferences.saveRemainDs(strenght.progress*10000)
                customPreferences.saveEus(speed.progress)
                customPreferences.saveRemainEus((speed.progress*20).toFloat())
                customPreferences.saveUgs(capasity.progress)
                customPreferences.saveRemainUgs(capasity.progress*10000)
                customPreferences.saveName(spaceshipName.text.toString())
                customPreferences.saveCurrentCoordinateX(0.0)
                customPreferences.saveCurrentCoordinateY(0.0)
                customPreferences.saveMillisUntilFinished(strenght.progress.toLong()*10000*10)
                customPreferences.saveDamageValue(100)
                customPreferences.saveCurrentDamageTime(strenght.progress*10)
                customPreferences.saveCurrentStationName("Dünya")
                customPreferences.saveVisitedPosition(hashSetOf(0))
                customPreferences.saveFromMain(true)

                val intent = Intent(this, SecondActivity::class.java)
                startActivity(intent)
                finish()
            }

        }

        strenghtValue.text=customPreferences.getDs().toString()
        speedValue.text=customPreferences.getEus().toString()
        capasityValue.text=customPreferences.getUgs().toString()
        spaceshipName.setText(customPreferences.getName())
        strenght.progress= customPreferences.getDs()!!
        speed.progress= customPreferences.getEus()!!
        capasity.progress= customPreferences.getUgs()!!
        sub=strenght.progress + speed.progress + capasity.progress
        total.text = "Dağıtılan Puan :" + sub.toString() + " / 15"

        strenght.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {

                strenghtValue.text=strenght.progress.toString()
                sub=strenght.progress + speed.progress + capasity.progress
                total.text = "Dağıtılan Puan :" + sub.toString() + " / 15"
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
                // write custom code for progress is changed

                speedValue.text=speed.progress.toString()
                sub=strenght.progress + speed.progress + capasity.progress
                total.text = "Dağıtılan Puan :" + sub.toString() + " / 15"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started

            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped

            }
        })

        capasity.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                // write custom code for progress is changed

                capasityValue.text=capasity.progress.toString()
                sub=strenght.progress + speed.progress + capasity.progress
                total.text = "Dağıtılan Puan :" + sub.toString() + " / 15"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                // write custom code for progress is started

            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                // write custom code for progress is stopped

            }
        })

    }

}


