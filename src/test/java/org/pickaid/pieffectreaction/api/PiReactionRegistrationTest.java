package org.pickaid.pieffectreaction.api;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import org.pickaid.pibrary.api.signal.PiSignalType;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class PiReactionRegistrationTest {
    @Test
    void registrationCarriesSignalTypePriorityAndAction() {
        PiSignalType<TestPayload> type = PiSignalType.create(id("test:damage_applied"), TestPayload.class);
        PiReactionRegistration registration = PiReactions.on(type)
                .id(id("test:ignite_on_hit"))
                .priority(100)
                .action(context -> context.emit(id("test:burn_ready")))
                .build();

        assertEquals(id("test:ignite_on_hit"), registration.id());
        assertEquals(type, registration.signalType());
        assertEquals(100, registration.priority());
    }

    private static ResourceLocation id(String value) {
        return Objects.requireNonNull(ResourceLocation.tryParse(value));
    }

    private record TestPayload(int amount) {
    }
}
