package com.tiffles.labrat.domain.repository

import com.tiffles.labrat.domain.model.Biomarker

interface BiomarkerRepository {
    suspend fun getAll(): List<Biomarker>
    suspend fun getById(id: Long): Biomarker?
    suspend fun getPinned(): List<Biomarker>
    suspend fun save(biomarker: Biomarker)
    suspend fun update(biomarker: Biomarker)
}