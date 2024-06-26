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

import java.util.ArrayList;

public class DanmakuImportMenu extends AbstractBaseMenu {
    private final ContainerLevelAccess access;
    private final Container container = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            DanmakuImportMenu.this.slotsChanged(this);
        }
    };
    public DanmakuImportMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id,inventory,ContainerLevelAccess.NULL);
    }

    public DanmakuImportMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        super(MenuRegistry.IMPORT_MENU.get(),id,1);
        this.access = access;
        this.addSlot(new Slot(container,0,25,27));
        addPlayerInventory(inventory,8,84,142);
    }

    public void setItem(ArrayList<Long> arrayList) {
        ItemStack itemStack = this.container.getItem(0);
        if (itemStack.is(ItemRegistry.SanaeGohei.get())) {
            this.access.execute((level, blockPos) -> {
                itemStack.getOrCreateTag().putLongArray("crystal_point", arrayList);
                this.container.setItem(0,itemStack);
            });
        }
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(this.access, pPlayer, BlockRegistry.DANMAKU_IMPORT.get());
    }

    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> this.clearContainer(player, this.container));
    }
}
