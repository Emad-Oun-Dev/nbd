package com.example.nbdtask.presentation.features.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.nbdtask.presentation.features.localAlerts.LocalAlertsScreen
import com.example.nbdtask.presentation.features.localAlerts.LocalAlertsSideEffects
import com.example.nbdtask.presentation.features.localAlerts.LocalAlertsViewModel
import com.example.nbdtask.utils.LocalAlertDetailsScreen

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

@Composable
fun LocalAlertScreenDestination(
    navController: NavController
) {
    val viewModel = hiltViewModel<LocalAlertsViewModel>()
    LocalAlertsScreen(state = viewModel.viewState.value,
        effectFlow = viewModel.effect,
        onEventSent = { event -> viewModel.setEvent(event) },
        onNavigationRequested = { navigation ->
            when (navigation) {
                is LocalAlertsSideEffects.Navigation.OpenDetailsScreen -> {
                    navController.navigate(route = "$LocalAlertDetailsScreen/${navigation.time}")
                }
            }
        })
}
