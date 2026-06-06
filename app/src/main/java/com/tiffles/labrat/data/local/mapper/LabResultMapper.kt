package com.tiffles.labrat.data.local.mapper

import com.tiffles.labrat.data.local.entity.LabResultEntity
import com.tiffles.labrat.domain.model.LabResult
import java.time.LocalDate

fun LabResultEntity.toDomain(): LabResult = LabResult(
    id = id,
    date = LocalDate.ofEpochDay(dateEpochDay),
    labName = labName,
    notes = notes
)

fun LabResult.toEntity(): LabResultEntity = LabResultEntity(
    id = id,
    dateEpochDay = date.toEpochDay(),
    labName = labName,
    notes = notes
)