package com.ashleysutton.lockwoodrecruitmentapp.app.ui.applicants

import androidx.lifecycle.ViewModel
import com.ashleysutton.lockwoodrecruitmentapp.app.data.repository.ApplicantRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ApplicantsViewModel @Inject constructor(private val repository: ApplicantRepository): ViewModel() {
    val applicants by lazy {
        repository.getApplicants()
    }
}