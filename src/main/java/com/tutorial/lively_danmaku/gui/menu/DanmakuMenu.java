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
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class DanmakuMenu extends AbstractBaseMenu {
    private final ContainerLevelAccess access;
    public final int[] isFull = new int[3];
    private final Container container = new SimpleContainer(120) {
        public void setChanged() {
            super.setChanged();
            DanmakuMenu.this.slotsChanged(this);
        }
    };

    public DanmakuMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id,inventory,ContainerLevelAccess.NULL);
    }

    public DanmakuMenu(int id, Inventory inventory,ContainerLevelAccess access) {
        super(MenuRegistry.DANMAKU_CRAFT.get(),id,4);
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
                return itemStack.is(ItemRegistry.SmallPoint.get());
            }
        });
        this.addSlot(new Slot(this.container, 2, 30, 49) {
            public int getMaxStackSize() { return 16;}
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.is(ItemRegistry.BigPoint.get());
            }
        });
        addPlayerInventory(inventory,-72,84,142);

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
                StringBuilder position = new StringBuilder(81);
                StringBuilder color = new StringBuilder();
                for (int j = 38; j < 120; j++) {
                    ItemStack item = this.container.getItem(j);
                    if (item.is(ItemRegistry.ItemDanmaku.get())) {
                        position.append("1");
                        if (item.getOrCreateTag().get("danmaku_color") != null) {
                            color.append(item.getOrCreateTag().get("danmaku_color"));
                        } else color.append(Color.RED.getRGB());
                        color.append("#");
                    }   else if(item.is(ItemRegistry.ItemStarDanmaku.get())) {
                        position.append("2");
                    }   else
                    {
                        position.append("#");
                    }
                    this.container.setItem(j,ItemStack.EMPTY);
                }
                if (!itemstack1.isEmpty()) {
                    itemstack.getOrCreateTag().putInt("crystal_amount",itemstack1.getCount());
                    itemstack1.setCount(0);
                }
                if(!itemStack2.isEmpty()) {
                    itemstack.getOrCreateTag().putInt("crystal_speed",itemStack2.getCount());
                    itemStack2.setCount(0);
                }
                itemstack.getOrCreateTag().putString("crystal_distribution",position.toString());
                itemstack.getOrCreateTag().putString("crystal_color",color.toString());
                this.container.setItem(0, itemstack);
            });
            return true;
        }
    }

    public void slotsChanged(@NotNull Container container) {
        if (container == this.container) {
            ItemStack itemstack = this.container.getItem(0);
            ItemStack itemStack1 = this.container.getItem(1);
            ItemStack itemStack2 = this.container.getItem(2);
            if (!itemstack.isEmpty() && !itemStack1.isEmpty() && !itemStack2.isEmpty()) {
                this.access.execute((level, blockPos) -> this.isFull[0] = 1);
            }   else {
                this.access.execute((level, blockPos) -> this.isFull[0] = 0);
            }
        }
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
