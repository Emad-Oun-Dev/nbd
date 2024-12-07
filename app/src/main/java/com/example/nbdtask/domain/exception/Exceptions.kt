package com.example.nbdtask.domain.exception

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

class GeneralException(msg: String = "method not found") : Throwable(msg)
class NetworkException(msg: String = "method not found") : Throwable(msg)
class ApiException(var exceptionMsg: String? = "", var statusCode: Int = 0, var data:Any?=null) :
    Throwable(exceptionMsg)
