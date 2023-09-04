package com.example.network.data.model

const val CHECK_INTERNET_MSG = "Check your internet connection"

open class NetworkException(override val message: String = "") : Exception(message)

open class ConnectionException(override val message: String = CHECK_INTERNET_MSG) : Exception(message)

class EmptyResponseBodyException(override val message: String = "Response body can not be empty") : Exception(message)

class RequestException(var httpCode: Int = 500, override var message: String = "") : NetworkException(message)

class GenericException() : NetworkException("")
