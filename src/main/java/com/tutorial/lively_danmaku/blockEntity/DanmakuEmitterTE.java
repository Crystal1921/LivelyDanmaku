package com.tutorial.lively_danmaku.blockEntity;

import com.tutorial.lively_danmaku.gui.EmitterMenu;
import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DanmakuEmitterTE extends RandomizableContainerBlockEntity{
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    public DanmakuEmitterTE(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.DANMAKU_EMITTER.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state,DanmakuEmitterTE danmakuEmitterTE){

    }

    public void load(CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items);
        }

    }

    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items);
        }

    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("ui.danmaku_emitter");
    }

    @Override
    protected @NotNull NonNullList<ItemStack> getItems() {
        return this.items;
    }

    protected void setItems(@NotNull NonNullList<ItemStack> pItems) {
        this.items = pItems;
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pInventory) {
        return new EmitterMenu(pContainerId,pInventory,this,this);
    }

    @Override
    public int getContainerSize() {
        return 27;
    }
}
