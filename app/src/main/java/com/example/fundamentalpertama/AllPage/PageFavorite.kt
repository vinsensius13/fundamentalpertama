package com.example.fundamentalpertama.AllPage

import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fundamentalpertama.AllComponent.CustomCard.CustomCard
import com.example.fundamentalpertama.Room.FavoriteViewModel

@Composable
fun PageFavorite(navController: NavController) {
    // Mengambil instance dari FavoriteViewModel
    val favoriteViewModel: FavoriteViewModel = viewModel()
    // Mengamati data favorit
    val favoriteEvents by favoriteViewModel.allFavorites.observeAsState(emptyList())

    // Mengambil data dari ViewModel saat composable pertama kali diluncurkan
    LazyColumn(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp).fillMaxSize(),
        content = {
            item {
                Spacer(modifier = Modifier.padding(10.dp))
            }
            items(
                items = favoriteEvents,
                itemContent = {
                    CustomCard(
                        id = it.id,
                        category = it.catagory,
                        img = it.img,
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
    )
}