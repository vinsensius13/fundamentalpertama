package com.example.fundamentalpertama

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fundamentalpertama.API.ApiViewModel.DataDicodingEventViewModel
import com.example.fundamentalpertama.API.DataDicodingEvent
import com.example.fundamentalpertama.Adapter.DarkMode.DarkModeAndLight
import com.example.fundamentalpertama.Adapter.DarkMode.ThemePreferances
import com.example.fundamentalpertama.AllComponent.CustomAppBar.AppDetail
import com.example.fundamentalpertama.AllComponent.CustomAppBar.FinishedTopBar
import com.example.fundamentalpertama.AllComponent.CustomAppBar.HomeBar
import com.example.fundamentalpertama.AllComponent.CustomAppBar.UpComingBar
import com.example.fundamentalpertama.AllComponent.CustomBottomBar.BottomBar
import com.example.fundamentalpertama.AllPage.PageDetail
import com.example.fundamentalpertama.AllPage.PageFavorite
import com.example.fundamentalpertama.AllPage.PageFinished
import com.example.fundamentalpertama.AllPage.PageHome
import com.example.fundamentalpertama.AllPage.PageSettings
import com.example.fundamentalpertama.AllPage.PageUpComing


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val themePreferenceManager = ThemePreferances(applicationContext)
        setContent {
            val isDarkMode =
                themePreferenceManager.isDarkTheme.collectAsState(initial = false).value
            DarkModeAndLight().DarkModeTheme(darkTheme = isDarkMode){
                val navControler = rememberNavController()
                var getRouteAppBar by remember {
                    mutableStateOf("")
                }
                var inputSearchFinished by remember {
                    mutableStateOf("")
                }
                val dataDicodingEventViewModel: DataDicodingEventViewModel = viewModel()
                var searchResult by remember {
                    mutableStateOf<List<DataDicodingEvent>>(emptyList())
                }
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
                val navBackEntry by navControler.currentBackStackEntryAsState()

                LaunchedEffect(navBackEntry?.destination?.route) {
                    getRouteAppBar = navBackEntry?.destination?.route ?: "Home"
                }

                Scaffold(
                    topBar = {
                        when (getRouteAppBar) {
                            "Home" -> HomeBar(scrollBehavior = scrollBehavior)
                            "Detail/{getId}" -> {
                                AppDetail(navController = navControler)
                            }

                            "Up Coming" -> UpComingBar()
                            "Finished" -> FinishedTopBar(
                                inputSearch = inputSearchFinished,
                                onChangeInputSearch = {
                                    inputSearchFinished = it
                                },
                                onSearch = {
                                    dataDicodingEventViewModel.fetchSearchDataDicodingEvent(
                                        inputSearchFinished
                                    ) {
                                        searchResult = it
                                        Log.d("get keyword appbar finished",searchResult.toString())
                                    }
                                }
                            )

                            else -> {
                                HomeBar(scrollBehavior = scrollBehavior)
                            }
                        }
                    },
                    content = {
                        Column(
                            modifier = Modifier.padding(it),
                            content = {
                                NavHost(navController = navControler, startDestination = "Home") {
                                    composable("Home") {
                                        PageHome(
                                            navController = navControler,
                                            scrollBehavior = scrollBehavior
                                        )
                                    }
                                    composable("Up Coming") { PageUpComing(navController = navControler) }
                                    composable("Finished") {
                                        PageFinished(
                                            searchResult = searchResult,
                                            navController = navControler
                                        )
                                    }
                                    composable("Detail/{getId}") { backStackEntry ->
                                        val eventId =
                                            backStackEntry.arguments?.getString("getId")?.toInt()
                                        eventId?.let { set ->
                                            PageDetail(getId = set)
                                            Log.d("setId", set.toString())
                                        }
                                    }
                                    composable("Favorite"){ PageFavorite(navController = navControler)}
                                    composable("Settings"){ PageSettings(themePreferenceManager) }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        if (navBackEntry?.destination?.route != "Detail/{getId}") {
                            BottomBar(
                                navController = navControler,
                                getTopBar = { getRouteAppBar = it }
                            )
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}
