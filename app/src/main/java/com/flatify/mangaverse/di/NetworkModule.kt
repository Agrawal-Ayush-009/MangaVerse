package com.flatify.mangaverse.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.flatify.mangaverse.data.local.dao.MangaDao
import com.flatify.mangaverse.data.local.dao.RemoteKeysDao
import com.flatify.mangaverse.data.local.dao.UserDao
import com.flatify.mangaverse.data.local.database.AppDatabase
import com.flatify.mangaverse.data.prefs.UserPreferences
import com.flatify.mangaverse.data.remote.api.MangaAPI
import com.flatify.mangaverse.utils.Constants.MANGA_BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)  // Connection timeout
        .readTimeout(30, TimeUnit.SECONDS)     // Read timeout
        .writeTimeout(30, TimeUnit.SECONDS)    // Write timeout
        .build()

    @Singleton
    @Provides
    fun provideRetrofitForUser(): Builder {
        return Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(MANGA_BASE_URL)

    }

    @Provides
    @Singleton
    fun provideDatabase(application: Application): AppDatabase{
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
       return db.userDao()
    }

    @Provides
    fun providesMangaDao(db: AppDatabase): MangaDao{
        return db.mangaDao()
    }

    @Provides
    fun providesRemoteKeysDao(db: AppDatabase): RemoteKeysDao {
        return db.remoteKeysDao()
    }

    @Provides
    @Singleton
    fun provideUserPreferences(@ApplicationContext context: Context): UserPreferences {
        return UserPreferences(context)
    }

    @Singleton
    @Provides
    fun provideMangaApi(retrofitBuilder: Builder): MangaAPI{
        return retrofitBuilder
            .build()
            .create(MangaAPI::class.java)
    }

}

