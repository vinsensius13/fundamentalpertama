package com.example.fundamentalpertama.AllPage

import android.Manifest
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.WorkManager
import com.example.fundamentalpertama.API.ApiViewModel.DataDicodingEventViewModel
import com.example.fundamentalpertama.API.DataDicodingEvent
import com.example.fundamentalpertama.Adapter.DarkMode.ThemePreferances
import com.example.fundamentalpertama.Adapter.HitungHari
import com.example.fundamentalpertama.Notifikasi.scheduleDailyReminder
import kotlinx.coroutines.launch

@Composable
fun PageSettings(themePreferenceManager: ThemePreferances) {
    val dataDicodingEventViewModel: DataDicodingEventViewModel = viewModel()
    var dataDicodingEventNotif by remember { mutableStateOf<DataDicodingEvent?>(null) }
    var checkedDarkMode by remember { mutableStateOf(false) }
    var checkedDailyReminder by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val createContext = LocalContext.current



    LaunchedEffect(Unit) {
        dataDicodingEventViewModel.fetchEvents(
            onResult = { fetch ->
                dataDicodingEventNotif = fetch
            }
        )
    }

    LaunchedEffect(Unit) {
        themePreferenceManager.isDarkTheme.collect { isDarkMode ->
            checkedDarkMode = isDarkMode
        }
    }
    LaunchedEffect(Unit) {
        themePreferenceManager.isNotificationEnabled.collect { isEnabled ->
            checkedDailyReminder = isEnabled
        }
    }

    val hitungHari = if (dataDicodingEventNotif != null) {
        HitungHari(dataDicodingEventNotif?.beginTime ?: "", dataDicodingEventNotif?.endTime ?: "")
    } else {
        "Data tidak tersedia"
    }
//     Perizinan notifikasi
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                // Izin diberikan, jalankan notifikasi
                scheduleDailyReminder(
                    context = createContext,
                    name = dataDicodingEventNotif?.name.toString(),
                    img = dataDicodingEventNotif?.imageLogo.toString(),
                    hitungHari = "10"
                )
            }
        }
    )


    Column(
        modifier = Modifier.padding(20.dp),
        content = {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                content = {
                    val (tex1, button) = createRefs()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .constrainAs(
                                ref = tex1,
                                constrainBlock = {
                                    start.linkTo(parent.start)
                                }
                            )
                            .fillMaxHeight(),
                        content = {
                            Text(
                                text = "Dark Mode",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Enable Dark Mode",
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 10.sp
                            )
                        }
                    )
                    Switch(
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.LightGray,
                            checkedTrackColor = Color.DarkGray,
                            uncheckedThumbColor = Color.DarkGray,
                            uncheckedTrackColor = Color.LightGray
                        ),
                        modifier = Modifier.constrainAs(
                            ref = button,
                            constrainBlock = {
                                end.linkTo(parent.end)
                            }
                        ),
                        checked = checkedDarkMode,
                        onCheckedChange = { isChecked ->
                            checkedDarkMode = isChecked
                            if (checkedDarkMode) {
                                Toast.makeText(createContext, "DARK MODE", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(createContext, "LIGHT MODE", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            coroutineScope.launch {
                                themePreferenceManager.saveTheme(isDark = isChecked)
                            }
                        }
                    )
                }
            )
            Spacer(modifier = Modifier.padding(5.dp))
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                content = {
                    val (tex1, button) = createRefs()
                    Column(
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier
                            .constrainAs(
                                ref = tex1,
                                constrainBlock = {
                                    start.linkTo(parent.start)
                                }
                            )
                            .fillMaxHeight(),
                        content = {
                            Text(
                                text = "Dily reminder",
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Enable Notification",
                                fontWeight = FontWeight.Light,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontSize = 10.sp
                            )
                        }
                    )
                    Switch(
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.LightGray,
                            checkedTrackColor = Color.DarkGray,
                            uncheckedThumbColor = Color.DarkGray,
                            uncheckedTrackColor = Color.LightGray),
                        modifier = Modifier.constrainAs(
                            ref = button,
                            constrainBlock = {
                                end.linkTo(parent.end)
                            }
                        ),
                        checked = checkedDailyReminder,
                        onCheckedChange = { isCek ->
                            checkedDailyReminder = isCek
                            if (checkedDailyReminder) {
                                Toast.makeText(createContext, "Notifikasi ON", Toast.LENGTH_SHORT)
                                    .show()
                            } else {
                                Toast.makeText(createContext, "Notifikasi OF", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            if (isCek) {
                                // Memeriksa izin notifikasi
                                if (ActivityCompat.checkSelfPermission(
                                        createContext,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    ) != PackageManager.PERMISSION_GRANTED
                                ) {
                                    notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                } else {
                                    scheduleDailyReminder(
                                        context = createContext,
                                        name = dataDicodingEventNotif?.name.toString(),
                                        img = dataDicodingEventNotif?.imageLogo.toString(),
                                        hitungHari = hitungHari
                                    )
                                }
                            } else {
                                WorkManager.getInstance(createContext)
                                    .cancelAllWorkByTag("notification_reminder")
                            }
                            coroutineScope.launch {
                                themePreferenceManager.saveNotificationEnabled(isCek)
                            }
                        }
                    )

                }
            )
        }
    )
}


@Preview
@Composable
private fun PageSettingsPrev() {
    PageSettings(
        ThemePreferances(context = LocalContext.current)
    )
}