# LabRat

A personal health tracking app for visualizing lab results and biomarker trends over time.

## Context Files

Read the following at the start of every session:

- @context/ai-guidelines.md
- @context/coding-standards.md
- @context/current-feature.md
- @context/project-schema.md

## Commands

```bash
./gradlew build          # full project build — run before every commit
./gradlew assembleDebug  # build debug APK
./gradlew test           # run unit tests
./gradlew lint           # run lint checks
```

## Stack

- **Kotlin** with **Jetpack Compose** (Material 3)
- **Hilt** for dependency injection
- **Room** for local persistence
- **Jetpack Navigation Compose** for navigation
- **Vico** for trend line charts
- **Kotlin Serialization** for data serialization
- Min SDK 26, Target SDK 35

## Architecture

MVVM + Clean Architecture. Features are organized into three layers:

- `ui/` — Composables and ViewModels, organized by feature
- `domain/` — Use cases, repository interfaces, and domain models (pure Kotlin)
- `data/` — Repository implementations, Room entities, DAOs, and mappers

All navigation routes are defined in `ui/navigation/Routes.kt`.
