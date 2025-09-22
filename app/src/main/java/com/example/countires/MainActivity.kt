package com.example.countires

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.example.countires.navigation.CountryNavigation
import com.example.countires.ui.theme.CountryExplorerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CountryExplorerTheme {
                val navController = rememberNavController()
                CountryNavigation(navController = navController)
            }
        }
    }
}