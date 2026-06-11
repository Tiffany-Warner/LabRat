package com.tiffles.labrat.domain.model

enum class BiomarkerStatus { IN_RANGE, BORDERLINE, OUT_OF_RANGE, NEUTRAL }

fun computeBiomarkerStatus(biomarker: Biomarker, value: Double): BiomarkerStatus {
    val low = biomarker.refRangeLow
    val high = biomarker.refRangeHigh
    if (low == null && high == null) return BiomarkerStatus.NEUTRAL
    val belowLow = low != null && value < low
    val aboveHigh = high != null && value > high
    if (!belowLow && !aboveHigh) return BiomarkerStatus.IN_RANGE
    val borderline = when {
        belowLow -> value >= low!! * 0.90
        aboveHigh -> value <= high!! * 1.10
        else -> false
    }
    return if (borderline) BiomarkerStatus.BORDERLINE else BiomarkerStatus.OUT_OF_RANGE
}