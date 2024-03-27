package com.tutorial.lively_danmaku.mixin.mixinClass;

import com.tutorial.lively_danmaku.init.ItemRegistry;
import com.tutorial.lively_danmaku.init.SoundRegistry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class MixinPlayer {
    @Shadow public abstract Inventory getInventory();

    @Inject(at = @At(value = "HEAD"),method = "getDeathSound", cancellable = true)
    private void injectAddData(CallbackInfoReturnable<SoundEvent> cir) {
        if (this.getInventory().getArmor(3).is(ItemRegistry.ReimuHeaddress.get())) {
            cir.setReturnValue(SoundRegistry.touhou_dead);
        } else {
            cir.setReturnValue(SoundEvents.PLAYER_DEATH);
        }
    }
}
