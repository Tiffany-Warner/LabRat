# Current Feature

Phase 1.3 — Room Database

## Status

In-Progress

## Goals

Implement the full Room database layer — entities, DAOs, database class, TypeConverter, mappers, repository implementations, Hilt module, and biomarker seed data.

### Deliverables

**Entities** (`data/local/entity/`)
- `BiomarkerEntity`, `LabResultEntity`, `BiomarkerEntryEntity`
- Mirror Phase 1.2 domain models with `@Entity` annotations
- `LocalDate` stored as `Long` (epoch day) via a TypeConverter

**DAOs** (`data/local/dao/`)
- `BiomarkerDao` — insert, update, `getAll(): Flow`, `getById()`, `getPinned()`
- `LabResultDao` — insert, update, delete, `getAll(): Flow`, `getById()`
- `BiomarkerEntryDao` — insert, delete, `getEntriesForBiomarker(): Flow`, `getEntriesForLabResult()`

**Database** (`data/local/AppDatabase.kt`)
- Room database class, version 1, registers all DAOs and the TypeConverter

**Mappers** (`data/local/mapper/`)
- Extension functions: `BiomarkerEntity.toDomain()`, `Biomarker.toEntity()`, and equivalents for LabResult and BiomarkerEntry

**Repository Implementations** (`data/repository/`)
- `BiomarkerRepositoryImpl`, `LabResultRepositoryImpl`, `BiomarkerEntryRepositoryImpl`
- Implement Phase 1.2 interfaces, use mappers to convert between layers

**Seed Data** (`data/local/BiomarkerSeeds.kt`)
- Hardcoded list of 100+ common biomarkers with name, unit, category, and reference ranges
- Covers all 13 categories
- Seeding runs on first launch if the Biomarker table is empty

**Hilt Module** (`di/DatabaseModule.kt`)
- Provides `AppDatabase` as `@Singleton`
- Provides all three DAOs
- Binds all three repository implementations to their interfaces

## Notes

- Fix all warnings before committing (`./gradlew build`)
- Verify seed data with Android Studio Database Inspector before moving on

## References

- @context/specs/phase1-3-room-database-spec.md
- @context/coding-standards.md

## History

- Initial Android Studio project created
- Core dependencies added (Hilt, Room, Navigation, Vico, Serialization)
- Hilt wired up (`LabRatApplication`, `MainActivity`)
- Project structure scaffolded to match clean architecture layout
- LabRat Material 3 theme applied (Color, Type, Shape, Theme)
- Bottom navigation shell — 4 tabs (Dashboard, Biomarkers, History, Settings) with placeholder screens
- Domain models defined (`Biomarker`, `BiomarkerCategory`, `LabResult`, `BiomarkerEntry`)
- Repository interfaces defined (`BiomarkerRepository`, `LabResultRepository`, `BiomarkerEntryRepository`)