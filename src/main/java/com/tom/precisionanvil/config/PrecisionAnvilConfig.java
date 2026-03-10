package com.tom.precisionanvil.config;

import net.minecraft.util.Mth;
import net.neoforged.neoforge.common.ModConfigSpec;

public final class PrecisionAnvilConfig {
    public static final ModConfigSpec SERVER_SPEC;
    public static final Server SERVER;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();
        SERVER = new Server(builder);
        SERVER_SPEC = builder.build();
    }

    private PrecisionAnvilConfig() {
    }

    public static int computeRequiredLevels(int vanillaCost) {
        if (vanillaCost <= 0) {
            return 0;
        }

        int required = (int) Math.round(vanillaCost * SERVER.requiredLevelMultiplierFromVanilla.get());
        return Mth.clamp(required, 0, SERVER.requiredLevelCap.get());
    }

    public static int computeUsedLevels(int requiredLevels) {
        if (requiredLevels <= 0) {
            return 0;
        }

        int used = (int) Math.round(requiredLevels * SERVER.usedLevelMultiplierFromRequired.get());
        return Math.max(0, used);
    }

    public static final class Server {
        public final ModConfigSpec.IntValue requiredLevelCap;
        public final ModConfigSpec.DoubleValue requiredLevelMultiplierFromVanilla;
        public final ModConfigSpec.DoubleValue usedLevelMultiplierFromRequired;

        private Server(ModConfigSpec.Builder builder) {
            builder.push("anvil_costs");
            requiredLevelCap = builder
                .comment("Maximum required level to take the anvil result.")
                .defineInRange("required_level_cap", 40, 0, 10000);
            requiredLevelMultiplierFromVanilla = builder
                .comment("Required level = round(vanilla_cost * this_multiplier), then clamped by required_level_cap.")
                .defineInRange("required_level_multiplier_from_vanilla", 1.0D, 0.0D, 64.0D);
            usedLevelMultiplierFromRequired = builder
                .comment("Used level = round(required_level * this_multiplier). Example: required 200 and 0.5 = uses 100.")
                .defineInRange("used_level_multiplier_from_required", 1.0D, 0.0D, 64.0D);
            builder.pop();
        }
    }
}
