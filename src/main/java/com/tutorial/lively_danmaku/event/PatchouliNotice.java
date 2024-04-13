package com.tutorial.lively_danmaku.event;

import com.tutorial.lively_danmaku.LivelyDanmaku;
import net.minecraft.ChatFormatting;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = LivelyDanmaku.MOD_ID)
public class PatchouliNotice {
    private static boolean notFirst = false;

    @SubscribeEvent
    public static void onEnterGame(PlayerEvent.PlayerLoggedInEvent event) {
        boolean missingPatchouli = !ModList.get().isLoaded("patchouli");
        if (notFirst) {
            return;
        }
        if (missingPatchouli) {
            MutableComponent title = Component.translatable("message.lively_danmaku.missing_patchouli.title")
                    .withStyle(style -> style.withColor(ChatFormatting.GREEN).withBold(true));
            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.OPEN_URL, I18n.get("message.lively_danmaku.missing_patchouli.url"));
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, Component.translatable("message.lively_danmaku.missing_patchouli.url"));
            MutableComponent base = Component.translatable("message.lively_danmaku.missing_patchouli.click_here")
                    .withStyle(style -> style.withColor(ChatFormatting.GOLD).withBold(false).withUnderlined(true).withClickEvent(clickEvent).withHoverEvent(hoverEvent));
            event.getEntity().sendSystemMessage(title.append(CommonComponents.SPACE).append(base));
        }
        notFirst = true;
    }
}
