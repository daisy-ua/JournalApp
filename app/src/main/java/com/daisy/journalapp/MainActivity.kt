package com.daisy.journalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.daisy.journalapp.navigation.graph.JourneyNavGraph
import com.daisy.journalapp.ui.theme.JournalAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JournalAppTheme(darkTheme = true) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    JourneyNavGraph(rememberNavController())
                }
            }
        }
    }
}