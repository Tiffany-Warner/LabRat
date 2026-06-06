package com.tiffles.labrat.domain.repository

import com.tiffles.labrat.domain.model.LabResult
import kotlinx.coroutines.flow.Flow

interface LabResultRepository {
    fun getAll(): Flow<List<LabResult>>
    suspend fun getById(id: Long): LabResult?
    suspend fun save(labResult: LabResult)
    suspend fun delete(labResult: LabResult)
}