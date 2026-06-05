# LabRat

A personal Android app for logging lab results and visualizing biomarker trends over time. Track 100+ biomarkers, see color-coded status indicators, and watch your health data unfold as charts.

This project is also being used as a hands-on learning exercise for building real software with agentic AI tools (Claude Code).

## Features (planned)

- Log lab results and individual biomarker values
- Trend charts per biomarker over time
- In-range / borderline / out-of-range indicators
- Full history view with delta indicators
- CSV import/export

See [`context/project-schema.md`](context/project-schema.md) for the full phased roadmap.

## Stack

- **Kotlin** + **Jetpack Compose** (Material 3)
- **Hilt** for dependency injection
- **Room** for local persistence
- **Jetpack Navigation Compose**
- **Vico** for trend charts
- Min SDK 26, Target SDK 35

## Architecture

MVVM + Clean Architecture with three layers: `ui/`, `domain/`, `data/`.

## Build

```bash
./gradlew build          # full build
./gradlew assembleDebug  # debug APK
./gradlew test           # unit tests
./gradlew lint           # lint checks
```