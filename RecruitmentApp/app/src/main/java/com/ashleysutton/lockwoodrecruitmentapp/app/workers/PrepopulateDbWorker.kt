package com.ashleysutton.lockwoodrecruitmentapp.app.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.ashleysutton.lockwoodrecruitmentapp.app.data.entities.Applicant
import com.ashleysutton.lockwoodrecruitmentapp.app.data.local.ApplicantDao
import com.ashleysutton.lockwoodrecruitmentapp.app.data.local.LocalDatabase
import com.ashleysutton.lockwoodrecruitmentapp.app.utils.IOUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

class PrepopulateDbWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result = coroutineScope {
        withContext(Dispatchers.IO) {

            try {
                val fileContents = IOUtils.getFileContents(applicationContext, "candidate_import.json")

                val jsonContents = JSONArray(fileContents)

                val listApplicants: MutableList<Applicant> = ArrayList()
                for (i in 0 until jsonContents.length()) {
                    val jsonObject = JSONObject(jsonContents[i].toString())

                    listApplicants.add(Applicant(
                        jsonObject.optString("id", ""),
                        jsonObject.optString("name", ""),
                        jsonObject.optInt("age", 0),
                        jsonObject.optString("gender", ""),
                        jsonObject.optString("company", ""),
                        jsonObject.optString("email", ""),
                        jsonObject.optString("mobile", ""),
                        jsonObject.optString("landline", ""),
                        jsonObject.optString("experience", ""),
                        jsonObject.optString("picture", ""),
                        jsonObject.optString("notifications", "")
                    ))
                }

                LocalDatabase.getInstance(applicationContext).applicantDao().insertAllApplicants(listApplicants)

                Result.success()
            } catch (e: Exception) {
                Timber.e(e, "Error seeding database")
                Result.failure()
            }
        }
    }
}