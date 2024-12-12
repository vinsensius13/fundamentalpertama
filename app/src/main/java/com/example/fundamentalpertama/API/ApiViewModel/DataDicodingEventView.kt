package com.example.fundamentalpertama.API.ApiViewModel

import com.example.fundamentalpertama.API.ApiClient.ApiClient
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fundamentalpertama.API.DataDicodingEvent
import com.example.fundamentalpertama.Room.FavoriteDao
import kotlinx.coroutines.launch

@Suppress("unused")
class DataDicodingEventViewModel : ViewModel() {
    private lateinit var favoriteDao: FavoriteDao

    var eventOnGoing: List<DataDicodingEvent> = emptyList()
    var eventFinished: List<DataDicodingEvent> = emptyList()
    var eventDetailCache: MutableList<DataDicodingEvent> = mutableListOf()
    var eventNotif: MutableList<DataDicodingEvent> = mutableListOf()
//    var eventSearch: List<DataDicodingEvent> = emptyList()
    //    var dataDicodingEventFinished by remember {
//        mutableStateOf<List<DataDicodingEvent>>(emptyList())
//    }

    fun fetchDataDicodingEvent(
        type: String,
        onResult: (List<DataDicodingEvent>) -> Unit,
        getError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = when (type) {
                    "On-Going" -> {
                        ApiClient.apiService.getEvent(1)
                    }

                    "Finished" -> {
                        ApiClient.apiService.getEvent(0)
                    }

                    else -> {
                        throw IllegalArgumentException("data event tidak diketahui input: -1 ,0 , 1")
                    }
                }

                when (type) {
                    "On-Going" -> {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                eventOnGoing = it.listEvents
                                Log.d("get data on Going", eventOnGoing.toString())
                                onResult(eventOnGoing)
                            }
                        } else {
                            Log.d("View Model", "Error: response todak sukses")
                        }
                    }
                    "Finished" -> {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                eventFinished = it.listEvents
                                Log.d("get data on Going", eventFinished.toString())
                                onResult(eventFinished)
                            }
                        } else {
                            Log.d("View Model", "Error: response tidak sukses")
                        }
                    }
                }


            } catch (e: Exception) {
                Log.d("TRY CATCH VIEW MODEL", "Error ; $e")
                getError("data API bermasalah $e")

            }
        }

    }

    fun fetchSearchDataDicodingEvent(keyword: String, onResult: (List<DataDicodingEvent>) -> Unit) {
        viewModelScope.launch {
            try {
                Log.d("Please Check", keyword)
                val response = ApiClient.apiService.searchEvent(active = 0, keyword = keyword)
                if (response.isSuccessful) {
                    response.body()?.let {
                        eventFinished = it.listEvents
                        onResult(eventFinished)
                    }
                } else {
                    Log.d("inFetch Search", "Kwakkk")
                    Log.d(
                        "in Fetch Search",
                        "Error: ${response.code()} - ${response.errorBody()?.string()}"
                    )
                }

            } catch (e: Exception) {
                Log.d("fetch search", "$e")
            }
        }
    }

    fun fetchDetailDicodingEvent(getId: Int, onResult: (DataDicodingEvent) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getDetail(getId = getId)
                if (response.isSuccessful) {
                    response.body()?.let {
                        Log.d("fetch detail", "get id $getId")
                        val eventDetail2 = it.listEvents.find { data ->
                            data.id == getId
                        }

                        Log.d("get response event detail", eventDetail2.toString())
                        if (eventDetail2 != null) {
//                            eventDetail = eventDetail2
                            eventDetailCache.add(eventDetail2)
                            onResult(eventDetail2)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d("data dicoding event error", e.toString())
            }
        }

    }
    fun fetchEvents(onResult: (DataDicodingEvent) -> Unit) {
        viewModelScope.launch {
            try {
                val response = ApiClient.apiService.getActiveEvents()
                if (response.isSuccessful) {
                    response.body()?.let { events ->
                        Log.d("fetch events", "events count: ${events}")
                        onResult(events.listEvents.first())
                    } ?: run {
                        Log.d("fetch events", "Response body is null")
                    }
                } else {
                    Log.e("fetch events", "Error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("fetch events error", e.toString())
            }
        }
    }

}