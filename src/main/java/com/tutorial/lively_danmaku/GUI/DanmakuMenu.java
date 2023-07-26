package com.tutorial.lively_danmaku.GUI;

import com.tutorial.lively_danmaku.init.BlockRegistry;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import com.tutorial.lively_danmaku.init.MenuRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class DanmakuMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    public final int[] isFull = new int[3];
    private final Container container = new SimpleContainer(120) {
        public void setChanged() {
            super.setChanged();
            DanmakuMenu.this.slotsChanged(this);
        }
    };
    public DanmakuMenu(int id, Inventory inventory) {
        this(id, inventory, ContainerLevelAccess.NULL);
    }

    public DanmakuMenu(int id, Inventory inventory,ContainerLevelAccess access) {
        super(MenuRegistry.DANMAKU_CRAFT.get(),id);
        this.access = access;
        this.addSlot(new Slot(this.container, 0, 0, 19) {
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return (itemStack.is(ItemRegistry.HakureiGohei.get()));
            }
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.container, 1, -30, 49) {
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.is(ItemRegistry.red_Point.get());
            }
        });
        this.addSlot(new Slot(this.container, 2, 30, 49) {
            public int getMaxStackSize() { return 16;}
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.is(ItemRegistry.P_Point.get());
            }
        });
        for(int i = 0; i < 3; ++i) {
            for(int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, -72 + j * 18, 84 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(inventory, k, -72 + k * 18, 142));
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(container, i * 9 + j + 39, 128 + j * 18,  i * 18 + 5) {
                    public boolean mayPlace(@NotNull ItemStack itemStack) {
                        return itemStack.is(ItemRegistry.ItemDanmaku.get()) || itemStack.is(ItemRegistry.ItemStarDanmaku.get());
                    }
                    public int getMaxStackSize() { return 1;}
                });
            }
        }
        this.addDataSlot(DataSlot.shared(isFull,0));
    }
    @Override
    public boolean clickMenuButton(@NotNull Player player, int i) {
        ItemStack itemstack = this.container.getItem(0);
        ItemStack itemstack1 = this.container.getItem(1);
        ItemStack itemStack2 = this.container.getItem(2);
        if (itemstack1.isEmpty() || itemStack2.isEmpty()) {
            return false;
        }   else {
            this.access.execute((level,blockPos) -> {
                StringBuilder string = new StringBuilder(81);
                for (int j = 39; j < 120; j++) {
                    if(this.container.getItem(j).is(ItemRegistry.ItemDanmaku.get())) {
                        string.append("1");
                    }   else if(this.container.getItem(j).is(ItemRegistry.ItemStarDanmaku.get())) {
                        string.append("2");
                    }   else
                    {
                        string.append("#");
                    }
                    this.container.setItem(j,ItemStack.EMPTY);
                }
                if (!itemstack1.isEmpty()) {
                    itemstack.getOrCreateTag().putInt("speed",itemstack1.getCount());
                    itemstack1.setCount(0);
                }
                if(!itemStack2.isEmpty()) {
                    itemstack.getOrCreateTag().putInt("amount",itemStack2.getCount());
                    itemStack2.setCount(0);
                }
                itemstack.getOrCreateTag().putString("distribution",string.toString());
                System.out.println(string);
                this.container.setItem(0, itemstack);
            });
            return true;
        }
    }

    public void slotsChanged(@NotNull Container container) {
        if (container == this.container) {
            ItemStack itemstack = this.container.getItem(0);
            ItemStack itemstack1 = this.container.getItem(1);
            ItemStack itemStack2 = this.container.getItem(2);
            if (!itemstack.isEmpty() && !itemstack1.isEmpty() && !itemStack2.isEmpty()) {
                this.access.execute((level, blockPos) -> this.isFull[0] = 1);
            }   else {
                this.access.execute((level, blockPos) -> this.isFull[0] = 0);
            }
        }
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int i) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(i);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (i >= 0 && i <= 3) {
                if (!this.moveItemStackTo(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (itemstack1.is(ItemRegistry.P_Point.get()) || itemstack1.is(ItemRegistry.red_Point.get())) {
                if (!this.moveItemStackTo(itemstack1, 1, 3, true)) {
                    return ItemStack.EMPTY;
                }
            }
            else {
                if (this.slots.get(0).hasItem() || !this.slots.get(0).mayPlace(itemstack1)) {
                    return ItemStack.EMPTY;
                }

                ItemStack itemstack2 = itemstack1.copyWithCount(1);
                itemstack1.shrink(1);
                this.slots.get(0).setByPlayer(itemstack2);
            }

            if (itemstack1.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return stillValid(this.access, player, BlockRegistry.DANMAKU_TABLE.get());
    }
    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> this.clearContainer(player, this.container));
    }
}
