package com.example.countires.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.countires.presentation.screens.CountryDetailScreen
import com.example.countires.presentation.screens.CountryListScreen

@Composable
fun CountryNavigation(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = "country_list"
    ) {
        composable("country_list") {
            CountryListScreen(
                onCountryClick = { countryName ->
                    navController.navigate("country_detail/$countryName")
                }
            )
        }

        composable("country_detail/{countryName}") { backStackEntry ->
            val countryName = backStackEntry.arguments?.getString("countryName") ?: ""
            CountryDetailScreen(
                countryName = countryName,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}