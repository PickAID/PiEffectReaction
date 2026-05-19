package org.pickaid.pieffectreaction.api;

import java.util.Objects;
import net.minecraft.resources.ResourceLocation;
import org.pickaid.pibrary.api.signal.PiSignalType;

public final class PiReactions {
    private PiReactions() {
    }

    public static Builder on(PiSignalType<?> signalType) {
        return new Builder(signalType);
    }

    public static final class Builder {
        private final PiSignalType<?> signalType;
        private ResourceLocation id;
        private int priority;
        private PiReactionAction action = context -> {
        };

        private Builder(PiSignalType<?> signalType) {
            this.signalType = Objects.requireNonNull(signalType, "signalType");
        }

        public Builder id(ResourceLocation id) {
            this.id = Objects.requireNonNull(id, "id");
            return this;
        }

        public Builder priority(int priority) {
            this.priority = priority;
            return this;
        }

        public Builder action(PiReactionAction action) {
            this.action = Objects.requireNonNull(action, "action");
            return this;
        }

        public PiReactionRegistration build() {
            if (id == null) {
                throw new IllegalStateException("Reaction id is required");
            }
            return new PiReactionRegistration(id, signalType, priority, action);
        }
    }
}
