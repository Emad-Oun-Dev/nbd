package com.example.nbdtask.presentation.features.localAlerts

import com.example.nbdtask.core.base.ViewSideEffect

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

sealed class LocalAlertsSideEffects : ViewSideEffect {
    data class ShowErrorMsg(val msg: String) : LocalAlertsSideEffects()
    data class ShowMsg(val msg: Int) : LocalAlertsSideEffects()
    object ShowReloadButton : LocalAlertsSideEffects()

    sealed class Navigation : LocalAlertsSideEffects() {
        data class OpenDetailsScreen(val time: Long) : Navigation()
    }
}
