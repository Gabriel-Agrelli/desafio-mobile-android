package com.example.marveluniverse.data.repository

data class NetworkResult<out T>(val status: Status, val data: T?, val error: Error?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T?): NetworkResult<T> {
            return NetworkResult(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String, error: Error?): NetworkResult<T> {
            return NetworkResult(Status.ERROR, null, error, message)
        }

        fun <T> loading(data: T? = null): NetworkResult<T> {
            return NetworkResult(Status.LOADING, data, null, null)
        }
    }
}

//data class NetworkResult<out T>(val status: Status, val data: T?, val message: String?) {
//    companion object {
//        fun <T> success(data: T?): NetworkResult<T> {
//            return NetworkResult(Status.SUCCESS, data, null)
//        }
//
//        fun <T> error(message: String, data: T?): NetworkResult<T> {
//            return NetworkResult(Status.ERROR, data, message)
//        }
//
//        fun <T> loading(data: T?): NetworkResult<T> {
//            return NetworkResult(Status.LOADING, data, null)
//        }
//    }
//}
//
//enum class Status {
//    SUCCESS,
//    ERROR,
//    LOADING
//}
