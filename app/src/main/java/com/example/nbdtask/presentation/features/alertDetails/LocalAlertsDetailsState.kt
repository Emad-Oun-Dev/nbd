package com.example.nbdtask.presentation.features.alertDetails

import com.example.nbdtask.core.base.ViewState
import com.example.nbdtask.data.remote.model.responses.LocalAlertItemResponse

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

data class LocalAlertsDetailsState(
    val time: Long,
) : ViewState
