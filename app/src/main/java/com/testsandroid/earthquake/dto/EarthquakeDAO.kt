package com.testsandroid.earthquake.dto

import androidx.lifecycle.LiveData
import androidx.room.*
import com.testsandroid.earthquake.Earthquake

@Dao
interface EarthquakeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>)

    @Query("SELECT * FROM earthquake")
    fun getEarthquake(): LiveData<MutableList<Earthquake>>

    @Query("SELECT * FROM earthquake ORDER BY magnitud DESC")
    fun getEarthquakeByMag(): MutableList<Earthquake>

    @Query("SELECT * FROM earthquake WHERE id= :id_eq")
    fun getEarthquakeById(id_eq:String): MutableList<Earthquake>

    @Query("SELECT * FROM earthquake ORDER BY time DESC")
    fun getEarthquakeByTime(): MutableList<Earthquake>



    /*
    @Update
    fun updateEq(vararg eq: Earthquake)

    @Delete
    fun deleteEq(vararg eq: Earthquake)
    */

}