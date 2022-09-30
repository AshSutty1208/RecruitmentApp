package com.ashleysutton.lockwoodrecruitmentapp.app.ui.applicant_detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ashleysutton.lockwoodrecruitmentapp.app.data.repository.ApplicantRepository
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.getValue
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock


@RunWith(JUnit4::class)
class ApplicantDetailViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val repository = mock(ApplicantRepository::class.java)
    private var viewModel = ApplicantDetailViewModel(repository)

    @Test
    fun testNull() {
        viewModel.start("123456")

        assertEquals(getValue(viewModel.applicant), null)
    }
}