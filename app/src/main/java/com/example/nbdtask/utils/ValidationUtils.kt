package com.example.nbdtask.utils

import java.util.regex.Pattern

object ValidationUtils {

    private const val EMAIL_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+"

    fun isValidPhone(phone: String): Boolean {
        return !Pattern.matches("^\\+[0-9]$", phone) && phone.length > 6
    }

    fun String.isInvalidEmail() = !Pattern
        .compile(EMAIL_REGEX)
        .matcher(this)
        .matches()

    fun String.isNumeric(): Boolean {
        return if (this.isNotEmpty()) {
            val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
            this.matches(regex)
        } else {
            false
        }
    }
}
