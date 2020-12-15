package com.testsandroid.earthquake.dto

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.testsandroid.earthquake.Earthquake

@Database(entities = [Earthquake::class], version = 1)
abstract class EqDatabase : RoomDatabase(){
    abstract  val dao: EarthquakeDAO
}

//singleton
private lateinit var INSTANCE: EqDatabase
fun getDataBase(context: Context): EqDatabase{
    synchronized(EqDatabase::class.java){
        if (!::INSTANCE.isInitialized){
            INSTANCE =
                Room.databaseBuilder(
                    context.applicationContext,
                    EqDatabase::class.java,
                    "earthquake_db"
                ).build()
        }
        return INSTANCE

    }
}