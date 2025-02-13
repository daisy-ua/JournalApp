package com.daisy.journalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.daisy.journalapp.authentication.ui.auth.AuthScreen
import com.daisy.journalapp.authentication.ui.login.LoginScreen
import com.daisy.journalapp.ui.theme.JournalAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JournalAppTheme(darkTheme = true) {
                LoginScreen()
            }
        }
    }
}