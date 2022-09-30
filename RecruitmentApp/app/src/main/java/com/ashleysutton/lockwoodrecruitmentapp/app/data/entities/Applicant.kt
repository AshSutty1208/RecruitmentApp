package com.ashleysutton.lockwoodrecruitmentapp.app.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Applicant data class
 *
 * This class is used to represent an applicant
 */
@Entity(tableName = "applicants")
data class Applicant(@PrimaryKey val id: String,
                     val name: String,
                     val age: Int,
                     val gender: String,
                     val company: String,
                     val email: String,
                     val mobile: String,
                     val landline: String,
                     val experience: String,
                     val picture: String,
                     val notifications: String)