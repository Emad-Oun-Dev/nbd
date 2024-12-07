package com.example.nbdtask.domain.repository

import com.example.nbdtask.data.remote.model.responses.LocalAlertList
import kotlinx.coroutines.flow.Flow

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

interface LocalAlertsRepository {
  suspend fun getLocalAlerts(): Flow<LocalAlertList>
}