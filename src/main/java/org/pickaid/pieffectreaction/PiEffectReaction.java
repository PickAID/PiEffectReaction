package org.pickaid.pieffectreaction;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(PiEffectReaction.MOD_ID)
public final class PiEffectReaction {
    public static final String MOD_ID = "pieffectreaction";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PiEffectReaction() {
        LOGGER.info("Initializing {}", MOD_ID);
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
