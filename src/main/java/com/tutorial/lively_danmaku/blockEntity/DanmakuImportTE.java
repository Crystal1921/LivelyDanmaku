package com.tutorial.lively_danmaku.blockEntity;

import com.tutorial.lively_danmaku.gui.DanmakuImportMenu;
import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class DanmakuImportTE extends RandomizableContainerBlockEntity {
    private NonNullList<ItemStack> items = NonNullList.withSize(1, ItemStack.EMPTY);
    public DanmakuImportTE(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.DANMAKU_IMPORT.get(), pPos, pBlockState);
    }

    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items);
        }
    }

    protected void saveAdditional(@NotNull CompoundTag pTag) {
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items);
        }
        super.saveAdditional(pTag);
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(@NotNull NonNullList<ItemStack> pItems) {
        this.items = pItems;
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("block.lively_danmaku.danmaku_import");
    }

    @Override
    protected AbstractContainerMenu createMenu(int pContainerId, Inventory pInventory) {
        return new DanmakuImportMenu(pContainerId,pInventory,this,this);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }
}
