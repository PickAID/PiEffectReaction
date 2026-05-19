package org.pickaid.pieffectreaction.api;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;

public record PiThresholdReaction(ResourceLocation id, int minimum) {
    public PiThresholdReaction {
        Objects.requireNonNull(id, "id");
    }

    public static PiThresholdReaction atLeast(ResourceLocation id, int minimum) {
        return new PiThresholdReaction(id, minimum);
    }

    public boolean matches(int value) {
        return value >= minimum;
    }
}
