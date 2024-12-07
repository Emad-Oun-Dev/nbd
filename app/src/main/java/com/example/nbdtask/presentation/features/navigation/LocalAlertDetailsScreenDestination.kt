package com.example.nbdtask.presentation.features.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nbdtask.presentation.features.alertDetails.LocalAlertsDetailsScreen
import com.example.nbdtask.presentation.features.alertDetails.LocalAlertsDetailsViewModel

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

@Composable
fun LocalAlertDetailsScreenDestination() {
    val viewModel = hiltViewModel<LocalAlertsDetailsViewModel>()
    LocalAlertsDetailsScreen(state = viewModel.viewState.value)
}
