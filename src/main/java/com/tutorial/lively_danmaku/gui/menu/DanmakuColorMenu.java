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

public class DanmakuColorMenu extends AbstractBaseMenu{
    private final ContainerLevelAccess access;
    private int rgb;
    private final Container container = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            DanmakuColorMenu.this.slotsChanged(this);
        }
    };
    public DanmakuColorMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id,inventory,ContainerLevelAccess.NULL);
    }
    public DanmakuColorMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        super(MenuRegistry.COLOR_MENU.get(),id,1);
        this.access = access;
        this.addSlot(new Slot(container,0,25,27){
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return (itemStack.is(ItemRegistry.ItemDanmaku.get()));
            }
        });
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
    public boolean clickMenuButton(@NotNull Player player, int i) {
        if (i == 0) {
            ItemStack itemStack = this.container.getItem(0);
            if (itemStack.is(ItemRegistry.ItemDanmaku.get())) {
                itemStack.getOrCreateTag().putInt("danmaku_color",rgb);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean stillValid(@NotNull Player pPlayer) {
        return stillValid(this.access, pPlayer, BlockRegistry.DANMAKU_COLOR.get());
    }

    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> this.clearContainer(player, this.container));
    }

    public void setRGB(int rgb) {
        this.rgb = rgb;
    }
}
