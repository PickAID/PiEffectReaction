package org.pickaid.pieffectreaction.runtime;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public final class PiReactionTrace {
    private final List<ResourceLocation> emitted = new ArrayList<>();
    private final List<String> blocked = new ArrayList<>();

    public List<ResourceLocation> emitted() {
        return List.copyOf(emitted);
    }

    public List<String> blocked() {
        return List.copyOf(blocked);
    }

    void emit(ResourceLocation signalId) {
        emitted.add(signalId);
    }

    void block(String reason) {
        blocked.add(reason);
    }
}
