package com.example.nbdtask.presentation.features.localAlerts

import com.example.nbdtask.core.base.ViewEvent


/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */
sealed class LocalAlertsEvent : ViewEvent {
    object GetAllLocalAlerts : LocalAlertsEvent()
    object ReCallApi : LocalAlertsEvent()
    data class OpenDetailsScreen(val time: Long) : LocalAlertsEvent()

}
