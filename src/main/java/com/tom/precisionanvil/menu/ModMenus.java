package com.tom.precisionanvil.menu;

import com.tom.precisionanvil.PrecisionAnvil;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, PrecisionAnvil.MOD_ID);

    public static final DeferredHolder<MenuType<?>, MenuType<PrecisionAnvilMenu>> PRECISION_ANVIL =
        MENU_TYPES.register("precision_anvil", () -> IMenuTypeExtension.create(PrecisionAnvilMenu::new));

    private ModMenus() {
    }

    public static void register(IEventBus modEventBus) {
        MENU_TYPES.register(modEventBus);
    }
}

