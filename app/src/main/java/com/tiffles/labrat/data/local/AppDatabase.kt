package com.tiffles.labrat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tiffles.labrat.data.local.dao.BiomarkerDao
import com.tiffles.labrat.data.local.dao.BiomarkerEntryDao
import com.tiffles.labrat.data.local.dao.LabResultDao
import com.tiffles.labrat.data.local.entity.BiomarkerEntity
import com.tiffles.labrat.data.local.entity.BiomarkerEntryEntity
import com.tiffles.labrat.data.local.entity.LabResultEntity

@Database(
    entities = [
        BiomarkerEntity::class,
        LabResultEntity::class,
        BiomarkerEntryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun biomarkerDao(): BiomarkerDao
    abstract fun labResultDao(): LabResultDao
    abstract fun biomarkerEntryDao(): BiomarkerEntryDao
}