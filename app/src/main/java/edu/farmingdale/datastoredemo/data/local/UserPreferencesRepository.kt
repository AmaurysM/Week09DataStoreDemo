package edu.farmingdale.datastoredemo.data.local

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

/*
 * Concrete class implementation to access data store
 */
class UserPreferencesRepository(
    private val dataStore: DataStore<Preferences>
) {

    private companion object {
        val is_linear_layout = booleanPreferencesKey("is_linear_layout")
        // added another key for theme
        val is_dark_theme = booleanPreferencesKey("is_dark_theme")
        const val TAG = "UserPreferencesRepo"
    }

    private fun <T> getPreference(key: Preferences.Key<T>, defaultValue: T): Flow<T> =
        dataStore.data
            .catch { exception ->
                if (exception is IOException) {
                    Log.e(TAG, "Error reading preferences.", exception)
                    emit(emptyPreferences())
                } else {
                    throw exception
                }
            }
            .map { preferences ->
                preferences[key] ?: defaultValue
            }

    val isLinearLayout: Flow<Boolean> = getPreference(is_linear_layout, true)
    // added another key for theme
    val isDarkTheme: Flow<Boolean> = getPreference(is_dark_theme, true)

    suspend fun saveLayoutPreference(isLinearLayout: Boolean) {
        dataStore.edit { preferences ->
            preferences[is_linear_layout] = isLinearLayout
        }
    }

    // added another function to save theme
    suspend fun saveThemePreference(isDarkTheme: Boolean) {
        dataStore.edit { preferences ->
            preferences[is_dark_theme] = isDarkTheme
        }
    }

}
