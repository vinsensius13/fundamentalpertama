package com.example.fundamentalpertama.Notifikasi

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.fundamentalpertama.R

class DailyReminderWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        val name = inputData.getString("name") ?: "NAME"
        val img = inputData.getString("img") ?: "INVALID_URL"
        val hitungHari = inputData.getString("hitungHari") ?: "0"

        showNotification(
            context = applicationContext,
            name = name,
            img = img,
            hitungHari = hitungHari
        )
        return Result.success()
    }

    @SuppressLint("MissingPermission")
    private fun showNotification(context: Context, name: String, img: String, hitungHari: String) {
        val notificationId = 1
        val channelId = "daily_reminder_channel"
        Glide.with(context)
            .asBitmap()
            .load(img)
            .into(
                object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        val notification = NotificationCompat.Builder(context, channelId)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(name)
                            .setContentText("sebentar lagi nih acaranya di mulai  $hitungHari")
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setLargeIcon(resource)
                            .setStyle(
                                NotificationCompat.BigPictureStyle()
                                    .bigPicture(resource)
                            )
                            .build()
                        val notificationManager = NotificationManagerCompat.from(context)
                        notificationManager.notify(notificationId, notification)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        TODO("Not yet implemented")
                    }
                }
            )
    }
}