package com.ashleysutton.lockwoodrecruitmentapp.app.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import org.jetbrains.annotations.NotNull

@Dao
interface ApplicantDao {
    @Query("SELECT * FROM applicants")
    fun getAllApplicants() : LiveData<MutableList<Applicant>>

    @Query("SELECT * FROM applicants WHERE id = :id")
    fun getApplicant(id: String): LiveData<Applicant>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllApplicants(applicants: @NotNull MutableList<Applicant>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertApplicant(applicant: Applicant)

}