package com.example.airquality

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") {
            AirQualityScreen(onViewCities = { navController.navigate("cities") })
        }
        composable("cities") {
            CityListScreen(onBack = { navController.popBackStack() })
        }
    }
}