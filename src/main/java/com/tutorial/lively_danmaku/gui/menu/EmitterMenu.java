package com.tutorial.lively_danmaku.gui.menu;

import com.tutorial.lively_danmaku.blockEntity.DanmakuEmitterTE;
import com.tutorial.lively_danmaku.init.BlockRegistry;
import com.tutorial.lively_danmaku.init.MenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

public class EmitterMenu extends AbstractTEBaseMenu<DanmakuEmitterTE> {
    private final ContainerLevelAccess access;
    public EmitterMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id,inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()),new SimpleContainer(2));
    }

    public EmitterMenu(int id, Inventory inventory, BlockEntity blockEntity, Container container) {
        super(MenuRegistry.EMITTER_MENU.get(),(DanmakuEmitterTE) blockEntity,id,1);
        this.access = ContainerLevelAccess.NULL;
        this.addSlot(new Slot(container,0,25,27));
        addPlayerInventory(inventory,8,84,142);
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(this.access, pPlayer, BlockRegistry.DANMAKU_EMITTER.get());
    }
}
