package com.example.nbdtask.core.base

import com.example.nbdtask.domain.exception.ApiException
import com.example.nbdtask.domain.exception.GeneralException
import com.example.nbdtask.core.Resource
import com.example.nbdtask.domain.exception.NetworkException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException


/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

//https://www.geeksforgeeks.org/how-to-handle-api-responses-success-error-in-android/

abstract class BaseRepo {

    // we'll use this function in all
    // repos to handle api errors.
    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        // Returning api response
        // wrapped in Resource class
        return withContext(Dispatchers.IO) {
            try {

                // Here we are calling api lambda
                // function that will return response
                // wrapped in Retrofit's Response class
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    // In case of success response we
                    // are returning Resource.Success object
                    // by passing our data in it.
                    response.body()?.let {
                        Resource.Success(data = it)
                    }?: kotlin.run {
                        Resource.SuccessWithNoResponse()
                    }
                } else {
                    // parsing api's own custom json error
                    // response in ExampleErrorResponse pojo
                    val jsonObject = response.errorBody()?.string()?.let { JSONObject(it) }
//                    Timber.d("api_error_message==${jsonObject?.getString("message")}")
//                    Timber.d("api_error_status_code==${response.code()}")
//                    throw ApiException(msg = jsonObject?.getString("message") ?: "Something went wrong emad")

                    val exception = ApiException(
                        exceptionMsg = jsonObject?.getString("message") ?: "Something went wrong",
                        statusCode = jsonObject?.getInt("status_code")?:0, data = response.errorBody())
                    throw exception
                }

            } catch (e: HttpException) {
                // Returning HttpException's message
                // wrapped in Resource.Error
                throw GeneralException(msg = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                // Returning no internet message
                // wrapped in Resource.Error
                throw NetworkException("Please check your network connection")
                //  Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                // Returning 'Something went wrong' in case
                // of unknown error wrapped in Resource.Error
                throw GeneralException(msg = "Something went wrong")
            }
        }
    }
}

