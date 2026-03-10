package com.tom.precisionanvil.menu;

import com.tom.precisionanvil.config.PrecisionAnvilConfig;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class PrecisionAnvilMenu extends AnvilMenu {
    private static final int RESULT_SLOT = 2;

    private final DataSlot usedLevels = DataSlot.standalone();
    private final DataSlot requiredLevels = DataSlot.standalone();

    public PrecisionAnvilMenu(int containerId, Inventory playerInventory, RegistryFriendlyByteBuf extraData) {
        this(containerId, playerInventory, ContainerLevelAccess.create(playerInventory.player.level(), extraData.readBlockPos()));
    }

    public PrecisionAnvilMenu(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        super(containerId, playerInventory, access);
        addDataSlot(usedLevels);
        addDataSlot(requiredLevels);
    }

    @Override
    public MenuType<?> getType() {
        return ModMenus.PRECISION_ANVIL.get();
    }

    @Override
    public void createResult() {
        super.createResult();

        if (!getSlot(RESULT_SLOT).hasItem()) {
            usedLevels.set(0);
            requiredLevels.set(0);
            return;
        }

        int vanillaCost = getCost();
        int adjustedRequired = PrecisionAnvilConfig.computeRequiredLevels(vanillaCost);
        int adjustedUsed = PrecisionAnvilConfig.computeUsedLevels(adjustedRequired);

        usedLevels.set(adjustedUsed);
        requiredLevels.set(adjustedRequired);
    }

    @Override
    protected boolean mayPickup(Player player, boolean hasStack) {
        return hasStack && canTakeResult(player);
    }

    @Override
    public void clicked(int slotId, int button, ClickType clickType, Player player) {
        if (slotId == RESULT_SLOT && getSlot(RESULT_SLOT).hasItem() && !canTakeResult(player)) {
            return;
        }
        super.clicked(slotId, button, clickType, player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        if (index == RESULT_SLOT && getSlot(RESULT_SLOT).hasItem() && !canTakeResult(player)) {
            return ItemStack.EMPTY;
        }
        return super.quickMoveStack(player, index);
    }

    @Override
    protected void onTake(Player player, ItemStack stack) {
        int adjustedUsed = usedLevels.get();
        int levelBefore = player.experienceLevel;

        super.onTake(player, stack);

        if (player.getAbilities().instabuild) {
            return;
        }

        int targetLevel = Math.max(0, levelBefore - adjustedUsed);
        int adjustment = targetLevel - player.experienceLevel;
        if (adjustment != 0) {
            player.giveExperienceLevels(adjustment);
        }
    }

    public int getUsedLevels() {
        return usedLevels.get();
    }

    public int getRequiredLevels() {
        return requiredLevels.get();
    }

    private boolean canTakeResult(Player player) {
        return player.getAbilities().instabuild || player.experienceLevel >= requiredLevels.get();
    }
}
