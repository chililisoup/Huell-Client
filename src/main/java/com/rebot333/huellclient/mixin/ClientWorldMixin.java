package com.rebot333.huellclient.mixin;

import com.rebot333.huellclient.HuellSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.client.world.ClientWorld$Properties")
public class ClientWorldMixin {
    @Inject(method = "getTimeOfDay", at = @At("RETURN"), cancellable = true)
    private void injectedTime(CallbackInfoReturnable<Long> cir) {
        cir.setReturnValue(switch (HuellSettings.clientTime.stringValue) {
            case "DAY" -> 1000L;
            case "NOON" -> 6000L;
            case "NIGHT" -> 13000L;
            case "MIDNIGHT" -> 18000L;
            default -> cir.getReturnValue();
        });
    }
}
