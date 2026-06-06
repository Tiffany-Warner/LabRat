# Current Feature

Phase 1.2 — Domain Models

## Status

Not Started

## Goals

Define the core domain models and repository interfaces. Pure Kotlin — no Room, no Android dependencies.

### Models (`domain/model/`)

- `Biomarker` — id, name, unit, category, refRangeLow, refRangeHigh, isPinned, isArchived
- `BiomarkerCategory` — enum: METABOLIC, CARDIAC, CBC, HORMONES, THYROID, LIVER, KIDNEY, PANCREAS, VITAMINS, INFLAMMATION, AUTOIMMUNITY, ENVIRONMENTAL_TOXINS, OTHER
- `LabResult` — id, date (LocalDate), labName?, notes?
- `BiomarkerEntry` — id, labResultId, biomarkerId, value (one biomarker value within a result)

### Repository Interfaces (`domain/repository/`)

- `BiomarkerRepository` — `getAll()`, `getById()`, `getPinned()`, `save()`, `update()`
- `LabResultRepository` — `getAll(): Flow<List<LabResult>>`, `getById()`, `save()`, `delete()`
- `BiomarkerEntryRepository` — `getEntriesForBiomarker()`, `getEntriesForLabResult()`, `save()`, `delete()`

## Notes

- No Room annotations on domain models — plain Kotlin data classes only
- Repository interfaces define the contract; implementations come in Phase 1.3

## References

- @context/specs/phase1-2-domain-models-spec.md
- @context/coding-standards.md

## History

- Initial Android Studio project created
- Core dependencies added (Hilt, Room, Navigation, Vico, Serialization)
- Hilt wired up (`LabRatApplication`, `MainActivity`)
- Project structure scaffolded to match clean architecture layout
- LabRat Material 3 theme applied (Color, Type, Shape, Theme)
- Bottom navigation shell — 4 tabs (Dashboard, Biomarkers, History, Settings) with placeholder screens