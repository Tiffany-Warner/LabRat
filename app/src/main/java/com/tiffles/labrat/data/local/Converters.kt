package com.tiffles.labrat.data.local

import androidx.room.TypeConverter
import com.tiffles.labrat.domain.model.BiomarkerCategory
import java.time.LocalDate

class Converters {

    @TypeConverter
    fun fromEpochDay(value: Long): LocalDate = LocalDate.ofEpochDay(value)

    @TypeConverter
    fun toEpochDay(date: LocalDate): Long = date.toEpochDay()

    @TypeConverter
    fun fromCategoryName(value: String): BiomarkerCategory = BiomarkerCategory.valueOf(value)

    @TypeConverter
    fun toCategoryName(category: BiomarkerCategory): String = category.name
}