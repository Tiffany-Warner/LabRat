package com.tiffles.labrat.domain.model

import java.time.LocalDate

data class LabResult(
    val id: Long,
    val date: LocalDate,
    val labName: String?,
    val notes: String?
)