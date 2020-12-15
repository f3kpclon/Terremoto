package com.testsandroid.earthquake.api

import android.content.Context
import androidx.work.*
import com.testsandroid.earthquake.Constants
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

        //se instancia el WorkManager, primero va el String que hace referencia ala syncWorkmanager, el tipo de policy al instanciar
        //si se mantiene(KEEP) u otro valor, y el syncRequest que es la periocidad en que se instacia el WORKER
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            Constants.WORK_NAME, ExistingPeriodicWorkPolicy.KEEP,syncRequest)
    }
}