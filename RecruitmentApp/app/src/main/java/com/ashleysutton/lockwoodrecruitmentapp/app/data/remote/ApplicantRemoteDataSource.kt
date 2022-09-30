package com.ashleysutton.lockwoodrecruitmentapp.app.data.remote

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Fake Applicant remote data source
 *
 * Just this is what would be used to call an api to get applicants/applicant
 */
@Singleton
class ApplicantRemoteDataSource @Inject constructor(
    private val characterService: ApplicantService
): BaseDataSource() {

    suspend fun getApplicants() = getResult {
        characterService.getAllApplicants()
    }

    suspend fun getApplicant(id: Int) = getResult {
        characterService.getApplicant(id)
    }
}