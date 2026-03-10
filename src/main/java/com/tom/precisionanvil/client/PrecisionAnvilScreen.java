package com.tom.precisionanvil.client;

import com.tom.precisionanvil.menu.PrecisionAnvilMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;

public final class PrecisionAnvilScreen extends AnvilScreen {
    public PrecisionAnvilScreen(AnvilMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 0x404040, false);
        guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, 0x404040, false);

        PrecisionAnvilMenu precisionMenu = (PrecisionAnvilMenu) this.menu;
        if (!precisionMenu.getSlot(2).hasItem()) {
            return;
        }

        int usedLevels = precisionMenu.getUsedLevels();
        int requiredLevels = precisionMenu.getRequiredLevels();
        Component infoText = Component.translatable("precisionanvil.ui.costs_compact", usedLevels, requiredLevels);

        int textWidth = this.font.width(infoText);
        int maxWidth = this.imageWidth - 16;
        float scale = Math.max(0.75F, Math.min(1.0F, maxWidth / (float) textWidth));

        int right = this.imageWidth - 8;
        int infoY = 66;

        Player player = this.minecraft != null ? this.minecraft.player : null;
        boolean canPickup = player != null && precisionMenu.getSlot(2).mayPickup(player);
        int color = canPickup ? 0x80FF20 : 0xFF6060;

        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(scale, scale, 1.0F);

        int scaledRight = (int) (right / scale);
        int scaledLeft = scaledRight - textWidth;
        int scaledY = (int) (infoY / scale);

        guiGraphics.drawString(this.font, infoText, scaledLeft, scaledY, color, true);

        guiGraphics.pose().popPose();
    }
}
