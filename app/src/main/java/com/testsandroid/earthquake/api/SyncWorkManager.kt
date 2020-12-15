package com.testsandroid.earthquake.api

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.testsandroid.earthquake.dto.getDataBase
import com.testsandroid.earthquake.main.MainRepository

//trabajo en background
class SyncWorkManager(appContext: Context,params:WorkerParameters): CoroutineWorker(appContext,params) {

    /*companion object{


    }*/
    private val database = getDataBase(applicationContext)
    private val repository = MainRepository(database)

    override suspend fun doWork(): Result {
        repository.fetchEarthquakes(true)
        return Result.success()
    }

}