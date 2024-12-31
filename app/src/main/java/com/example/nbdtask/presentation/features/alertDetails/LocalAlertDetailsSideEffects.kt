package com.example.nbdtask.presentation.features.alertDetails

import com.example.nbdtask.core.base.ViewSideEffect
import com.example.nbdtask.presentation.features.localAlerts.LocalAlertsSideEffects

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

sealed class LocalAlertDetailsSideEffects : ViewSideEffect {

    sealed class Navigation : LocalAlertDetailsSideEffects() {
        object CloseScreen : Navigation()
    }
}
