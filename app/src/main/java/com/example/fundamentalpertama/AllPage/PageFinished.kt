package com.example.fundamentalpertama.AllPage

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fundamentalpertama.API.ApiViewModel.DataDicodingEventViewModel
import com.example.fundamentalpertama.API.DataDicodingEvent
import com.example.fundamentalpertama.AllComponent.CustomCard.CustomCard
import com.example.fundamentalpertama.NetworkMonitor.NetworkMonitor

@Composable
fun PageFinished(
    searchResult: List<DataDicodingEvent>,
    navController: NavController
) {
    val dataDicodingEventViewModel: DataDicodingEventViewModel = viewModel()
    var dataDicodingEventFinished by remember { mutableStateOf(dataDicodingEventViewModel.eventFinished) }
    var isLoading by remember { mutableStateOf(true) }
    val createContext = LocalContext.current
    val netWorkMonitor = remember { NetworkMonitor(context = createContext) }
    var getError by remember { mutableStateOf("") }
    var isRefreshing by remember { mutableStateOf(false) }
    isLoading = dataDicodingEventFinished.isEmpty()

    fun fetchFinished() {
        isRefreshing = true
        if (searchResult.isEmpty()) {
            dataDicodingEventViewModel.fetchDataDicodingEvent(
                type = "Finished",
                onResult = { fetch ->
                    dataDicodingEventFinished = fetch
                    isLoading = false
                    isRefreshing= false
                },
                getError = {
                    getError = it
                    isLoading = false
                    isRefreshing= false
                }
            )
        } else {
            dataDicodingEventFinished = searchResult
            isRefreshing = false
        }
    }

    LaunchedEffect(searchResult) {
        netWorkMonitor.startNetworkCallback(
            onNetworkAvailable = {
                fetchFinished()
            },
        )
        fetchFinished()
    }

    if (getError.isNotBlank()) {
        Toast.makeText(createContext, getError, Toast.LENGTH_SHORT).show()
    }

            if (isLoading) {
                Column(
                    modifier = Modifier
                        .padding(top = 40.dp, bottom = 40.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        CircularProgressIndicator(color = Color(0xFFEE299B))
                        Text("Loading...", fontSize = 10.sp, modifier = Modifier.padding(5.dp))
                    }
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier.fillMaxSize(),
                    content = {
                        items(
                            items = dataDicodingEventFinished,
                            itemContent = {finished->
                                CustomCard(
                                    id = finished.id,
                                    category = finished.category,
                                    img = finished.imageLogo,
                                    name = finished.name,
                                    ownerName = finished.ownerName,
                                    summary = finished.summary,
                                    beginTime = finished.beginTime,
                                    endTime = finished.endTime,
                                    onClick = {
                                        navController.navigate("Detail/${finished.id}")
                                    }
                                )
                                Spacer(modifier = Modifier.padding(10.dp))
                            }
                        )

                    }
                )
            }


}