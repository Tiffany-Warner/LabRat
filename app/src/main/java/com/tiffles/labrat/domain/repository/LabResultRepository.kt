package com.tiffles.labrat.domain.repository

import com.tiffles.labrat.domain.model.BiomarkerEntry
import com.tiffles.labrat.domain.model.LabResult
import kotlinx.coroutines.flow.Flow

interface LabResultRepository {
    fun getAll(): Flow<List<LabResult>>
    suspend fun getById(id: Long): LabResult?
    suspend fun saveWithEntries(labResult: LabResult, entries: List<BiomarkerEntry>)
    suspend fun delete(labResult: LabResult)
}