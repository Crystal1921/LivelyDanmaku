package com.tutorial.lively_danmaku.event;

import com.tutorial.lively_danmaku.init.ItemRegistry;
import net.minecraft.network.protocol.game.ClientboundPlayerAbilitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class SizeChangeEvent {
    @SubscribeEvent
    public static void onEquipmentChange(LivingEquipmentChangeEvent event) {
        if (event.getEntity() instanceof ServerPlayer player && player.gameMode.isSurvival()){
            boolean isReimuHeaddress = event.getTo().is(ItemRegistry.ReimuHeaddress.get());
            player.getAbilities().mayfly = isReimuHeaddress;
            player.getAbilities().flying = isReimuHeaddress;
            player.connection.send(new ClientboundPlayerAbilitiesPacket(player.getAbilities()));
            player.refreshDimensions();
        }
    }

    @SubscribeEvent
    public static void onSizeChange(EntityEvent.Size event) {
        if (event.getEntity() instanceof Player player && event.getEntity().isAddedToWorld()){
            if (player.getInventory().getArmor(3).is(ItemRegistry.ReimuHeaddress.get()) && event.getPose() == Pose.CROUCHING) {
                event.setNewSize(new EntityDimensions(0.6F,0.6F,true),true);
            }
        }
    }
}
