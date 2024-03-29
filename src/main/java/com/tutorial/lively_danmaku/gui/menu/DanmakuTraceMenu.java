package com.tutorial.lively_danmaku.gui.menu;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

public class DanmakuTraceMenu extends AbstractBaseMenu {
    protected DanmakuTraceMenu(@Nullable MenuType<?> pMenuType, int pContainerId, int inv_start) {
        super(pMenuType, pContainerId, inv_start);
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return false;
    }
}
