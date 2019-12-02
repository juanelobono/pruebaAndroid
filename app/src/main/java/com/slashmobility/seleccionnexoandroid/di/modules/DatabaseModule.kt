package com.slashmobility.seleccionnexoandroid.di.modules

import android.app.Application
import androidx.room.Room
import com.slashmobility.seleccionnexoandroid.database.AppDatabase
import com.slashmobility.seleccionnexoandroid.database.daos.GroupDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Leo ¯\_(ツ)_/¯ on 2019-12-02
 */

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).fallbackToDestructiveMigration()
        .allowMainThreadQueries().build()
    }

    @Provides
    @Singleton
    internal fun provideMovieDao(appDatabase: AppDatabase): GroupDao {
        return appDatabase.groupDao()
    }
}