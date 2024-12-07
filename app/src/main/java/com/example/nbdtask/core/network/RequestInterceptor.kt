package com.example.nbdtask.core.network

import android.app.Application
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

class RequestInterceptor @Inject constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request()
            request = request.newBuilder()
//                .addHeader("Content-Type", "application/json")
//                .addHeader("Accept", "application/json")
//                .addHeader("Authorization", "Bearer c9c94be224b73e9da6e1e95d208b2090165010bbdf8b79244083524f491bd39b")
                .build()
            return chain.proceed(request)
    }
}