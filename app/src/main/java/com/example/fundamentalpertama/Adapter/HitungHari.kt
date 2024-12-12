package com.example.fundamentalpertama.Adapter

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

fun HitungHari(beginTime: String,endTime: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val eventBeginTime = LocalDateTime.parse(beginTime, formatter)
    val eventEndTime = LocalDateTime.parse(endTime, formatter)
    val currentTime = LocalDateTime.now()

    return when {
        currentTime.isAfter(eventEndTime) -> "Selesai"
        currentTime.isBefore(eventBeginTime) -> {
            val daysLeft = ChronoUnit.DAYS.between(currentTime, eventBeginTime)
            """
                Waiting List
                $daysLeft Hari Lagi
            """.trimIndent()
        }
        else -> "Sedang berlangsung" // Jika waktu saat ini di antara waktu mulai dan berakhir
    }
}