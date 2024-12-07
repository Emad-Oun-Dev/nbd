package com.example.nbdtask.data.remote.api

import com.example.nbdtask.data.LOCAL_ALERTS
import com.example.nbdtask.data.remote.model.responses.LocalAlertList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

interface LocalAlertsApi {
    @Headers ("Content-Type: text/xml")
    @GET(LOCAL_ALERTS)
    suspend fun getLocalAlerts(): Response<LocalAlertList>
}
