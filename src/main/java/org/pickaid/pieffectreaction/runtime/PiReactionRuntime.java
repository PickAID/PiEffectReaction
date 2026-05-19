package org.pickaid.pieffectreaction.runtime;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import org.pickaid.pieffectreaction.api.PiReactionBudget;
import org.pickaid.pieffectreaction.api.PiReactionContext;
import org.pickaid.pieffectreaction.api.PiReactionRegistration;
import org.pickaid.pibrary.api.signal.PiSignal;
import org.pickaid.pibrary.api.signal.PiSignalType;

public final class PiReactionRuntime {
    private final PiReactionBudget budget;
    private final List<PiReactionRegistration> registrations;

    private PiReactionRuntime(PiReactionBudget budget, List<PiReactionRegistration> registrations) {
        this.budget = Objects.requireNonNull(budget, "budget");
        this.registrations = List.copyOf(registrations);
    }

    public static PiReactionRuntime compile(PiReactionBudget budget, PiReactionRegistration... registrations) {
        List<PiReactionRegistration> sorted = new ArrayList<>(List.of(registrations));
        sorted.sort(Comparator.comparingInt(PiReactionRegistration::priority).reversed());
        return new PiReactionRuntime(budget, sorted);
    }

    public PiReactionDispatchResult dispatch(PiSignal signal) {
        PiReactionTrace trace = new PiReactionTrace();
        DispatchState state = new DispatchState();
        dispatch(signal, trace, state, 0);
        return new PiReactionDispatchResult(state.executedCount, trace);
    }

    private void dispatch(PiSignal signal, PiReactionTrace trace, DispatchState state, int depth) {
        if (depth >= budget.maxDepth()) {
            trace.block("max_depth");
            return;
        }
        if (state.seenSignals >= budget.maxSignalsPerTick()) {
            trace.block("max_signals_per_tick");
            return;
        }
        state.seenSignals++;

        for (PiReactionRegistration registration : registrations) {
            if (!matches(registration.signalType(), signal.type())) {
                continue;
            }
            state.executedCount++;
            registration.action().run(new RuntimeContext(signal, trace, state, depth));
        }
    }

    private static boolean matches(PiSignalType<?> expected, PiSignalType<?> actual) {
        return expected.equals(actual);
    }

    private final class RuntimeContext implements PiReactionContext {
        private final PiSignal signal;
        private final PiReactionTrace trace;
        private final DispatchState state;
        private final int depth;

        private RuntimeContext(PiSignal signal, PiReactionTrace trace, DispatchState state, int depth) {
            this.signal = signal;
            this.trace = trace;
            this.state = state;
            this.depth = depth;
        }

        @Override
        public PiSignal signal() {
            return signal;
        }

        @Override
        public void emit(net.minecraft.resources.ResourceLocation signalId) {
            trace.emit(signalId);
        }

        @Override
        public void dispatch(PiSignal signal) {
            PiReactionRuntime.this.dispatch(signal, trace, state, depth + 1);
        }
    }

    private static final class DispatchState {
        private int seenSignals;
        private int executedCount;
    }
}
