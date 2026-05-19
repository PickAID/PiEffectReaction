package org.pickaid.pieffectreaction.api;

import net.minecraft.resources.ResourceLocation;
import org.pickaid.pibrary.api.signal.PiSignal;

public interface PiReactionContext {
    PiSignal signal();

    void emit(ResourceLocation signalId);

    void dispatch(PiSignal signal);
}
