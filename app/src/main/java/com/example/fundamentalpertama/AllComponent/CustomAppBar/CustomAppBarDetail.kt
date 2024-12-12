package com.example.fundamentalpertama.AllComponent.CustomAppBar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetail(navController: NavController) {
    TopAppBar(
        modifier = Modifier.shadow(
            elevation = 10.dp,
            ambientColor = Color(0xFF00B0FF),
            spotColor = Color(0xFF00B0FF)
        ),
        title = {},
        colors = TopAppBarDefaults.topAppBarColors(Color(0xFF2D2838)),
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                },
                content = {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back", tint = Color.White)
                }
            )
        }
    )
}

