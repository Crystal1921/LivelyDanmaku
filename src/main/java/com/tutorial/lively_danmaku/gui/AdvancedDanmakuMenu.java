package com.tutorial.lively_danmaku.gui;

import com.tutorial.lively_danmaku.init.BlockRegistry;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import com.tutorial.lively_danmaku.init.MenuRegistry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;

public class AdvancedDanmakuMenu extends AbstractContainerMenu {
    private final ContainerLevelAccess access;
    public ArrayList<ArrayList<Point>> pointList = new ArrayList<>();
    public final int[] isFull = new int[1];
    private final Container container = new SimpleContainer(120) {
        public void setChanged() {
            super.setChanged();
            AdvancedDanmakuMenu.this.slotsChanged(this);
        }
    };

    public AdvancedDanmakuMenu(int id, Inventory inventory, FriendlyByteBuf buf) {
        this(id,inventory,ContainerLevelAccess.NULL);
    }

    public AdvancedDanmakuMenu(int id, Inventory inventory, ContainerLevelAccess access) {
        super(MenuRegistry.ADVANCED_DANMAKU_TABLE.get(),id);
        this.access = access;
        this.addSlot(new Slot(this.container, 0, 0, 19) {
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return (itemStack.is(ItemRegistry.SanaeGohei.get()));
            }
            public int getMaxStackSize() {
                return 1;
            }
        });
        this.addSlot(new Slot(this.container, 1, -30, 49) {
            public boolean mayPlace(@NotNull ItemStack itemStack) {
                return itemStack.is(ItemRegistry.ItemStarDanmaku.get()) || itemStack.is(ItemRegistry.ItemDanmaku.get());
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
        this.addDataSlot(DataSlot.shared(isFull,0));
    }

    @Override
    public boolean clickMenuButton(@NotNull Player player, int i) {
        if ( i == 0) {
            ItemStack itemStack = this.container.getItem(0);
            ItemStack itemStack1 = this.container.getItem(1);
            ItemStack itemStack2 = this.container.getItem(2);
            if (itemStack1.isEmpty() || itemStack2.isEmpty()) {
                return false;
            } else {
                this.access.execute((level, blockPos) -> {
                    if (!itemStack1.isEmpty()) {
                        itemStack.getOrCreateTag().putInt("crystal_amount",itemStack1.getCount());
                        if (itemStack1.is(ItemRegistry.ItemDanmaku.get())) {
                            itemStack.getOrCreateTag().putString("crystal_type","danmaku");
                        }   else {
                            itemStack.getOrCreateTag().putString("crystal_type","star");
                        }
                        itemStack1.setCount(0);
                    }
                    if(!itemStack2.isEmpty()) {
                        itemStack.getOrCreateTag().putInt("crystal_speed",itemStack2.getCount());
                        itemStack2.setCount(0);
                    }
                    itemStack.getOrCreateTag().putString("crystal_point",PointList(GenerateList(pointList)));
                    this.container.setItem(0, itemStack);
                });
                return true;
            }
        }
        return false;
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
        return stillValid(this.access, player, BlockRegistry.ADVANCED_DANMAKU_TABLE.get());
    }

    public void removed(@NotNull Player player) {
        super.removed(player);
        this.access.execute((level, blockPos) -> this.clearContainer(player, this.container));
    }

    public static String PointList (ArrayList<Point> pointArrayList) {
        StringBuilder stringBuilder = new StringBuilder();
        pointArrayList.forEach(point -> stringBuilder.append("*").append(point.x).append("+").append(point.y));
        return stringBuilder.toString();
    }

    private ArrayList<Point> GenerateList(ArrayList<ArrayList<Point>> pointList) {
        ArrayList<Point> arrayList = new ArrayList<>();
        pointList.stream()
                .filter(innerList -> innerList.size() > 1)
                .forEach(innerList -> {
                    for (int i = 0; i < innerList.size() - 1; i++) {
                        Point p1 = innerList.get(i);
                        Point p2 = innerList.get(i + 1);
                        int numPoints = (int) (Math.sqrt(Math.pow((p1.x-p2.x),2) + Math.pow((p1.y-p2.y),2)) / 6);
                        double deltaX = (p2.x - p1.x) / (numPoints + 1.0);
                        double deltaY = (p2.y - p1.y) / (numPoints + 1.0);
                        for (int j = 1; j <= numPoints; j++) {
                            double x = p1.x + j * deltaX;
                            double y = p1.y + j * deltaY;
                            arrayList.add(new Point((int) x, (int) y));
                        }
                    }
                });
        return arrayList;
    }

    public void addPointOnServer(Player player, int x, int y, int number) {
        if (pointList.size() <= number) {
            pointList.add(new ArrayList<>());
        }
        pointList.get(number).add(new Point(x,y));
    }
}
