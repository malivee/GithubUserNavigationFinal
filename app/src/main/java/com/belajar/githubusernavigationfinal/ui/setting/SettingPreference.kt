package com.belajar.githubusernavigationfinal.ui.setting

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")

class SettingPreference private constructor(private val dataStore: DataStore<Preferences>) {

    val DARK_MODE = booleanPreferencesKey("dark_mode")

    fun getDarkMode(): Flow<Boolean> {
        return dataStore.data.map {
            it[DARK_MODE] ?: false
        }
    }

    suspend fun saveDarkMode(isActive: Boolean) {
        dataStore.edit {
            it[DARK_MODE] = isActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingPreference? = null
        fun getInstance(dataStore: DataStore<Preferences>): SettingPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}