package org.pickaid.pieffectreaction.api;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PiThresholdReactionTest {
    @Test
    void thresholdTriggersWhenObservedValueMeetsMinimum() {
        PiThresholdReaction threshold = PiThresholdReaction.atLeast(id("test:rage_ready"), 10);

        assertFalse(threshold.matches(9));
        assertTrue(threshold.matches(10));
    }

    private static ResourceLocation id(String value) {
        return Objects.requireNonNull(ResourceLocation.tryParse(value));
    }
}
