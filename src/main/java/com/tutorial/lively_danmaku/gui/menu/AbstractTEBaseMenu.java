package com.tutorial.lively_danmaku.gui.menu;

import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractTEBaseMenu<T extends BlockEntity> extends AbstractBaseMenu {
    protected T blockEntity;
    public T getBlock() {
        return blockEntity;
    }
    protected AbstractTEBaseMenu(@Nullable MenuType<?> pMenuType,T blockEntity, int pContainerId, int inv_start) {
        super(pMenuType, pContainerId, inv_start);
        this.blockEntity = blockEntity;
    }
}
