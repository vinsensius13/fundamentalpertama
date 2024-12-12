package com.example.fundamentalpertama.AllPage

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.fundamentalpertama.API.ApiViewModel.DataDicodingEventViewModel
import com.example.fundamentalpertama.AllComponent.CustomCard.CustomCard
import com.example.fundamentalpertama.NetworkMonitor.NetworkMonitor


@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageHome(navController: NavController, scrollBehavior: TopAppBarScrollBehavior) {
    val dataDicodingEventViewModel: DataDicodingEventViewModel = viewModel()
    var dataDicodingEventOnGoing by remember { mutableStateOf(dataDicodingEventViewModel.eventOnGoing) }
    var dataDicodingEventFinished by remember { mutableStateOf(dataDicodingEventViewModel.eventFinished) }
    var isLoadingOnGoing by remember { mutableStateOf(true) }
    var isLoadingFinished by remember { mutableStateOf(true) }
    val createContext = LocalContext.current
    val netWorkMonitor = remember { NetworkMonitor(context = createContext) }
    val scoper = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    isLoadingOnGoing = dataDicodingEventOnGoing.isEmpty()
    isLoadingFinished = dataDicodingEventFinished.isEmpty()
    var getErorongoing by remember { mutableStateOf("") }
    var getErorfinfished by remember { mutableStateOf("") }

    fun fetchAllData() {
        dataDicodingEventViewModel.fetchDataDicodingEvent(
            type = "On-Going",
            onResult = { fetch ->
                dataDicodingEventOnGoing = fetch
                isLoadingOnGoing = false
            },
            getError = {
                getErorongoing = it
            }
        )

        dataDicodingEventViewModel.fetchDataDicodingEvent(
            type = "Finished",
            onResult = { fetch ->
                dataDicodingEventFinished = fetch
                isLoadingFinished = false
            },
            getError = {
                getErorfinfished = it
            }
        )
    }
    if (getErorongoing.isNotBlank()) {
        Toast.makeText(createContext, getErorongoing, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(netWorkMonitor) {
        netWorkMonitor.startNetworkCallback(
            onNetworkAvailable = {
                fetchAllData()
            },
        )
        fetchAllData()
    }


    LazyColumn(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        content = {
            item {
                Box(
                    modifier = Modifier.height(400.dp),
                    content = {
                        Image(
                            painter = rememberAsyncImagePainter("https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/event-ui-hero.jpg"),
                            contentDescription = "bacground",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .shadow(10.dp)
                                .fillMaxHeight()
                                .fillMaxWidth()
                        )
                        Column(
                            modifier = Modifier.padding(20.dp),
                            content = {
                                Image(
                                    painter = rememberAsyncImagePainter("https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/event-ui-logo.png"),
                                    contentDescription = "img",
                                    modifier = Modifier.size(70.dp)
                                )
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text(text = "Dicoding Event", color = Color(0xFF565656))
                                Spacer(modifier = Modifier.padding(30.dp))
                                Text(
                                    text = "Kembangkan Jaringan dan Belajar dari Developer Terbaik",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.ExtraBold,
                                )
                                Spacer(modifier = Modifier.padding(20.dp))
                                Text(
                                    text = "Tingkatkan  Kemampuan teknis sekaligus membuka jaringan dengan developer terbaik melalui seminar atau workshop yang diselenggarakan oleh partner Dicoding",
                                    color = Color(0xFF565656),
                                )

                            }
                        )
                    }
                )
            }
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, bottom = 40.dp, top = 40.dp),
                    content = {
                        Text(
                            text = "Kenapa Event Penting bagi jenjang karirmu",
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = "Berikut ini adalah manfaat yang akan kamu dapatkan jika aktif bergabung dalam event Dicoding.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Image(
                            painter = rememberAsyncImagePainter("https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/event-ui-upgrade.png"),
                            contentDescription = "img",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                        )
                        Text(
                            text = "Upgrade Kemampuan Teknis",
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = "Banyak pembicara yang sudah lama di dunia IT dari berbagai bidang, sehingga Anda dapat memilih event yang sesuai dengan kemampuan/minat anda.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                            ,color = MaterialTheme.colorScheme.onPrimary

                        )
                        Image(
                            painter = rememberAsyncImagePainter("https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/event-ui-network.png"),
                            contentDescription = "img",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                        )
                        Text(
                            text = "Bangun Jaringan",
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            modifier = Modifier.fillMaxWidth()
                            ,color = MaterialTheme.colorScheme.onPrimary

                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = "Event dihadiri oleh pembicara yang top dan peserta dari berbagai daerah, sehingga Anda bisa berkesempatan membangun relasi dengan pembicara, peserta dan orang-orang hebat di sana.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                            ,color = MaterialTheme.colorScheme.onPrimary

                        )
                        Image(
                            painter = rememberAsyncImagePainter("https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/event-ui-update.png"),
                            contentDescription = "img",
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                        )
                        Text(
                            text = "Up-to-Date Terhadap Perkembangan Teknologi",
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,
                            modifier = Modifier.fillMaxWidth()
                            ,color = MaterialTheme.colorScheme.onPrimary

                        )
                        Spacer(modifier = Modifier.padding(5.dp))
                        Text(
                            text = "Materi yang dibawakan sesuai perberkembang IT saat ini, sehingga Anda tidak ketinggalan perkembangan teknologi terkini.",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                            ,color = MaterialTheme.colorScheme.onPrimary

                        )
                    }
                )
            }
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxWidth(),
                    content = {
                        Image(
                            painter = rememberAsyncImagePainter("https://dicoding-web-img.sgp1.cdn.digitaloceanspaces.com/original/commons/new-ui-bg-dots.png"),
                            contentDescription = "img",
                            contentScale = ContentScale.Crop,
                            alignment = Alignment.TopCenter,
                            modifier = Modifier
                                .fillMaxSize()
                                .heightIn(min = 490.dp)
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxSize(),
                            content = {
                                Spacer(modifier = Modifier.padding(20.dp))
                                Text(
                                    text = "Upcoming Events",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = 25.sp,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                )
                                Text(
                                    text = "Jangan ketinggalan event-event yang akan datang. Pilihlah sesuai dengan minat Anda dan silakan hadir di kota terdekat Anda.",
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .fillMaxWidth()
                                )
                                Spacer(modifier = Modifier.padding(20.dp))
                                if (isLoadingOnGoing) {
                                    Column(
                                        modifier = Modifier
                                            .padding(top = 80.dp)
                                            .fillMaxSize(),
                                        verticalArrangement = Arrangement.Center,
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        content = {
                                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                                            Text(
                                                "Loading...",
                                                fontSize = 10.sp,
                                                modifier = Modifier.padding(5.dp)
                                            )
                                        }
                                    )
                                } else {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        content = {
                                            for (i in dataDicodingEventOnGoing) {
                                                CustomCard(
                                                    id = i.id,
                                                    category = i.category,
                                                    img = i.imageLogo,
                                                    name = i.name,
                                                    ownerName = i.ownerName,
                                                    summary = i.summary,
                                                    beginTime = i.beginTime,
                                                    endTime = i.endTime,
                                                    onClick = { id ->
                                                        Log.d(
                                                            "Get id card up coming",
                                                            i.id.toString()
                                                        )
                                                        Log.d(
                                                            "Get id card up coming",
                                                            id.toString()
                                                        )
                                                        navController.navigate("Detail/${id}")
                                                    }
                                                )
                                                Spacer(modifier = Modifier.padding(10.dp))
                                            }
                                        }
                                    )
                                }
                            }
                        )
                    }
                )
            }
            item {
                Column(
                    modifier = Modifier.fillParentMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    content = {
                        Text(
                            text = "Successful Events",
                            fontSize = 25.sp,
                            modifier = Modifier.padding(10.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = "Simak Event mana saja yang sukses diselenggarakan. Acara-acara ini punya reputasi networking yang luas, follow-up action yang positif, dan tentunya sesak dihadiri lebih dari 100 persen target peserta. Penasaran?",
                            modifier = Modifier.padding(10.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                )
            }
            if (isLoadingFinished) {
                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 40.dp, bottom = 40.dp)
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
                            Text("Loading...", fontSize = 10.sp, modifier = Modifier.padding(5.dp))
                        }
                    )
                }
            } else {
                items(
                    items = dataDicodingEventFinished,
                    itemContent = { finished ->
                        Box(
                            modifier = Modifier.padding(10.dp),
                            content = {
                                CustomCard(
                                    id = finished.id,
                                    category = finished.category,
                                    img = finished.imageLogo,
                                    name = finished.name,
                                    ownerName = finished.ownerName,
                                    summary = finished.summary,
                                    beginTime = finished.beginTime,
                                    endTime = finished.endTime,
                                    onClick = { id ->
                                        Log.d("Get id card up coming", finished.id.toString())
                                        Log.d("Get id card up coming", id.toString())
                                        navController.navigate("Detail/${id}")
                                    }
                                )
                            }
                        )
                    }
                )
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun PageHomePrev() {
    PageHome(
        rememberNavController(),
        scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    )
}