# LabRat — Project Schema

> Visualize lab results over time. Track 100+ biomarkers and their trends at a glance.

---

## Tech Stack

- **Language**: Kotlin
- **UI**: Jetpack Compose + Material 3
- **Architecture**: MVVM + Clean Architecture
- **DI**: Hilt
- **Local DB**: Room
- **Navigation**: Jetpack Navigation Compose
- **Charts**: [Vico](https://github.com/patrykandpatrick/vico) (Compose-native charting)
- **Serialization**: Kotlin Serialization

---

## Phase 1 — Foundation

> Goal: Core data model and app skeleton. Nothing fancy, just solid.

- [x] Project setup (Hilt, Room, Navigation, Material 3 theming)
- [x] Define core domain models: `LabResult`, `Biomarker`, `BiomarkerCategory`
- [x] Room database schema + DAOs for results and biomarkers
- [x] Bottom navigation shell (Dashboard, Biomarkers, History, Settings)
- [x] App-wide theming (light + dark mode, LabRat brand colors)
- [x] Seed with a predefined list of 100+ common biomarkers (name, unit, standard reference range)

---

## Phase 2 — Data Entry

> Goal: Users can log their lab results manually.

- [x] Add Lab Result screen — select date, lab name (optional)
- [x] Add/edit individual biomarker values within a result
- [x] Biomarker picker — searchable, grouped by category (e.g. Metabolic, Lipids, CBC, Hormones, Thyroid, Vitamins, Inflammation)
- [x] Input validation (numeric ranges, units)
- [x] Save lab result
- [ ] Delete lab result
- [x] Empty state on Dashboard when no data exists

---

## Phase 3 — Dashboard & Visualization

> Goal: Make the data beautiful and immediately useful.

- [x] Dashboard screen — summary cards for pinned biomarkers with status dot and trend arrow
- [x] Color-coded status indicators: In Range 🟢 / Borderline 🟡 / Out of Range 🔴
- [ ] Biomarker detail screen — trend line chart over time (Vico)
- [ ] Reference range overlay on chart
- [ ] Date range filter (3 months / 6 months / 1 year / All time)
- [x] Biomarker list screen — all tracked markers, grouped by category

---

## Phase 4 — History & Context

> Goal: Make it easy to review the full picture over time.

- [ ] History screen — chronological list of all lab result entries
- [ ] Lab result detail view — all biomarkers from a single draw
- [ ] Delta indicator — show change vs. previous result (↑ ↓ →)
- [ ] Highlight out-of-range values in result detail view
- [ ] Notes field per lab result entry

---

## Phase 5 — Biomarker Management

> Goal: Let users customize what they track.

- [ ] Custom biomarker creation (name, unit, reference range)
- [ ] Edit reference ranges per biomarker (personalized targets)
- [x] Pin/favorite biomarkers to Dashboard
- [ ] Archive/hide unused biomarkers
- [ ] Biomarker category management

---

## Phase 6 — Import & Export

> Goal: Meet users where their data already is.

- [ ] Export all data as CSV
- [ ] Export a single lab result as PDF summary
- [ ] Import from CSV (map columns to biomarkers)
- [ ] Camera/OCR import from lab result documents (stretch goal)

---

## Phase 7 — Polish & Notifications

> Goal: Delight and retention.

- [ ] Onboarding flow (what is this app, how to use it)
- [ ] Reminder notifications to log new results
- [ ] Widgets (home screen widget for key biomarker status)
- [ ] Animations and transitions
- [ ] Accessibility audit (content descriptions, touch targets)
- [ ] Performance profiling

---

## Out of Scope (for now)

- Cloud sync / user accounts
- Sharing results with doctors
- AI-generated health advice
- Wearable integrations

---

## Context Files

| File | Purpose |
|---|---|
| `context/ai-guidelines.md` | How Claude Code should behave |
| `context/current-feature.md` | Active feature being worked on |
| `context/coding-standards.md` | Kotlin/Compose/architecture rules |
| `context/project-schema.md` | This file — phases and roadmap |
| `context/specs/` | Pending feature specs, one per feature |
| `context/features/` | Completed feature docs, one per feature |
