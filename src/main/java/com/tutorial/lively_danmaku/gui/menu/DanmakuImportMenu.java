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

import static com.tutorial.lively_danmaku.util.MathMethod.PointList;
import static com.tutorial.lively_danmaku.util.MathMethod.extractPoint;

public class DanmakuImportMenu extends AbstractDanmakuMenu {
    private final ContainerLevelAccess access;
    private String imageString;
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
        super(MenuRegistry.IMPORT_MENU.get(),id);
        this.access = access;
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
    public boolean clickMenuButton(@NotNull Player player, int i) {
        if (i == 0) {
            ItemStack itemStack = this.container.getItem(0);
            if (itemStack.is(ItemRegistry.SanaeGohei.get()) && imageString != null) {
                this.access.execute((level, blockPos) -> {
                    itemStack.getOrCreateTag().putString("crystal_point",imageString);
                    this.container.setItem(0,itemStack);
                });
                return true;
            }
        }
        return false;
    }

    public void setImageString(ArrayList<Long> arrayList) {
        this.imageString = PointList(extractPoint(arrayList));
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
