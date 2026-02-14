package com.kenotracker.di

import android.content.Context
import androidx.room.Room
import com.kenotracker.data.local.dao.DrawDao
import com.kenotracker.data.local.database.KenoDatabase
import com.kenotracker.data.repository.DrawRepositoryImpl
import com.kenotracker.domain.repository.DrawRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideKenoDatabase(@ApplicationContext context: Context): KenoDatabase {
        return Room.databaseBuilder(
            context,
            KenoDatabase::class.java,
            "keno_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDrawDao(database: KenoDatabase): DrawDao {
        return database.drawDao()
    }

    @Provides
    @Singleton
    fun provideDrawRepository(drawDao: DrawDao): DrawRepository {
        return DrawRepositoryImpl(drawDao)
    }
}
