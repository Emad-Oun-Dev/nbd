package com.example.nbdtask.presentation.features.localAlerts

import androidx.lifecycle.viewModelScope
import com.example.nbdtask.core.base.BaseViewModel
import com.example.nbdtask.domain.exception.NetworkException
import com.example.nbdtask.domain.usecase.GetLocalAlertsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

@HiltViewModel
class LocalAlertsViewModel @Inject constructor(
    private val getLocalAlertsUseCase: GetLocalAlertsUseCase,
) :
    BaseViewModel<LocalAlertsEvent, LocalAlertsState, LocalAlertsSideEffects>() {


    init {
        getLocalAlerts()
    }

    override fun setInitialState() = LocalAlertsState(
        isLoading = false
    )

    private fun getLocalAlerts() {
        viewModelScope.launch {
            setState { copy(isLoading = true) }
            getLocalAlertsUseCase.invoke().catch {
                Timber.d("config_error==${it.message}")
                if (it is NetworkException){
                    setEffect {
                        LocalAlertsSideEffects.ShowReloadButton
                    }
                }
                setState {
                    copy(
                        isLoading = false
                    )
                }
                setEffect {
                    LocalAlertsSideEffects.ShowErrorMsg(it.message ?: "")
                }
            }.collect { remoteResponse ->
                setState {
                    copy(
                        isLoading = false,
                        localAlertsList = remoteResponse?.result ?: emptyList()
                    )
                }
            }
        }
    }

    override fun handleEvents(event: LocalAlertsEvent) {
        when (event) {
            is LocalAlertsEvent.GetAllLocalAlerts -> {
                getLocalAlerts()
            }

            is LocalAlertsEvent.OpenDetailsScreen -> {
                setEffect { LocalAlertsSideEffects.Navigation.OpenDetailsScreen(event.time) }
            }

            is LocalAlertsEvent.ReCallApi -> {
                getLocalAlerts()
            }
        }
    }
}