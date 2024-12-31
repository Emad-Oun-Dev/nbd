package com.example.nbdtask.data.di

//import androidx.work.WorkManager
import com.example.nbdtask.data.remote.api.LocalAlertsApi
import com.example.nbdtask.data.repository.LocalAlertsRepositoryImpl
import com.example.nbdtask.domain.repository.LocalAlertsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by Emad Mohamed Oun
 * Speedi
 * emad.3oon@gmail.com
 */

@Module(includes = [LocalAlertsDataModule.BindsModule::class])
@InstallIn(SingletonComponent::class)
object LocalAlertsDataModule {

    @Provides
    @Singleton
    fun provideLocalAlertsApi(
        retrofit: Retrofit
    ): LocalAlertsApi {
        return retrofit.create(LocalAlertsApi::class.java)
    }

//    @Provides
//    @Singleton
//    fun provideWorkManager(@ApplicationContext appContext: Context, params: WorkerParameters): ReminderWorker =
//        ReminderWorker(appContext, params = params)

    @Module
    @InstallIn(SingletonComponent::class)
    interface BindsModule {

        @Binds
        @Singleton
        fun bindLocalAlertsRepository(impl: LocalAlertsRepositoryImpl): LocalAlertsRepository
    }

}
