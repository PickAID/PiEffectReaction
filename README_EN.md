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

## P0 Scope

The current version provides the minimal reaction contract and server runtime base:

- `PiReactions.on(signalType)` registers typed signal reactions.
- `PiReactionBudget` limits signal count and recursive depth.
- `PiReactionRuntime` compiles registrations and dispatches matching reactions by priority.
- `PiReactionTrace` records emitted signal ids and budget/cycle guard blocks.
- `PiThresholdReaction` provides the first threshold trigger shape.

P0 does not own entity effect state, mutate the world directly, replace PiEngine state ownership, or turn PiDataGraph into a global signal loop.

## Build

```bash
./gradlew build
```
