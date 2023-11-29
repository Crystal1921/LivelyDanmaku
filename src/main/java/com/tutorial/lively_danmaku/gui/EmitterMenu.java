package com.tutorial.lively_danmaku.gui;

import com.tutorial.lively_danmaku.blockEntity.DanmakuEmitterTE;
import com.tutorial.lively_danmaku.init.BlockRegistry;
import com.tutorial.lively_danmaku.init.MenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import org.jetbrains.annotations.NotNull;

public class EmitterMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    private final Container container;
    private final DanmakuEmitterTE danmakuEmitterTE;
    public EmitterMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id,inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()),new SimpleContainer(2));
    }

    public EmitterMenu(int id, Inventory inventory, BlockEntity blockEntity, Container container) {
        super(MenuRegistry.EMITTER_MENU.get(),id);
        this.access = ContainerLevelAccess.NULL;
        this.container = container;
        this.danmakuEmitterTE = (DanmakuEmitterTE) blockEntity;
        this.addSlot(new Slot(container,0,25,27));
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, 8 + k * 18, 142));
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player pPlayer, int pIndex) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(pIndex);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (pIndex == 0) {
                if (!this.moveItemStackTo(itemstack1, 1, 37, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(itemstack1, itemstack);
            } else if (this.moveItemStackTo(itemstack1, 0, 1, false)) { //Forge Fix Shift Clicking in beacons with stacks larger then 1.
                return ItemStack.EMPTY;
            } else if (pIndex >= 1 && pIndex < 28) {
                if (!this.moveItemStackTo(itemstack1, 28, 37, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (pIndex >= 28 && pIndex < 37) {
                if (!this.moveItemStackTo(itemstack1, 1, 28, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 1, 37, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, itemstack1);
        }

        return itemstack;
    }
    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer, BlockRegistry.DANMAKU_EMITTER.get());
    }

    public DanmakuEmitterTE getDanmakuEmitterTE () {
        return this.danmakuEmitterTE;
    }
}
