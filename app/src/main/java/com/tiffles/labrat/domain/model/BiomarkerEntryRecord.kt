package com.tiffles.labrat.domain.model

import java.time.LocalDate

data class BiomarkerEntryRecord(
    val value: Double,
    val date: LocalDate,
)