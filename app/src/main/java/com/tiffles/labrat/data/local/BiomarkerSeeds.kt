package com.tiffles.labrat.data.local

import com.tiffles.labrat.data.local.entity.BiomarkerEntity
import com.tiffles.labrat.domain.model.BiomarkerCategory

object BiomarkerSeeds {

    val all: List<BiomarkerEntity> = listOf(

        // METABOLIC
        BiomarkerEntity(name = "Glucose (Fasting)", unit = "mg/dL", category = BiomarkerCategory.METABOLIC, refRangeLow = 70.0, refRangeHigh = 99.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "HbA1c", unit = "%", category = BiomarkerCategory.METABOLIC, refRangeLow = 4.0, refRangeHigh = 5.6, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Insulin (Fasting)", unit = "μIU/mL", category = BiomarkerCategory.METABOLIC, refRangeLow = 2.6, refRangeHigh = 24.9, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Total Cholesterol", unit = "mg/dL", category = BiomarkerCategory.METABOLIC, refRangeLow = null, refRangeHigh = 200.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "LDL Cholesterol", unit = "mg/dL", category = BiomarkerCategory.METABOLIC, refRangeLow = null, refRangeHigh = 100.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "HDL Cholesterol", unit = "mg/dL", category = BiomarkerCategory.METABOLIC, refRangeLow = 40.0, refRangeHigh = null, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Triglycerides", unit = "mg/dL", category = BiomarkerCategory.METABOLIC, refRangeLow = null, refRangeHigh = 150.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "VLDL Cholesterol", unit = "mg/dL", category = BiomarkerCategory.METABOLIC, refRangeLow = 2.0, refRangeHigh = 30.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Non-HDL Cholesterol", unit = "mg/dL", category = BiomarkerCategory.METABOLIC, refRangeLow = null, refRangeHigh = 130.0, isPinned = false, isArchived = false),

        // CARDIAC
        BiomarkerEntity(name = "Troponin I", unit = "ng/mL", category = BiomarkerCategory.CARDIAC, refRangeLow = null, refRangeHigh = 0.04, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "BNP", unit = "pg/mL", category = BiomarkerCategory.CARDIAC, refRangeLow = null, refRangeHigh = 100.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "NT-proBNP", unit = "pg/mL", category = BiomarkerCategory.CARDIAC, refRangeLow = null, refRangeHigh = 125.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Homocysteine", unit = "μmol/L", category = BiomarkerCategory.CARDIAC, refRangeLow = 5.0, refRangeHigh = 15.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Lipoprotein(a)", unit = "mg/dL", category = BiomarkerCategory.CARDIAC, refRangeLow = null, refRangeHigh = 30.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "ApoB", unit = "mg/dL", category = BiomarkerCategory.CARDIAC, refRangeLow = null, refRangeHigh = 90.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "ApoA1", unit = "mg/dL", category = BiomarkerCategory.CARDIAC, refRangeLow = 120.0, refRangeHigh = 160.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Fibrinogen", unit = "mg/dL", category = BiomarkerCategory.CARDIAC, refRangeLow = 200.0, refRangeHigh = 400.0, isPinned = false, isArchived = false),

        // CBC
        BiomarkerEntity(name = "WBC", unit = "K/μL", category = BiomarkerCategory.CBC, refRangeLow = 4.5, refRangeHigh = 11.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "RBC", unit = "M/μL", category = BiomarkerCategory.CBC, refRangeLow = 4.2, refRangeHigh = 5.4, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Hemoglobin", unit = "g/dL", category = BiomarkerCategory.CBC, refRangeLow = 12.0, refRangeHigh = 17.5, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Hematocrit", unit = "%", category = BiomarkerCategory.CBC, refRangeLow = 36.0, refRangeHigh = 50.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "MCV", unit = "fL", category = BiomarkerCategory.CBC, refRangeLow = 80.0, refRangeHigh = 100.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "MCH", unit = "pg", category = BiomarkerCategory.CBC, refRangeLow = 27.0, refRangeHigh = 33.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "MCHC", unit = "g/dL", category = BiomarkerCategory.CBC, refRangeLow = 32.0, refRangeHigh = 36.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Platelets", unit = "K/μL", category = BiomarkerCategory.CBC, refRangeLow = 150.0, refRangeHigh = 400.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Neutrophils", unit = "%", category = BiomarkerCategory.CBC, refRangeLow = 40.0, refRangeHigh = 70.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Lymphocytes", unit = "%", category = BiomarkerCategory.CBC, refRangeLow = 20.0, refRangeHigh = 40.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Monocytes", unit = "%", category = BiomarkerCategory.CBC, refRangeLow = 2.0, refRangeHigh = 8.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Eosinophils", unit = "%", category = BiomarkerCategory.CBC, refRangeLow = 1.0, refRangeHigh = 4.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Basophils", unit = "%", category = BiomarkerCategory.CBC, refRangeLow = 0.0, refRangeHigh = 1.0, isPinned = false, isArchived = false),

        // HORMONES
        BiomarkerEntity(name = "Testosterone (Total)", unit = "ng/dL", category = BiomarkerCategory.HORMONES, refRangeLow = 300.0, refRangeHigh = 1000.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Testosterone (Free)", unit = "pg/mL", category = BiomarkerCategory.HORMONES, refRangeLow = 9.0, refRangeHigh = 30.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Estradiol (E2)", unit = "pg/mL", category = BiomarkerCategory.HORMONES, refRangeLow = 15.0, refRangeHigh = 350.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Progesterone", unit = "ng/mL", category = BiomarkerCategory.HORMONES, refRangeLow = 0.1, refRangeHigh = 25.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "DHEA-S", unit = "μg/dL", category = BiomarkerCategory.HORMONES, refRangeLow = 65.0, refRangeHigh = 380.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Cortisol (AM)", unit = "μg/dL", category = BiomarkerCategory.HORMONES, refRangeLow = 6.0, refRangeHigh = 23.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "LH", unit = "mIU/mL", category = BiomarkerCategory.HORMONES, refRangeLow = 1.5, refRangeHigh = 9.3, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "FSH", unit = "mIU/mL", category = BiomarkerCategory.HORMONES, refRangeLow = 1.5, refRangeHigh = 12.4, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "SHBG", unit = "nmol/L", category = BiomarkerCategory.HORMONES, refRangeLow = 20.0, refRangeHigh = 70.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "IGF-1", unit = "ng/mL", category = BiomarkerCategory.HORMONES, refRangeLow = 100.0, refRangeHigh = 300.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Prolactin", unit = "ng/mL", category = BiomarkerCategory.HORMONES, refRangeLow = 2.0, refRangeHigh = 29.0, isPinned = false, isArchived = false),

        // THYROID
        BiomarkerEntity(name = "TSH", unit = "μIU/mL", category = BiomarkerCategory.THYROID, refRangeLow = 0.4, refRangeHigh = 4.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Free T4", unit = "ng/dL", category = BiomarkerCategory.THYROID, refRangeLow = 0.8, refRangeHigh = 1.8, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Free T3", unit = "pg/mL", category = BiomarkerCategory.THYROID, refRangeLow = 2.3, refRangeHigh = 4.2, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Total T4", unit = "μg/dL", category = BiomarkerCategory.THYROID, refRangeLow = 5.0, refRangeHigh = 12.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Total T3", unit = "ng/dL", category = BiomarkerCategory.THYROID, refRangeLow = 80.0, refRangeHigh = 200.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Reverse T3", unit = "ng/dL", category = BiomarkerCategory.THYROID, refRangeLow = 10.0, refRangeHigh = 24.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "TPO Antibodies", unit = "IU/mL", category = BiomarkerCategory.THYROID, refRangeLow = null, refRangeHigh = 35.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Thyroglobulin Antibodies", unit = "IU/mL", category = BiomarkerCategory.THYROID, refRangeLow = null, refRangeHigh = 1.0, isPinned = false, isArchived = false),

        // LIVER
        BiomarkerEntity(name = "ALT", unit = "U/L", category = BiomarkerCategory.LIVER, refRangeLow = 7.0, refRangeHigh = 56.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "AST", unit = "U/L", category = BiomarkerCategory.LIVER, refRangeLow = 10.0, refRangeHigh = 40.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "ALP", unit = "U/L", category = BiomarkerCategory.LIVER, refRangeLow = 44.0, refRangeHigh = 147.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "GGT", unit = "U/L", category = BiomarkerCategory.LIVER, refRangeLow = 8.0, refRangeHigh = 61.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Total Bilirubin", unit = "mg/dL", category = BiomarkerCategory.LIVER, refRangeLow = 0.1, refRangeHigh = 1.2, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Direct Bilirubin", unit = "mg/dL", category = BiomarkerCategory.LIVER, refRangeLow = 0.0, refRangeHigh = 0.3, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Albumin", unit = "g/dL", category = BiomarkerCategory.LIVER, refRangeLow = 3.4, refRangeHigh = 5.4, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Total Protein", unit = "g/dL", category = BiomarkerCategory.LIVER, refRangeLow = 6.3, refRangeHigh = 8.2, isPinned = false, isArchived = false),

        // KIDNEY
        BiomarkerEntity(name = "Creatinine", unit = "mg/dL", category = BiomarkerCategory.KIDNEY, refRangeLow = 0.6, refRangeHigh = 1.2, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "BUN", unit = "mg/dL", category = BiomarkerCategory.KIDNEY, refRangeLow = 7.0, refRangeHigh = 20.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "eGFR", unit = "mL/min/1.73m²", category = BiomarkerCategory.KIDNEY, refRangeLow = 60.0, refRangeHigh = null, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Uric Acid", unit = "mg/dL", category = BiomarkerCategory.KIDNEY, refRangeLow = 3.5, refRangeHigh = 7.2, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Cystatin C", unit = "mg/L", category = BiomarkerCategory.KIDNEY, refRangeLow = 0.52, refRangeHigh = 0.98, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Sodium", unit = "mEq/L", category = BiomarkerCategory.KIDNEY, refRangeLow = 136.0, refRangeHigh = 145.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Potassium", unit = "mEq/L", category = BiomarkerCategory.KIDNEY, refRangeLow = 3.5, refRangeHigh = 5.1, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Chloride", unit = "mEq/L", category = BiomarkerCategory.KIDNEY, refRangeLow = 98.0, refRangeHigh = 107.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "CO2 (Bicarbonate)", unit = "mEq/L", category = BiomarkerCategory.KIDNEY, refRangeLow = 22.0, refRangeHigh = 29.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Calcium", unit = "mg/dL", category = BiomarkerCategory.KIDNEY, refRangeLow = 8.5, refRangeHigh = 10.5, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Phosphorus", unit = "mg/dL", category = BiomarkerCategory.KIDNEY, refRangeLow = 2.5, refRangeHigh = 4.5, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Magnesium", unit = "mg/dL", category = BiomarkerCategory.KIDNEY, refRangeLow = 1.7, refRangeHigh = 2.2, isPinned = false, isArchived = false),

        // PANCREAS
        BiomarkerEntity(name = "Amylase", unit = "U/L", category = BiomarkerCategory.PANCREAS, refRangeLow = 40.0, refRangeHigh = 140.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Lipase", unit = "U/L", category = BiomarkerCategory.PANCREAS, refRangeLow = 10.0, refRangeHigh = 140.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "C-Peptide", unit = "ng/mL", category = BiomarkerCategory.PANCREAS, refRangeLow = 0.8, refRangeHigh = 3.9, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Glucagon", unit = "pg/mL", category = BiomarkerCategory.PANCREAS, refRangeLow = 70.0, refRangeHigh = 180.0, isPinned = false, isArchived = false),

        // VITAMINS
        BiomarkerEntity(name = "Vitamin D (25-OH)", unit = "ng/mL", category = BiomarkerCategory.VITAMINS, refRangeLow = 30.0, refRangeHigh = 100.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Vitamin B12", unit = "pg/mL", category = BiomarkerCategory.VITAMINS, refRangeLow = 200.0, refRangeHigh = 900.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Folate", unit = "ng/mL", category = BiomarkerCategory.VITAMINS, refRangeLow = 5.0, refRangeHigh = 20.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Vitamin A (Retinol)", unit = "μg/dL", category = BiomarkerCategory.VITAMINS, refRangeLow = 30.0, refRangeHigh = 65.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Vitamin E (Alpha-Tocopherol)", unit = "mg/L", category = BiomarkerCategory.VITAMINS, refRangeLow = 5.5, refRangeHigh = 17.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Vitamin K (Phylloquinone)", unit = "ng/mL", category = BiomarkerCategory.VITAMINS, refRangeLow = 0.1, refRangeHigh = 2.2, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Zinc", unit = "μg/dL", category = BiomarkerCategory.VITAMINS, refRangeLow = 60.0, refRangeHigh = 130.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Selenium", unit = "μg/L", category = BiomarkerCategory.VITAMINS, refRangeLow = 70.0, refRangeHigh = 150.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Iron", unit = "μg/dL", category = BiomarkerCategory.VITAMINS, refRangeLow = 60.0, refRangeHigh = 170.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Ferritin", unit = "ng/mL", category = BiomarkerCategory.VITAMINS, refRangeLow = 12.0, refRangeHigh = 300.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "TIBC", unit = "μg/dL", category = BiomarkerCategory.VITAMINS, refRangeLow = 250.0, refRangeHigh = 370.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Transferrin Saturation", unit = "%", category = BiomarkerCategory.VITAMINS, refRangeLow = 20.0, refRangeHigh = 50.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Copper", unit = "μg/dL", category = BiomarkerCategory.VITAMINS, refRangeLow = 70.0, refRangeHigh = 140.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Omega-3 Index", unit = "%", category = BiomarkerCategory.VITAMINS, refRangeLow = 8.0, refRangeHigh = 12.0, isPinned = false, isArchived = false),

        // INFLAMMATION
        BiomarkerEntity(name = "hsCRP", unit = "mg/L", category = BiomarkerCategory.INFLAMMATION, refRangeLow = 0.0, refRangeHigh = 1.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "ESR", unit = "mm/hr", category = BiomarkerCategory.INFLAMMATION, refRangeLow = 0.0, refRangeHigh = 20.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "IL-6", unit = "pg/mL", category = BiomarkerCategory.INFLAMMATION, refRangeLow = 0.0, refRangeHigh = 7.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "TNF-alpha", unit = "pg/mL", category = BiomarkerCategory.INFLAMMATION, refRangeLow = 0.0, refRangeHigh = 8.1, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Lp-PLA2", unit = "ng/mL", category = BiomarkerCategory.INFLAMMATION, refRangeLow = null, refRangeHigh = 200.0, isPinned = false, isArchived = false),

        // AUTOIMMUNITY
        BiomarkerEntity(name = "ANA", unit = "titer", category = BiomarkerCategory.AUTOIMMUNITY, refRangeLow = null, refRangeHigh = null, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Anti-dsDNA", unit = "IU/mL", category = BiomarkerCategory.AUTOIMMUNITY, refRangeLow = null, refRangeHigh = 30.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Rheumatoid Factor", unit = "IU/mL", category = BiomarkerCategory.AUTOIMMUNITY, refRangeLow = null, refRangeHigh = 14.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Anti-CCP", unit = "U/mL", category = BiomarkerCategory.AUTOIMMUNITY, refRangeLow = null, refRangeHigh = 20.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Complement C3", unit = "mg/dL", category = BiomarkerCategory.AUTOIMMUNITY, refRangeLow = 90.0, refRangeHigh = 180.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Complement C4", unit = "mg/dL", category = BiomarkerCategory.AUTOIMMUNITY, refRangeLow = 16.0, refRangeHigh = 47.0, isPinned = false, isArchived = false),

        // ENVIRONMENTAL_TOXINS
        BiomarkerEntity(name = "Lead", unit = "μg/dL", category = BiomarkerCategory.ENVIRONMENTAL_TOXINS, refRangeLow = null, refRangeHigh = 5.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Mercury", unit = "μg/L", category = BiomarkerCategory.ENVIRONMENTAL_TOXINS, refRangeLow = null, refRangeHigh = 5.8, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Arsenic", unit = "μg/L", category = BiomarkerCategory.ENVIRONMENTAL_TOXINS, refRangeLow = null, refRangeHigh = 35.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "Cadmium", unit = "μg/L", category = BiomarkerCategory.ENVIRONMENTAL_TOXINS, refRangeLow = null, refRangeHigh = 1.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "BPA", unit = "ng/mL", category = BiomarkerCategory.ENVIRONMENTAL_TOXINS, refRangeLow = null, refRangeHigh = 1.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "PFAS (Total)", unit = "ng/mL", category = BiomarkerCategory.ENVIRONMENTAL_TOXINS, refRangeLow = null, refRangeHigh = 7.0, isPinned = false, isArchived = false),

        // OTHER
        BiomarkerEntity(name = "PSA", unit = "ng/mL", category = BiomarkerCategory.OTHER, refRangeLow = null, refRangeHigh = 4.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "CEA", unit = "ng/mL", category = BiomarkerCategory.OTHER, refRangeLow = null, refRangeHigh = 2.5, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "CA-125", unit = "U/mL", category = BiomarkerCategory.OTHER, refRangeLow = null, refRangeHigh = 35.0, isPinned = false, isArchived = false),
        BiomarkerEntity(name = "DHEA", unit = "ng/dL", category = BiomarkerCategory.OTHER, refRangeLow = 61.0, refRangeHigh = 1636.0, isPinned = false, isArchived = false),
    )
}