package com.ashleysutton.lockwoodrecruitmentapp.app.utils

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import kotlinx.coroutines.Dispatchers
import org.json.JSONArray
import org.json.JSONObject

fun performGetAllOperation(databaseQuery: () -> LiveData<MutableList<Applicant>>) :
        LiveData<Resource<MutableList<Applicant>>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val source = databaseQuery.invoke().map {
            Resource.success(it)
        }

        emitSource(source)
}

fun performGetOperation(databaseQuery: () -> LiveData<Applicant>):
        LiveData<Resource<Applicant>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        // Get the data from the database before invoking
        val source = databaseQuery.invoke().map {
            Resource.success(it)
        }

        emitSource(source)
    }