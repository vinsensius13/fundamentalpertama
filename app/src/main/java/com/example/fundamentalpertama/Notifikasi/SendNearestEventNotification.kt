package com.example.fundamentalpertama.Notifikasi

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import java.util.concurrent.TimeUnit

fun scheduleDailyReminder(context: Context,name: String,img: String,hitungHari: String) {
    createNotificationChannel(context)

    val getData = Data.Builder()
        .putString("name",name)
        .putString("img",img)
        .putString("hitungHari",hitungHari)
        .build()

    val dailyReminderRequest: WorkRequest = OneTimeWorkRequestBuilder<DailyReminderWorker>()
        .setInitialDelay(1, TimeUnit.SECONDS)
        .setInputData(getData)
        .addTag("notification_reminder")
        .build()

    WorkManager.getInstance(context).enqueue(dailyReminderRequest)
}