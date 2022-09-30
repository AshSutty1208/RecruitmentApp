package com.ashleysutton.lockwoodrecruitmentapp.app.di

import android.content.Context
import com.ashleysutton.lockwoodrecruitmentapp.app.data.local.ApplicantDao
import com.ashleysutton.lockwoodrecruitmentapp.app.data.local.LocalDatabase
import com.ashleysutton.lockwoodrecruitmentapp.app.data.remote.ApplicantRemoteDataSource
import com.ashleysutton.lockwoodrecruitmentapp.app.data.remote.ApplicantService
import com.ashleysutton.lockwoodrecruitmentapp.app.data.repository.ApplicantRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.DefineComponent
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    /**
     * This is just for show, just to show how you would call API by providing Retrofit
     */
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://google.com")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideApplicantService(retrofit: Retrofit): ApplicantService = retrofit.create(ApplicantService::class.java)

    @Singleton
    @Provides
    fun provideApplicantRemoteDataSource(applicantService: ApplicantService) = ApplicantRemoteDataSource(applicantService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = LocalDatabase.getInstance(appContext)

    @Singleton
    @Provides
    fun provideApplicantDao(db: LocalDatabase) = db.applicantDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: ApplicantRemoteDataSource,
                          localDataSource: ApplicantDao) =
        ApplicantRepository(remoteDataSource, localDataSource)
}