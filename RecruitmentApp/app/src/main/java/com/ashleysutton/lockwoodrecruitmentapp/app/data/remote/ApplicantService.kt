package com.ashleysutton.lockwoodrecruitmentapp.app.data.remote

import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * This is obviously just a fake remote all to an api, but this demonstrates how to do it
 *
 * This is the service interface for retrofit.
 */
interface ApplicantService {
    @GET("applicant")
    suspend fun getAllApplicants() : Response<MutableList<Applicant>>

    @GET("applicant/{id}")
    suspend fun getApplicant(@Path("id") id: Int): Response<Applicant>
}