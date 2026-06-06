package com.tiffles.labrat.data.repository

import com.tiffles.labrat.data.local.dao.BiomarkerDao
import com.tiffles.labrat.data.local.mapper.toDomain
import com.tiffles.labrat.data.local.mapper.toEntity
import com.tiffles.labrat.domain.model.Biomarker
import com.tiffles.labrat.domain.repository.BiomarkerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BiomarkerRepositoryImpl @Inject constructor(
    private val dao: BiomarkerDao
) : BiomarkerRepository {

    override fun getAll(): Flow<List<Biomarker>> =
        dao.getAll().map { entities -> entities.map { it.toDomain() } }

    override suspend fun getById(id: Long): Biomarker? =
        dao.getById(id)?.toDomain()

    override suspend fun getPinned(): List<Biomarker> =
        dao.getPinned().map { it.toDomain() }

    override suspend fun save(biomarker: Biomarker) =
        dao.insert(biomarker.toEntity())

    override suspend fun update(biomarker: Biomarker) =
        dao.update(biomarker.toEntity())
}