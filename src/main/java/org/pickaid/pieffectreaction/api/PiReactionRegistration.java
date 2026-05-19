package org.pickaid.pieffectreaction.api;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import org.pickaid.pibrary.api.signal.PiSignalType;

public record PiReactionRegistration(
        ResourceLocation id,
        PiSignalType<?> signalType,
        int priority,
        PiReactionAction action
) {
    public PiReactionRegistration {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(signalType, "signalType");
        Objects.requireNonNull(action, "action");
    }
}
