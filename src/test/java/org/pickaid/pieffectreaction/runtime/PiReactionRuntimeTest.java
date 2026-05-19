package org.pickaid.pieffectreaction.runtime;

import net.minecraft.resources.ResourceLocation;
import org.junit.jupiter.api.Test;
import org.pickaid.pieffectreaction.api.PiReactionBudget;
import org.pickaid.pieffectreaction.api.PiReactionRegistration;
import org.pickaid.pieffectreaction.api.PiReactions;
import org.pickaid.pibrary.api.signal.PiSignalFrame;
import org.pickaid.pibrary.api.signal.PiSignalType;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

final class PiReactionRuntimeTest {
    @Test
    void dispatchRunsRegisteredReactionAndRecordsTrace() {
        PiSignalType<TestPayload> type = PiSignalType.create(id("test:hit"), TestPayload.class);
        PiReactionRegistration registration = PiReactions.on(type)
                .id(id("test:chain"))
                .action(context -> context.emit(id("test:follow_up")))
                .build();

        PiReactionRuntime runtime = PiReactionRuntime.compile(PiReactionBudget.defaults(), registration);
        PiReactionDispatchResult result = runtime.dispatch(PiSignalFrame.builder(type).payload(new TestPayload(1)).build());

        assertEquals(1, result.executedCount());
        assertTrue(result.trace().emitted().contains(id("test:follow_up")));
    }

    @Test
    void budgetBlocksRecursiveDepthBeforeActionRuns() {
        PiSignalType<TestPayload> type = PiSignalType.create(id("test:loop"), TestPayload.class);
        PiReactionRegistration registration = PiReactions.on(type)
                .id(id("test:loop"))
                .action(context -> context.dispatch(context.signal()))
                .build();

        PiReactionRuntime runtime = PiReactionRuntime.compile(new PiReactionBudget(8, 1), registration);
        PiReactionDispatchResult result = runtime.dispatch(PiSignalFrame.builder(type).payload(new TestPayload(1)).build());

        assertEquals(1, result.executedCount());
        assertEquals(1, result.trace().blocked().size());
    }

    private static ResourceLocation id(String value) {
        return Objects.requireNonNull(ResourceLocation.tryParse(value));
    }

    private record TestPayload(int value) {
    }
}
