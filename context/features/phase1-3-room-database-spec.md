# Phase 1.3 — Room Database

## Overview

Implement the Room database — entities, DAOs, database class, mappers, Hilt module, and biomarker seed data.

## Requirements

### Room Entities (`data/local/entity/`)

- `BiomarkerEntity`, `LabResultEntity`, `BiomarkerEntryEntity`
- Mirror the domain models from Phase 1.2 with `@Entity` annotations
- Store `LocalDate` as `Long` (epoch day) via a `TypeConverter`

### DAOs (`data/local/dao/`)

- `BiomarkerDao` — insert, update, `getAll(): Flow<List<BiomarkerEntity>>`, `getById()`, `getPinned()`
- `LabResultDao` — insert, update, delete, `getAll(): Flow<List<LabResultEntity>>`, `getById()`
- `BiomarkerEntryDao` — insert, delete, `getEntriesForBiomarker(biomarkerId): Flow<List<BiomarkerEntryEntity>>`, `getEntriesForLabResult(labResultId)`

### Database (`data/local/AppDatabase.kt`)

- Room database class, version 1
- Registers all three DAOs and the `LocalDate` TypeConverter

### Mappers (`data/local/mapper/`)

- Extension functions to map each entity → domain model and back
- e.g. `BiomarkerEntity.toDomain(): Biomarker`, `Biomarker.toEntity(): BiomarkerEntity`

### Repository Implementations (`data/repository/`)

- Implement the three repository interfaces from Phase 1.2
- Use mappers to convert between entity and domain layers

### Biomarker Seed Data (`data/local/BiomarkerSeeds.kt`)

- Hardcoded list of 100+ common biomarkers with name, unit, category, and standard reference ranges
- Must include entries across all 13 categories
- Seeding logic runs on first launch if the `Biomarker` table is empty

### Hilt Module (`di/DatabaseModule.kt`)

- Provide `AppDatabase` as `@Singleton`
- Provide all three DAOs from the database instance
- Provide all three repository implementations bound to their interfaces

## Notes

- Run `./gradlew build` — fix all warnings before committing
- Verify seed data with Android Studio Database Inspector before moving on

## References

- @context/coding-standards.md
- @context/specs/phase1-2-domain-models-spec.md
