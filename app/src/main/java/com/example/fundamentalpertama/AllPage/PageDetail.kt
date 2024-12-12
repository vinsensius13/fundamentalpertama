package com.example.fundamentalpertama.AllPage

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.fundamentalpertama.API.ApiViewModel.DataDicodingEventViewModel
import com.example.fundamentalpertama.API.DataDicodingEvent
import com.example.fundamentalpertama.NetworkMonitor.NetworkMonitor
import com.example.fundamentalpertama.Room.FavoriteViewModel
import org.jsoup.Jsoup

@Composable
fun PageDetail(
    getId: Int
) {
    val favoriteViewModel: FavoriteViewModel = viewModel()
    val dataDicodingEventViewModel: DataDicodingEventViewModel = viewModel()
    var dataDicodingEventDetail by remember { mutableStateOf<DataDicodingEvent?>(null) }
    val createContext = LocalContext.current
    var isLoading by remember { mutableStateOf(true) }
    val netWorkMonitor = remember { NetworkMonitor(context = createContext) }
    val dataDicodingEventDetailCache = dataDicodingEventViewModel.eventDetailCache.find { it.id == getId }
    var isFavorite by remember { mutableStateOf(false) }

    if (dataDicodingEventDetailCache != null) {
        dataDicodingEventDetail = dataDicodingEventDetailCache
        isLoading = false
    } else {
        isLoading = true
    }

    fun fetchPagedetail() {
        dataDicodingEventViewModel.fetchDetailDicodingEvent(
            getId = getId,
            onResult = { fetch ->
                dataDicodingEventDetail = fetch
                isLoading = false
            }
        )
    }

    LaunchedEffect(getId) {
        netWorkMonitor.startNetworkCallback(
            onNetworkAvailable = {
                fetchPagedetail()
            }
        )
        if (dataDicodingEventDetailCache == null) {
            fetchPagedetail()
        }
         favoriteViewModel.isItemFavorite(getId){
            isFavoriteResult -> isFavorite = isFavoriteResult
        }
    }

    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            content = {
                dataDicodingEventDetail?.let { data ->
                    Image(
                        painter = rememberAsyncImagePainter(data.imageLogo),
                        contentDescription = "image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .padding(10.dp)
                            .shadow(5.dp, RoundedCornerShape(10.dp))
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                    FloatingActionButton(
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(end = 40.dp)
                            .offset(y = -40.dp)
                            .clip(CircleShape),
                        onClick = {
                        isFavorite = !isFavorite
                        val table = com.example.fundamentalpertama.Room.Table(
                            id = data.id,
                            catagory = data.category,
                            img = data.imageLogo,
                            name = data.name,
                            ownerName = data.ownerName,
                            summary = data.summary,
                            beginTime = data.beginTime,
                            endTime = data.endTime,
                            isFavorite = isFavorite
                        )
                        if (isFavorite) { favoriteViewModel.addFavorite(table) }
                        else { favoriteViewModel.deleteFavorite(table) }
                    }) {
                        Icon(imageVector =
                        if (isFavorite) androidx.compose.material.icons.Icons.Default.Favorite else androidx.compose.material.icons.Icons.Default.FavoriteBorder , contentDescription = "" )
                    }
                    Box(
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 10.dp, top = 20.dp)
                            .border(
                                width = 2.dp,
                                shape = RoundedCornerShape(5.dp),
                                color = Color(0xFF565656)
                            ),
                        content = {
                            Text(
                                text = data.category,
                                modifier = Modifier.padding(
                                    top = 0.dp,
                                    bottom = 0.dp,
                                    start = 10.dp,
                                    end = 10.dp
                                ),
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 10.sp
                            )
                        }
                    )
                    Text(
                        text = data.name,
                        fontSize = 30.sp,
                        modifier = Modifier.padding(start = 10.dp, bottom = 10.dp),
                        lineHeight = 40.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Text(
                        text = "Diselenggarakan oleh: ${data.ownerName}",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Light,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(start = 10.dp, bottom = 20.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            Text(text = "Dibuka Sampai:",
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = data.beginTime,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(text = "Sisa Kuota:",
                                color = MaterialTheme.colorScheme.onPrimary)
                            Text(
                                text = "${data.quota - data.registrants}",
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(text = "Registrant:")
                            Text(
                                text = data.registrants.toString(),
                                fontWeight = FontWeight.Bold
                            )
                        }
                    )

                    Text(
                        text = "Deskripsi",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 20.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    val document = Jsoup.parse(data.description)
                    val images = document.select("img").map { it.attr("src") }
                    val htmlContent = document.body().html()

                    images.forEach { imageUrl ->
                        Image(
                            painter = rememberAsyncImagePainter(imageUrl),
                            contentDescription = "image from description",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .padding(10.dp)
                                .shadow(5.dp, RoundedCornerShape(10.dp))
                                .fillMaxWidth()
                                .height(200.dp)
                        )

                    Text(
                        text = HtmlCompat.fromHtml(htmlContent, HtmlCompat.FROM_HTML_MODE_LEGACY).toString(),
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxSize(),
                        fontSize = 14.sp,
                        lineHeight = 20.sp,
                        textAlign = TextAlign.Justify,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    }

                    Button(
                        modifier = Modifier
                            .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 50.dp)
                            .fillMaxWidth()
                            .height(60.dp)
                            .border(2.dp, Color.Transparent, RoundedCornerShape(10.dp)),
                        colors = ButtonDefaults.buttonColors(
                            Color(0xFFEE299B)
                        ),
                        onClick = {
                            val intent =
                                Intent(Intent.ACTION_VIEW, Uri.parse(data.link))
                            createContext.startActivity(intent)
                        },
                        content = {
                            Text(text = "Registrasi",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onPrimary)
                        }
                    )
                }
            }
        )
    }
}
