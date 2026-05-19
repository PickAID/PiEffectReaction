package org.pickaid.pieffectreaction.api;

@FunctionalInterface
public interface PiReactionAction {
    void run(PiReactionContext context);
}
