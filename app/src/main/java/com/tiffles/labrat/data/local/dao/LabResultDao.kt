package com.tiffles.labrat.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.tiffles.labrat.data.local.entity.BiomarkerEntryEntity
import com.tiffles.labrat.data.local.entity.LabResultEntity
import kotlinx.coroutines.flow.Flow

// abstract class required for @Transaction function with open modifier
@Dao
abstract class LabResultDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(labResult: LabResultEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract suspend fun insertEntry(entry: BiomarkerEntryEntity): Long

    @Update
    abstract suspend fun update(labResult: LabResultEntity)

    @Delete
    abstract suspend fun delete(labResult: LabResultEntity)

    @Query("SELECT * FROM lab_results ORDER BY dateEpochDay DESC")
    abstract fun getAll(): Flow<List<LabResultEntity>>

    @Query("SELECT * FROM lab_results WHERE id = :id")
    abstract suspend fun getById(id: Long): LabResultEntity?

    @Transaction
    open suspend fun insertWithEntries(result: LabResultEntity, entries: List<BiomarkerEntryEntity>) {
        val newId = insert(result)
        entries.forEach { insertEntry(it.copy(labResultId = newId)) }
    }
}