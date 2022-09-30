package com.ashleysutton.lockwoodrecruitmentapp.app.ui.applicant_detail

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import com.ashleysutton.lockwoodrecruitmentapp.app.data.repository.ApplicantRepository
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ApplicantDetailViewModel @Inject constructor(repository: ApplicantRepository): ViewModel() {

    private val _id = MutableLiveData<String>()

    private val _applicant = _id.switchMap { id ->
        repository.getApplicant(id)
    }

    val applicant: LiveData<Resource<Applicant>> = _applicant

    fun start(id: String) {
        _id.value = id
    }
}