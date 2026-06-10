package com.tiffles.labrat.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tiffles.labrat.data.local.AppDatabase
import com.tiffles.labrat.data.local.BiomarkerSeeds
import com.tiffles.labrat.data.local.dao.BiomarkerDao
import com.tiffles.labrat.data.local.dao.BiomarkerEntryDao
import com.tiffles.labrat.data.local.dao.LabResultDao
import com.tiffles.labrat.data.repository.BiomarkerEntryRepositoryImpl
import com.tiffles.labrat.data.repository.BiomarkerRepositoryImpl
import com.tiffles.labrat.data.repository.LabResultRepositoryImpl
import com.tiffles.labrat.domain.repository.BiomarkerEntryRepository
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import com.tiffles.labrat.domain.repository.LabResultRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DatabaseModule {

    @Binds @Singleton
    abstract fun bindBiomarkerRepository(impl: BiomarkerRepositoryImpl): BiomarkerRepository

    @Binds @Singleton
    abstract fun bindLabResultRepository(impl: LabResultRepositoryImpl): LabResultRepository

    @Binds @Singleton
    abstract fun bindBiomarkerEntryRepository(impl: BiomarkerEntryRepositoryImpl): BiomarkerEntryRepository

    companion object {

        @Provides @Singleton
        fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
            var db: AppDatabase? = null
            db = Room.databaseBuilder(context, AppDatabase::class.java, "labrat.db")
                .addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(sqLiteDatabase: SupportSQLiteDatabase) {
                        super.onCreate(sqLiteDatabase)
                        // Unmanaged scope intentional — seed runs once on DB creation only
                        CoroutineScope(Dispatchers.IO).launch {
                            db?.biomarkerDao()?.insertAll(BiomarkerSeeds.all)
                        }
                    }
                })
                .build()
            return db
        }

        @Provides
        fun provideBiomarkerDao(db: AppDatabase): BiomarkerDao = db.biomarkerDao()

        @Provides
        fun provideLabResultDao(db: AppDatabase): LabResultDao = db.labResultDao()

        @Provides
        fun provideBiomarkerEntryDao(db: AppDatabase): BiomarkerEntryDao = db.biomarkerEntryDao()

    }
}