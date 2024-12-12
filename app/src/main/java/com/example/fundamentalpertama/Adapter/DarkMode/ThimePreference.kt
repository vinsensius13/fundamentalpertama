package com.example.fundamentalpertama.Adapter.DarkMode

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class ThemePreferances(private val context: Context) {

    private object PreferencesKeys {
        val DARK_THEME = booleanPreferencesKey("dark_theme")
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notification_enabled") // Kunci untuk notifikasi
    }

    val isDarkTheme: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.DARK_THEME] ?: false
    }

    val isNotificationEnabled: Flow<Boolean> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.NOTIFICATION_ENABLED] ?: false // Ambil status notifikasi
    }

    suspend fun saveTheme(isDark: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_THEME] = isDark
        }
    }

    suspend fun saveNotificationEnabled(isEnabled: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.NOTIFICATION_ENABLED] = isEnabled // Simpan status notifikasi
        }
    }

    suspend fun getNotificationEnabled(): Boolean {
        return context.dataStore.data.map { preferences ->
            preferences[PreferencesKeys.NOTIFICATION_ENABLED] ?: false // Ambil status notifikasi
        }.first() // Mengambil nilai dengan first() untuk mendapatkan hasil Flow
    }
}