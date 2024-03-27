package com.tutorial.lively_danmaku.event;

import com.mojang.blaze3d.systems.RenderSystem;
import com.tutorial.lively_danmaku.Utils;
import com.tutorial.lively_danmaku.capability.CapabilityProvider;
import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Utils.MOD_ID)
public class RenderPowerPoint {
    private static ItemStack POWER_POINT;
    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Pre event) {
        if (event.getOverlay() == VanillaGuiOverlay.HOTBAR.type()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) {
                return;
            }
            ItemStack stack = player.getMainHandItem();
            ItemStack helmet = player.getInventory().getArmor(3);
            if (!(stack.is(ItemRegistry.HakureiGohei.get()) || stack.is(ItemRegistry.SanaeGohei.get()) || stack.is(ItemRegistry.BigPoint.get()) || helmet.is(ItemRegistry.ReimuHeaddress.get()))) {
                return;
            }
            GuiGraphics graphics = event.getGuiGraphics();
            Font font = Minecraft.getInstance().font;
            if (POWER_POINT == null) {
                POWER_POINT = ItemRegistry.BigPoint.get().getDefaultInstance();
            }
            graphics.renderItem(POWER_POINT, 5, 5);
            player.getCapability(CapabilityProvider.POWER).ifPresent(cap -> graphics.drawString(font, String.format("%s×%.2f", ChatFormatting.BOLD, cap.getPower()), 20, 10, 0xffffff));
            RenderSystem.enableBlend();
        }
    }
}
