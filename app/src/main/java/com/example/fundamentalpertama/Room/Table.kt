package com.example.fundamentalpertama.Room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_favorite")
data class Table(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val img: String,
    val catagory: String,
    val name: String,
    val ownerName: String,
    val summary: String,
    val beginTime: String,
    val endTime: String,
    val isFavorite: Boolean = false
)