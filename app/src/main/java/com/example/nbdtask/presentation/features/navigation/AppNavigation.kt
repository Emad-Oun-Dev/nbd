
package com.example.nbdtask.presentation.features.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nbdtask.utils.LocalAlertDetailsScreen
import com.example.nbdtask.utils.LocalAlertScreen
import com.example.nbdtask.utils.TIME_KEY

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = LocalAlertScreen
    ) {
        composable(
            route = LocalAlertScreen
        ) {
            LocalAlertScreenDestination(navController)
        }

        composable(
            route = "$LocalAlertDetailsScreen/{$TIME_KEY}",
            arguments = listOf(navArgument(name = TIME_KEY) {
                type = NavType.LongType
            })
        ){
            LocalAlertDetailsScreenDestination()
        }

    }
}


