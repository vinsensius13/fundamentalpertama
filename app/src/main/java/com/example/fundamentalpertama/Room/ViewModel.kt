package com.example.fundamentalpertama.Room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application): AndroidViewModel(application = application) {
    private val favoriteDao: FavoriteDao
    val allFavorites: LiveData<List<Table>>

    init {
        val db = FavoriteDatabase.getDatabase(application)
        favoriteDao = db.favoriteDao()
        allFavorites = favoriteDao.getFavorite()
    }

    fun addFavorite(table: Table) {
        viewModelScope.launch {
            favoriteDao.insertFavorite(table = table)
        }
    }

    fun deleteFavorite(table: Table) {
        viewModelScope.launch {
            favoriteDao.deleteFavorite(table = table)
        }
    }

    fun isItemFavorite(id: Int, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isFavorite = favoriteDao.getFavoriteId(id).isNotEmpty()  // Jika id ditemukan
            onResult(isFavorite) // Kirim hasil pengecekan
        }
    }

    fun toggleFavorite(table: Table) {
        val update = table.copy(isFavorite = !table.isFavorite)
        viewModelScope.launch {
            favoriteDao.insertFavorite(update)
        }
    }

}