package com.testsandroid.earthquake

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "earthquake")
data class Earthquake (@PrimaryKey val id:String,
                       val place:String,
                       val magnitud: Double,
                       val time:Long,
                       val longitud: Double,
                       val latitud: Double) : Parcelable