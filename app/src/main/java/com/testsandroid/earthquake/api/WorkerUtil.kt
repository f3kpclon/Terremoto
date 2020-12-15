package com.testsandroid.earthquake.api

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit

object WorkerUtil {

    fun scheduleSync(context: Context){
        //restricciones solo se conecta si el telefono esta conectado a internet
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        //se ejecuta cada 1 hora y se le insertan las restricciones ya antes declaradas
        val syncRequest =
            PeriodicWorkRequestBuilder<SyncWorkManager>(1,TimeUnit.HOURS)
                .setConstraints(constraints)
                .build()

        //se instancia el WorkManager
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            SyncWorkManager.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP,syncRequest)
    }
}