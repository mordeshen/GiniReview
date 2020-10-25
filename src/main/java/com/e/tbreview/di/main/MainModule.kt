package com.e.tbreview.di.main

import com.e.tbreview.api.ApiService
import com.e.tbreview.persistence.AppDatabase
import com.e.tbreview.persistence.MainDao
import com.e.tbreview.repo.MainRepository
import com.e.tbreview.session.SessionManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class MainModule {

    @MainScope
    @Provides
    fun provideApiService(retrofitBuilder: Retrofit.Builder): ApiService {
        return retrofitBuilder
            .build()
            .create(ApiService::class.java)
    }


    @MainScope
    @Provides
    fun provideMainRepository(
        apiService: ApiService,
        mainDao: MainDao,
        sessionManager: SessionManager
    ): MainRepository {
        return MainRepository(apiService,mainDao ,sessionManager)
    }

    @MainScope
    @Provides
    fun provideMainDao(db: AppDatabase): MainDao {
        return db.getMainDao()
    }

}