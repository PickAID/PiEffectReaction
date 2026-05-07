# PiEffectReaction

[中文](README.MD) | English

PiEffectReaction is the effect-reaction bridge for the Pi stack. It does not replace `PiDataGraph`, and it does not own entity state. It connects the two:

- `PiEntityFX` or the future entity runtime owns effect state, meters, counters, marks, cooldowns, and signals.
- `PiDataGraph` owns datapack-authored reaction plans, validation, branching, delays, execution budgets, and action composition.
- PiEffectReaction routes effect signals into bounded reaction execution and applies the results back to entity runtime state or visible sync cues.

## Pure Library Or Minecraft Mod

This project should be published as a Minecraft mod, not as a pure Java-only library. Its real use cases need `LivingEntity`, datapack reload, runtime diagnostics, targeting, sync, and game-state application.

The implementation should still keep a thin core:

- `api` for stable public contracts.
- `runtime` for server-authoritative queues, guards, cooldowns, and diagnostics.
- `bridge` for Pibrary, PiDataGraph, PiEngine, and visual-pack integration.

## Current Scope

This repository is only initialized with the project skeleton and package boundaries. It intentionally does not ship half-finished reaction APIs before the first production slice proves the exact entity-effect and data-graph integration.

## Build

```bash
./gradlew build
```
