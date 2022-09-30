package com.ashleysutton.lockwoodrecruitmentapp.app.data.repository

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.ashleysutton.lockwoodrecruitmentapp.app.data.local.ApplicantDao
import com.ashleysutton.lockwoodrecruitmentapp.app.data.remote.ApplicantRemoteDataSource
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.Resource
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.performGetAllOperation
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.performGetOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class is needed to seperate data source calls for the applicant call
 *
 * This is where we would make the call to the API to get the value and input them into the database
 * */
@Singleton
class ApplicantRepository @Inject constructor(
    private val remoteDataSource: ApplicantRemoteDataSource,
    private val localDataSource: ApplicantDao
) {

    /**
     * Gets the given applicant from id
     */
    fun getApplicant(id: String) = performGetOperation(
        databaseQuery = { localDataSource.getApplicant(id) }
    )

    fun getApplicants() = performGetAllOperation(
        databaseQuery = { localDataSource.getAllApplicants() }
    )
}