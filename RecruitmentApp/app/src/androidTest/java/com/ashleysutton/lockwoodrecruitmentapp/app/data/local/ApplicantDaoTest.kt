package com.ashleysutton.lockwoodrecruitmentapp.app.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.LiveDataTestUtil
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class ApplicantDaoTest {
    private lateinit var applicantDao: ApplicantDao
    private lateinit var db: LocalDatabase
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

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            LocalDatabase::class.java
        ).build()

        applicantDao = db.applicantDao()
    }

    private fun insertOneApplicant() {
        runBlocking {
            applicantDao.insertApplicant(testApplicant1)
        }
    }

    private fun insertMultipleApplicants() {
        runBlocking {
            applicantDao.insertAllApplicants(listOf(testApplicant1, testApplicant2).toMutableList())
        }
    }

    @Test
    fun testGetWithCorrectIdApplicant() {
        insertOneApplicant()

        val applicant = LiveDataTestUtil.getValue(applicantDao.getApplicant("1429359126"))

        assertThat(applicant.age, equalTo(35))
        assertThat(applicant.company, equalTo("Cillit Bang"))
    }

    @Test
    fun testGetWithIncorrectIdApplicant() {
        insertOneApplicant()

        val applicant = LiveDataTestUtil.getValue(applicantDao.getApplicant("1432"))

        assertThat(applicant, equalTo(null))
    }

    @Test
    fun testGetMultipleApplicants() {
        insertMultipleApplicants()

        val applicants = LiveDataTestUtil.getValue(applicantDao.getAllApplicants())

        assertThat(applicants.size, equalTo(2))

        assertThat(applicants[0].age, equalTo(35))

        assertThat(applicants[0].company, equalTo("Cillit Bang"))

        assertThat(applicants[1].age, equalTo(4572636))
    }

    @Test
    fun testGetMultipleWithoutInsertingApplicants() {
//        insertMultipleApplicants()

        val applicants = LiveDataTestUtil.getValue(applicantDao.getAllApplicants())

        assertThat(applicants.size, equalTo(0))
    }

    @After
    fun closeDb() {
        db.close()
    }

}