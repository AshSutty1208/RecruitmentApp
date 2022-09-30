package com.ashleysutton.lockwoodrecruitmentapp.app.ui.applicants

import androidx.lifecycle.liveData
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import com.ashleysutton.lockwoodrecruitmentapp.app.data.repository.ApplicantRepository
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.getValue
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class ApplicantsViewModelTest {
    private val repository = mock(ApplicantRepository::class.java)
    private lateinit var viewModel: ApplicantsViewModel

    @Before
    fun setup() {
        viewModel = ApplicantsViewModel(repository)
    }

    @Test
    fun testNull() {
        assertThat(viewModel.applicants, nullValue())
    }
}