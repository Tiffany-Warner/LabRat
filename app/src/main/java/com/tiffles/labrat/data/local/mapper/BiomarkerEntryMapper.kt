package com.tiffles.labrat.data.local.mapper

import com.tiffles.labrat.data.local.entity.BiomarkerEntryEntity
import com.tiffles.labrat.domain.model.BiomarkerEntry

fun BiomarkerEntryEntity.toDomain(): BiomarkerEntry = BiomarkerEntry(
    id = id,
    labResultId = labResultId,
    biomarkerId = biomarkerId,
    value = value
)

fun BiomarkerEntry.toEntity(): BiomarkerEntryEntity = BiomarkerEntryEntity(
    id = id,
    labResultId = labResultId,
    biomarkerId = biomarkerId,
    value = value
)