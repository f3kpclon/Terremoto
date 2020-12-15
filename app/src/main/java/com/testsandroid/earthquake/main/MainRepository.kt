package com.testsandroid.earthquake.main

import androidx.lifecycle.LiveData
import com.testsandroid.earthquake.Earthquake
import com.testsandroid.earthquake.api.EqJsonResponse
import com.testsandroid.earthquake.api.service
import com.testsandroid.earthquake.dto.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//Encargado de comunicarse con el servicio web o con base de datos o firebase
class MainRepository (private val dataBase: EqDatabase){

    //val eqList: LiveData<MutableList<Earthquake>> = dataBase.dao.getEarthquake()

    public suspend fun fetchEarthquakes(sortByMagnitud: Boolean) : MutableList<Earthquake> {
        return withContext(Dispatchers.IO){
            val eqList = service.getListEarthquake()
            val eqJsonParsed = parseEqResult(eqList)
            dataBase.dao.insertAll(eqJsonParsed)

           fetchEQFromDataBase(sortByMagnitud)


        }

    }
    suspend fun fetchEQFromDataBase(sortByMagnitud: Boolean): MutableList<Earthquake> {
        return withContext(Dispatchers.IO) {

            if (sortByMagnitud) {
                dataBase.dao.getEarthquakeByMag()
            } else {
                dataBase.dao.getEarthquakeByTime()
            }
        }
    }

    private fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake> {

        val eqList = mutableListOf<Earthquake>()
        val featureList = eqJsonResponse.features

        for(feature in featureList){
            val properties = feature.properties
            val magnitud = properties.mag
            val place = properties.place
            val time = properties.time
            val id = feature.id
            val geometry = feature.geometry
            val latitud = geometry.latitude
            val longitud = geometry.longitude

            eqList.add(
                Earthquake(
                    id,
                    place,
                    magnitud,
                    time,
                    longitud,
                    latitud
                )
            )
        }


        return eqList

    }
}