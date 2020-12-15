package com.testsandroid.earthquake.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


interface EarthquakeApiService {
    @GET("all_hour.geojson")
    suspend fun getListEarthquake(): EqJsonResponse
}

private var retrofit = Retrofit.Builder()
    .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
    //convierte la respuesta JSON en String
    //.addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

var service: EarthquakeApiService = retrofit.create(
    EarthquakeApiService::class.java)