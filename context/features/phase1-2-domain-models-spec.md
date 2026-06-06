# Phase 1.2 — Domain Models

## Overview

Define the core domain models that everything else in the app depends on. Pure Kotlin — no Room, no
Android dependencies.

## Requirements

### Models (`domain/model/`)

- `Biomarker`
    - `id: Long`
    - `name: String`
    - `unit: String` (e.g. "mg/dL", "ng/mL")
    - `category: BiomarkerCategory`
    - `refRangeLow: Double?`
    - `refRangeHigh: Double?`
    - `isPinned: Boolean`
    - `isArchived: Boolean`

- `BiomarkerCategory` — enum class
    - Values: `METABOLIC`, `CARDIAC`, `CBC`, `HORMONES`, `THYROID`, `LIVER`, `KIDNEY`, `PANCREAS`,
      `VITAMINS`, `INFLAMMATION`, `AUTOIMMUNITY`, `ENVIRONMENTAL TOXINS`, `OTHER`

- `LabResult`
    - `id: Long`
    - `date: LocalDate`
    - `labName: String?`
    - `notes: String?`

- `BiomarkerEntry` — one biomarker value within a lab result
    - `id: Long`
    - `labResultId: Long`
    - `biomarkerId: Long`
    - `value: Double`

### Repository Interfaces (`domain/repository/`)

- `BiomarkerRepository` — `getAll()`, `getById()`, `getPinned()`, `save()`, `update()`
- `LabResultRepository` — `getAll(): Flow<List<LabResult>>`, `getById()`, `save()`, `delete()`
- `BiomarkerEntryRepository` — `getEntriesForBiomarker(biomarkerId): Flow<List<BiomarkerEntry>>`,
  `getEntriesForLabResult(labResultId)`, `save()`, `delete()`

## Notes

- No Room annotations here — domain models are plain Kotlin data classes
- Repository interfaces define the contract; implementations come in Phase 1.3

## References

- @context/coding-standards.md
- @context/features/phase1-1-project-setup-spec.md
