package com.ashleysutton.lockwoodrecruitmentapp.app.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.liveData
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import com.ashleysutton.lockwoodrecruitmentapp.app.data.local.ApplicantDao
import com.ashleysutton.lockwoodrecruitmentapp.app.data.local.LocalDatabase
import com.ashleysutton.lockwoodrecruitmentapp.app.data.remote.ApplicantRemoteDataSource
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

@RunWith(JUnit4::class)
class ApplicantRepositoryTest {
    private lateinit var repository: ApplicantRepository
    private val dao = mock(ApplicantDao::class.java)
    private val remoteDataSource = mock(ApplicantRemoteDataSource::class.java)

    private val testApplicant1 = Applicant("1429359126",
        "Barry Scott",
        35,
        "male",
        "Cillit Bang",
        "barry.scott@cillitbang.com",
        "1234123123",
        "123131123",
        "Bang and the dirt is gone",
        "...",
        "Unread Cillit Bang notification")

    private val testApplicant2 = Applicant("12341231",
        "Jar Jar Binks",
        4572636,
        "male",
        "Intergalactic Federation",
        "jar_jar@asdadsda.com",
        "12312351",
        "25123251",
        "No experience. He fat.",
        "...",
        "Unread Jar Jar notification")

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        repository = ApplicantRepository(remoteDataSource,
            dao)
    }

    @Test
    fun verifyNull() {
        repository.getApplicants().observeForever {
            assertThat(it, null)
        }

        repository.getApplicant("").observeForever {
            assertThat(it, null)
        }
    }

    @Test
    fun testCorrectValueReturnedFromDaoGetApplicants() {
        `when`(dao.getAllApplicants()).thenReturn(liveData {
            listOf(testApplicant1, testApplicant2).toMutableList()
        })

        repository.getApplicants().observeForever {
            assertThat(it.data!![0].age, equalTo(35))
        }
    }

    @Test
    fun testCorrectValueReturnedFromDaoGetApplicant() {
        `when`(dao.getApplicant("1234")).thenReturn(liveData {
            testApplicant1
        })

        repository.getApplicant("1234").observeForever {
            assertThat(it.data!!.age, equalTo(35))
        }
    }
}