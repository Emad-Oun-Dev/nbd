package com.example.nbdtask.presentation.features.alertDetails

import androidx.lifecycle.SavedStateHandle
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.nbdtask.core.base.BaseViewModel
import com.example.nbdtask.presentation.features.localAlerts.LocalAlertsEvent
import com.example.nbdtask.presentation.features.localAlerts.LocalAlertsSideEffects
import com.example.nbdtask.presentation.features.localAlerts.LocalAlertsState
import com.example.nbdtask.presentation.features.localAlerts.ReminderWorker
import com.example.nbdtask.utils.TIME_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

@HiltViewModel
class LocalAlertsDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle) :
    BaseViewModel<LocalAlertsDetailsEvent, LocalAlertsDetailsState, LocalAlertDetailsSideEffects>() {

    private val timeValue: Long = checkNotNull(savedStateHandle[TIME_KEY])

    private lateinit var workManager: WorkManager


    init {
        setState {
            copy(
                time = timeValue
            )
        }
    }
    override fun setInitialState() = LocalAlertsDetailsState(
        time = 0
    )


    fun setWorkManager(workManager: WorkManager) {
        this.workManager = workManager
    }

    override fun handleEvents(event: LocalAlertsDetailsEvent) {
    }

}