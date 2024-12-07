package com.example.nbdtask.domain.usecase

import com.example.nbdtask.data.remote.model.responses.LocalAlertList
import com.example.nbdtask.domain.repository.LocalAlertsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

class GetLocalAlertsUseCase @Inject constructor(
    private val localAlertsRepository: LocalAlertsRepository
) {
    suspend fun invoke(): Flow<LocalAlertList> =
        localAlertsRepository.getLocalAlerts()
}