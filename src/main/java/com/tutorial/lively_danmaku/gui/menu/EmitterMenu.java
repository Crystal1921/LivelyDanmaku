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

public class EmitterMenu extends AbstractDanmakuMenu {
    private final DanmakuEmitterTE danmakuEmitterTE;
    private final ContainerLevelAccess access;
    public EmitterMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id,inventory, inventory.player.level().getBlockEntity(buf.readBlockPos()),new SimpleContainer(2));
    }

    public EmitterMenu(int id, Inventory inventory, BlockEntity blockEntity, Container container) {
        super(MenuRegistry.EMITTER_MENU.get(),id);
        this.access = ContainerLevelAccess.NULL;
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

    public DanmakuEmitterTE getDanmakuEmitterTE () {
        return this.danmakuEmitterTE;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(this.access, pPlayer, BlockRegistry.DANMAKU_EMITTER.get());
    }
}
