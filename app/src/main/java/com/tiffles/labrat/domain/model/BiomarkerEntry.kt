package com.tiffles.labrat.domain.model

data class BiomarkerEntry(
    val id: Long,
    val labResultId: Long,
    val biomarkerId: Long,
    val value: Double
)