package com.tiffles.labrat.data.local.entity

import androidx.room.ColumnInfo

data class BiomarkerEntryWithDate(
    @ColumnInfo(name = "value") val value: Double,
    @ColumnInfo(name = "dateEpochDay") val dateEpochDay: Long,
)