package com.tom.precisionanvil.client;

import com.tom.precisionanvil.PrecisionAnvil;
import com.tom.precisionanvil.menu.ModMenus;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@EventBusSubscriber(modid = PrecisionAnvil.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class PrecisionAnvilClient {
    private PrecisionAnvilClient() {
    }

    @SubscribeEvent
    public static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.PRECISION_ANVIL.get(), PrecisionAnvilScreen::new);
    }
}

