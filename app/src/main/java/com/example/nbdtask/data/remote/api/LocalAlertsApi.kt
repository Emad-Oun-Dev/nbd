package com.example.nbdtask.data.remote.api

import com.example.nbdtask.data.LOCAL_ALERTS
import com.example.nbdtask.data.remote.model.responses.LocalAlertList
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

interface LocalAlertsApi {
    @GET(LOCAL_ALERTS)
    suspend fun getLocalAlerts(): Response<LocalAlertList>
}
