package com.omarhawari.mrcoffee.di

import android.app.Application
import androidx.room.Room
import com.omarhawari.mrcoffee.core.Constants
import com.omarhawari.mrcoffee.data.data_source.local.CoffeeDatabase
import com.omarhawari.mrcoffee.data.data_source.remote.CoffeeAPI
import com.omarhawari.mrcoffee.data.repository.CoffeeRepositoryImpl
import com.omarhawari.mrcoffee.domain.repository.CoffeeRepository
import com.omarhawari.mrcoffee.domain.use_cases.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCoffeeApi(): CoffeeAPI {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .build()
            .create(CoffeeAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCoffeeRepository(api: CoffeeAPI): CoffeeRepository {
        return CoffeeRepositoryImpl(api)
    }


    @Provides
    @Singleton
    fun provideCoffeeDatabase(app: Application): CoffeeDatabase {
        return Room.databaseBuilder(
            app,
            CoffeeDatabase::class.java,
            CoffeeDatabase.DATABASE_NAME
        ).build()
    }


    @Provides
    @Singleton
    fun provideMasterUseCase(
        repository: CoffeeRepository,
        database: CoffeeDatabase
    ): MasterUseCase {
        return MasterUseCase(
            GetAllUseCase(repository),
            LoadSizesForType(database),
            SaveAllUseCase(database),
            LoadExtrasForType(database),
            LoadSubSelectionsForExtra(database),
            LoadTypes(database),
        )
    }

}