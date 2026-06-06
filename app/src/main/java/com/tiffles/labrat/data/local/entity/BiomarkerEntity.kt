package com.tiffles.labrat.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tiffles.labrat.domain.model.BiomarkerCategory

@Entity(tableName = "biomarkers")
data class BiomarkerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val unit: String,
    val category: BiomarkerCategory,
    val refRangeLow: Double?,
    val refRangeHigh: Double?,
    val isPinned: Boolean,
    val isArchived: Boolean
)