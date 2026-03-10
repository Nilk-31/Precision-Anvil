package com.tom.precisionanvil;

import com.tom.precisionanvil.config.PrecisionAnvilConfig;
import com.tom.precisionanvil.menu.ModMenus;
import com.tom.precisionanvil.menu.PrecisionAnvilMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@Mod(PrecisionAnvil.MOD_ID)
public final class PrecisionAnvil {
    public static final String MOD_ID = "precisionanvil";

    public PrecisionAnvil(IEventBus modEventBus, ModContainer modContainer) {
        ModMenus.register(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, PrecisionAnvilConfig.SERVER_SPEC);
        NeoForge.EVENT_BUS.addListener(this::onRightClickBlock);
    }

    private void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getHand() != InteractionHand.MAIN_HAND) {
            return;
        }

        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        if (!level.getBlockState(pos).is(BlockTags.ANVIL)) {
            return;
        }

        if (level.isClientSide() || !(event.getEntity() instanceof ServerPlayer serverPlayer)) {
            return;
        }

        MenuProvider provider = new SimpleMenuProvider(
            (containerId, playerInventory, player) ->
                new PrecisionAnvilMenu(containerId, playerInventory, ContainerLevelAccess.create(level, pos)),
            Component.translatable("container.repair")
        );

        serverPlayer.openMenu(provider, buffer -> buffer.writeBlockPos(pos));
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }
}


