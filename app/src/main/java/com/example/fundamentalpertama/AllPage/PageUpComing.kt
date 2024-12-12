package com.example.fundamentalpertama.AllPage

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.example.fundamentalpertama.AllComponent.CustomCard.CustomCard
import com.example.fundamentalpertama.NetworkMonitor.NetworkMonitor

@Composable
fun PageUpComing(navController: NavController) {
    val dataDicodingEventViewModel: DataDicodingEventViewModel = viewModel()
    var dataDicodingEventOnGoing by remember {
        mutableStateOf(dataDicodingEventViewModel.eventOnGoing)
    }
    var isLoading by remember { mutableStateOf(true) }
    val createContext = LocalContext.current
    val netWorkMonitor = remember { NetworkMonitor(context = createContext) }
    var getError by remember { mutableStateOf("") }

    isLoading = dataDicodingEventOnGoing.isEmpty()

    fun fetchOnGoing() {
        dataDicodingEventViewModel.fetchDataDicodingEvent(
            onResult = { fetch ->
                dataDicodingEventOnGoing = fetch
                isLoading = false
            },
            type = "On-Going",
            getError = {
                getError = it
            }
        )
    }

    LaunchedEffect(Unit) {
        netWorkMonitor.startNetworkCallback(
            onNetworkAvailable = {
                fetchOnGoing()
            },

            )
        fetchOnGoing()
    }

    LazyColumn(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp),
        content = {
            item {
                Spacer(modifier = Modifier.padding(10.dp))
            }
            if (isLoading) {
                item {

                    Column(
                        modifier = Modifier
                            .padding(top = 40.dp, bottom = 40.dp)
                            .fillParentMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            CircularProgressIndicator(color = Color(0xFFEE299B))
                            Text("Loading...", fontSize = 10.sp, modifier = Modifier.padding(5.dp))
                        }
                    )
                }
            } else {
                items(
                    items = dataDicodingEventOnGoing,
                    itemContent = {
                        CustomCard(
                            id = it.id,
                            category = it.category,
                            img = it.imageLogo,
                            name = it.name,
                            ownerName = it.ownerName,
                            summary = it.summary,
                            beginTime = it.beginTime,
                            endTime = it.endTime,
                            onClick = { id ->
                                Log.d("Get id card up coming", it.id.toString())
                                Log.d("Get id card up coming", id.toString())
                                navController.navigate("Detail/${id}")
                            }
                        )
                        Spacer(modifier = Modifier.padding(10.dp))
                    }
                )
            }
        }
    )
}