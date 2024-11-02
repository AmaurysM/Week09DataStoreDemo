package edu.farmingdale.datastoredemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import edu.farmingdale.datastoredemo.ui.EmojiReleaseApp
import edu.farmingdale.datastoredemo.ui.theme.DataStoreDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Get DataStore Preference
            val userPreferencesRepository =
                (application as EmojiReleaseApplication).userPreferencesRepository
            val isDarkTheme = userPreferencesRepository.isDarkTheme.collectAsState(initial = false)
            // Apply the theme
            DataStoreDemoTheme(darkTheme = isDarkTheme.value) {
                EmojiReleaseApp()
            }
        }
    }
}
