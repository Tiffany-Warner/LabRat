package com.tiffles.labrat.data.repository

import com.tiffles.labrat.data.local.dao.LabResultDao
import com.tiffles.labrat.data.local.mapper.toDomain
import com.tiffles.labrat.data.local.mapper.toEntity
import com.tiffles.labrat.domain.model.LabResult
import com.tiffles.labrat.domain.repository.LabResultRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LabResultRepositoryImpl @Inject constructor(
    private val dao: LabResultDao
) : LabResultRepository {

    override fun getAll(): Flow<List<LabResult>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getById(id: Long): LabResult? =
        dao.getById(id)?.toDomain()

    override suspend fun save(labResult: LabResult) {
        dao.insert(labResult.toEntity())
    }

    override suspend fun delete(labResult: LabResult) =
        dao.delete(labResult.toEntity())
}