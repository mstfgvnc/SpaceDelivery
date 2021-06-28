package com.mustafaguvenc.a8afcfcf1e2316a76d8f9ca65fe6da51d.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StationModel(
    @ColumnInfo(name="name")
    val name : String?,
    @ColumnInfo(name="coordinateX")
    val coordinateX : Double?,
    @ColumnInfo(name="coordinateY")
    val coordinateY : Double?,
    @ColumnInfo(name="capacity")
    val capacity : Int?,
    @ColumnInfo(name="stock")
    val stock : Int?,
    @ColumnInfo(name="need")
    val need : Int?,

){
    @PrimaryKey(autoGenerate = true)
    var uuid :Int =0

}