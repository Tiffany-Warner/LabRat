package com.tiffles.labrat.data.local.mapper

import com.tiffles.labrat.data.local.entity.BiomarkerEntity
import com.tiffles.labrat.domain.model.Biomarker

fun BiomarkerEntity.toDomain(): Biomarker = Biomarker(
    id = id,
    name = name,
    unit = unit,
    category = category,
    refRangeLow = refRangeLow,
    refRangeHigh = refRangeHigh,
    isPinned = isPinned,
    isArchived = isArchived
)

fun Biomarker.toEntity(): BiomarkerEntity = BiomarkerEntity(
    id = id,
    name = name,
    unit = unit,
    category = category,
    refRangeLow = refRangeLow,
    refRangeHigh = refRangeHigh,
    isPinned = isPinned,
    isArchived = isArchived
)