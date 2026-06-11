package com.tiffles.labrat.domain.model

import java.time.LocalDate

data class BiomarkerDetailEntry(
    val value: Double,
    val date: LocalDate,
    val status: BiomarkerStatus,
    val delta: Double?,
)