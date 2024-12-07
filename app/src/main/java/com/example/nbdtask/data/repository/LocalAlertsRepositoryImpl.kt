package com.example.nbdtask.data.repository

import com.example.nbdtask.core.base.BaseRepo
import com.example.nbdtask.data.remote.model.responses.LocalAlertItemResponse
import com.example.nbdtask.data.remote.api.LocalAlertsApi
import com.example.nbdtask.data.remote.model.responses.LocalAlertList
import com.example.nbdtask.domain.repository.LocalAlertsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

class LocalAlertsRepositoryImpl @Inject constructor(
    private val localAlertsApi: LocalAlertsApi,
) : LocalAlertsRepository, BaseRepo() {

    override suspend fun getLocalAlerts(): Flow<LocalAlertList> {
        return flow {
            safeApiCall {
                localAlertsApi.getLocalAlerts()
            }.data?.let { emit(it) }
        }
    }
}
