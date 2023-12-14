package com.tutorial.lively_danmaku.blockEntity;

import com.tutorial.lively_danmaku.gui.EmitterMenu;
import com.tutorial.lively_danmaku.init.BlockEntityTypeRegistry;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import com.tutorial.lively_danmaku.item.ItemHakureiGohei;
import com.tutorial.lively_danmaku.item.ItemSanaeGohei;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.RandomizableContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class DanmakuEmitterTE extends RandomizableContainerBlockEntity{
    public float XRot;
    public float YRot;
    public int freq = 20;
    public double posX;
    public double posY;
    public double posZ;
    private NonNullList<ItemStack> items = NonNullList.withSize(27, ItemStack.EMPTY);
    private int countTick = 0;
    public DanmakuEmitterTE(BlockPos pPos, BlockState pBlockState) {
        super(BlockEntityTypeRegistry.DANMAKU_EMITTER.get(), pPos, pBlockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state,DanmakuEmitterTE danmakuEmitterTE){
        danmakuEmitterTE.countTick ++;
        ItemStack itemStack = danmakuEmitterTE.items.get(0);
        if (danmakuEmitterTE.countTick >= danmakuEmitterTE.freq){
            danmakuEmitterTE.countTick = 0;
            if (itemStack.is(ItemRegistry.HakureiGohei.get())) {
                ItemHakureiGohei item = (ItemHakureiGohei) itemStack.getItem();
                item.HakureiShoot(itemStack, level, null, pos.getX(),pos.getY(),pos.getZ(), danmakuEmitterTE.XRot, danmakuEmitterTE.YRot,false);
            } else if (itemStack.is(ItemRegistry.SanaeGohei.get())) {
                ItemSanaeGohei item = (ItemSanaeGohei) itemStack.getItem();
                item.SanaeShoot(level,itemStack,danmakuEmitterTE.XRot,danmakuEmitterTE.YRot,pos.getX(),pos.getY(),pos.getZ());
            }
        }
    }

    public void load(@NotNull CompoundTag pTag) {
        super.load(pTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(pTag)) {
            ContainerHelper.loadAllItems(pTag, this.items);
        }
        this.XRot = getPersistentData().getFloat("XRot");
        this.YRot = getPersistentData().getFloat("YRot");
        this.freq = getPersistentData().getInt("Freq");
        this.posX = getPersistentData().getDouble("posX");
        this.posY = getPersistentData().getDouble("posY");
        this.posZ = getPersistentData().getDouble("posZ");
    }

    protected void saveAdditional(@NotNull CompoundTag pTag) {
        super.saveAdditional(pTag);
        if (!this.trySaveLootTable(pTag)) {
            ContainerHelper.saveAllItems(pTag, this.items);
        }
        getPersistentData().putFloat("XRot",XRot);
        getPersistentData().putFloat("YRot",YRot);
        getPersistentData().putInt("Freq",freq);
        getPersistentData().putDouble("posX",posX);
        getPersistentData().putDouble("posY",posY);
        getPersistentData().putDouble("posZ",posZ);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    @Nullable
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public void refresh() {
        this.setChanged();
        if (level != null) {
            BlockState state = level.getBlockState(worldPosition);
            level.sendBlockUpdated(worldPosition, state, state, Block.UPDATE_ALL);
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
