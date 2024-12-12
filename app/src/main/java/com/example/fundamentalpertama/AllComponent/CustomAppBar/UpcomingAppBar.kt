package com.example.fundamentalpertama.AllComponent.CustomAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpComingBar() {
    TopAppBar(
        modifier = Modifier.shadow(
            40.dp,
            ambientColor = Color(0xFF00B0FF),
            spotColor = Color(0xFF00B0FF)
        ),
        colors = TopAppBarDefaults.topAppBarColors(
            Color(0xFF2D2838)
        ),
        title = {
            Text(text = "Up Coming", color = Color.White)
        }
    )
}

@Preview
@Composable
private fun UpComingAppBarPrev() {
    UpComingBar()
}