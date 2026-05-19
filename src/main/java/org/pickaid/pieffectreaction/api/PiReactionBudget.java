package org.pickaid.pieffectreaction.api;

public record PiReactionBudget(int maxSignalsPerTick, int maxDepth) {
    public PiReactionBudget {
        if (maxSignalsPerTick < 1) {
            throw new IllegalArgumentException("maxSignalsPerTick must be positive");
        }
        if (maxDepth < 0) {
            throw new IllegalArgumentException("maxDepth must be non-negative");
        }
    }

    public static PiReactionBudget defaults() {
        return new PiReactionBudget(256, 16);
    }
}
