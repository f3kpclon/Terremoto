package com.testsandroid.earthquake.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.testsandroid.earthquake.Earthquake
import com.testsandroid.earthquake.api.ApiResponseStatus
import com.testsandroid.earthquake.dto.getDataBase
import kotlinx.coroutines.*
import java.net.UnknownHostException

private val TAG = MainViewMail::class.java.simpleName
class MainViewMail(application: Application,private val sortType:Boolean): AndroidViewModel(application) {
   private val database = getDataBase(application.applicationContext)
   private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    //colocamos un observe en el main activity para status
    val status : LiveData<ApiResponseStatus>
           get() = _status

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList : LiveData<MutableList<Earthquake>>
    get() = _eqList


    init {
        reloadEarthquakeFromDB(sortType)
    }

     private fun reloadEarthquake() {
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.LOADING
                _eqList.value = repository.fetchEarthquakes(sortType)
                _status.value = ApiResponseStatus.DONE
            } catch (e: UnknownHostException) {
                _status.value = ApiResponseStatus.ERROR
                Log.i(TAG, "no internet conection", e)
            }
        }
    }
    fun reloadEarthquakeFromDB(sortByMagnitud: Boolean) {
        viewModelScope.launch {
            _eqList.value = repository.fetchEQFromDataBase(sortByMagnitud)
            if(_eqList.value!!.isEmpty()){
                reloadEarthquake()
            }

        }
    }

}