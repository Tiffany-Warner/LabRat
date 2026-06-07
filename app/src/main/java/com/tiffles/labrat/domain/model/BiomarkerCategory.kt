package com.tiffles.labrat.domain.model

enum class BiomarkerCategory(val displayName: String) {
    METABOLIC("Metabolic"),
    CARDIAC("Cardiac"),
    CBC("CBC"),
    HORMONES("Hormones"),
    THYROID("Thyroid"),
    LIVER("Liver"),
    KIDNEY("Kidney"),
    PANCREAS("Pancreas"),
    VITAMINS("Vitamins & Minerals"),
    INFLAMMATION("Inflammation"),
    AUTOIMMUNITY("Autoimmunity"),
    ENVIRONMENTAL_TOXINS("Environmental Toxins"),
    OTHER("Other"),
}