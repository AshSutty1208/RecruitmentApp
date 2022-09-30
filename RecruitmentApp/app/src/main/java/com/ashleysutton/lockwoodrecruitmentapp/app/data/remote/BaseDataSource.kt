package com.ashleysutton.lockwoodrecruitmentapp.app.data.remote

import com.ashleysutton.lockwoodrecruitmentapp.app.utils.Resource
import retrofit2.Response
import timber.log.Timber

/**
 * This is the base class for all data sources in this project
 *
 * Passes a suspend call into the parameters to allow abstraction to what call is being made
 *
 * Returns a resource object based on the call that is passed in (Uses Resource in the Utils)
 *
 * We used Timber to error to console as its just a much easier helper library for logging
 */
abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Timber.e(message)
        return Resource.error("Network call has failed for reason: $message")
    }

}