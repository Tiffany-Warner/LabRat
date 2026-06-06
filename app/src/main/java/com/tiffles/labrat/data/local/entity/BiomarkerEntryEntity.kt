package com.tiffles.labrat.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "biomarker_entries",
    foreignKeys = [
        ForeignKey(
            entity = LabResultEntity::class,
            parentColumns = ["id"],
            childColumns = ["labResultId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = BiomarkerEntity::class,
            parentColumns = ["id"],
            childColumns = ["biomarkerId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("labResultId"), Index("biomarkerId")]
)
data class BiomarkerEntryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val labResultId: Long,
    val biomarkerId: Long,
    val value: Double
)