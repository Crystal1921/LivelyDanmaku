package com.tutorial.lively_danmaku.gui.menu;

import com.tutorial.lively_danmaku.init.BlockRegistry;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import com.tutorial.lively_danmaku.init.MenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DanmakuTraceMenu extends AbstractBaseMenu {
    private final ContainerLevelAccess access;
    private final Container container = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            DanmakuTraceMenu.this.slotsChanged(this);
        }
    };
    public DanmakuTraceMenu(int id, Inventory inventory, FriendlyByteBuf byteBuf) {
        this(id,inventory,ContainerLevelAccess.NULL);
    }

    public DanmakuTraceMenu(int pContainerId,Inventory inventory,ContainerLevelAccess access) {
        super(MenuRegistry.TRACE_MENU.get(), pContainerId, 1);
        this.access = access;
        this.addSlot(new Slot(container,0,25,27){
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return (itemStack.is(ItemRegistry.HakureiBulletDanmaku.get()));
            }
        });
        addPlayerInventory(inventory,8,84,142);
    }

    public void setItem(String particleType) {
        ItemStack itemStack = this.container.getItem(0);
        if (itemStack.is(ItemRegistry.HakureiBulletDanmaku.get())) {
            this.access.execute((level, blockPos) -> {
                itemStack.getOrCreateTag().putString("crystal_particle",particleType);
                this.container.setItem(0,itemStack);
            });
        }
    }

    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> this.clearContainer(player, this.container));
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(this.access, pPlayer, BlockRegistry.DANMAKU_TRACE.get());
    }
}
