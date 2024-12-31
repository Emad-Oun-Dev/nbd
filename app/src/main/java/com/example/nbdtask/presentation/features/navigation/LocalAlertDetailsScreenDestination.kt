package com.example.nbdtask.presentation.features.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nbdtask.presentation.features.alertDetails.LocalAlertDetailsSideEffects
import com.example.nbdtask.presentation.features.alertDetails.LocalAlertsDetailsScreen
import com.example.nbdtask.presentation.features.alertDetails.LocalAlertsDetailsViewModel
import com.example.nbdtask.presentation.features.localAlerts.LocalAlertsSideEffects
import com.example.nbdtask.utils.LocalAlertDetailsScreen

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

@Composable
fun LocalAlertDetailsScreenDestination(
    navController: NavController
) {
    val viewModel = hiltViewModel<LocalAlertsDetailsViewModel>()
    LocalAlertsDetailsScreen(state = viewModel.viewState.value,
        onNavigationRequested = { navigation ->
            when (navigation) {
                is LocalAlertDetailsSideEffects.Navigation.CloseScreen -> {
                    navController.popBackStack()
                }
            }
        })
}
